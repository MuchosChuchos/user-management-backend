package ua.tartemchuk.usermanagement.services;

import org.springframework.web.multipart.MultipartFile;
import ua.tartemchuk.usermanagement.controllers.dtos.PageWrapper;
import ua.tartemchuk.usermanagement.controllers.dtos.UserDto;
import ua.tartemchuk.usermanagement.entities.User;

import java.util.List;

public interface UserService {

    PageWrapper<User> findAllPaginated(int page);

    User save(UserDto userDto, MultipartFile image);

    void deleteById(Long id);

}
