package ua.tartemchuk.usermanagement.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.tartemchuk.usermanagement.entities.User;
import ua.tartemchuk.usermanagement.repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Connection connection;

    @Override
    public List<User> findAllPaginated(int page, int itemsPerPage) {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, first_name, last_name, username, location, active, phone, image_file_path FROM users ORDER BY id DESC LIMIT ? OFFSET ?;")) {
            statement.setInt(1, itemsPerPage);
            page--; // to make offset 0 for first page
            statement.setInt(2, page * itemsPerPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = populateUser(resultSet);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User populateUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("username"));
        user.setLocation(resultSet.getString("location"));
        user.setActive(resultSet.getBoolean("active"));
        user.setPhoneNumber(resultSet.getString("phone"));
        user.setImageFilePath(resultSet.getString("image_file_path"));
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, first_name, last_name, username, location, active, phone, image_file_path FROM users WHERE id = ?;")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = populateUser(resultSet);

                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        Long id = user.getId();
        if (id == null || id == 0) {
            id = insert(user);
        }
        return findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    private long insert(User user) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (first_name, last_name, username, active, location, phone, image_file_path) VALUES (?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            statement.setString(++k, user.getFirstName());
            statement.setString(++k, user.getLastName());
            statement.setString(++k, user.getUsername());
            statement.setBoolean(++k, user.isActive());
            statement.setString(++k, user.getLocation());
            statement.setString(++k, user.getPhoneNumber());
            statement.setString(++k, user.getImageFilePath());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            throw new RuntimeException("Something went wrong");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users  WHERE id = ?;")) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countTableRows() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM users;")) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
