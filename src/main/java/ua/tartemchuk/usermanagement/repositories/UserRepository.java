package ua.tartemchuk.usermanagement.repositories;

import org.springframework.stereotype.Repository;
import ua.tartemchuk.usermanagement.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    List<User> findAllPaginated(int page, int itemsPerPage);

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);

    int countTableRows();

}
