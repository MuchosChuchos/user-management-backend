package ua.tartemchuk.usermanagement.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ua.tartemchuk.usermanagement.controllers.dtos.PageWrapper;
import ua.tartemchuk.usermanagement.controllers.dtos.UserDto;
import ua.tartemchuk.usermanagement.entities.User;
import ua.tartemchuk.usermanagement.repositories.UserRepository;
import ua.tartemchuk.usermanagement.services.ImageService;
import ua.tartemchuk.usermanagement.services.UserService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    @Value("${usermanagement.pagination.user.items-per-page}")
    private Integer itemsPerPage;

    @Override
    public PageWrapper<User> findAllPaginated(int page) {
        int rowsCount = repository.countTableRows();
        int pages = (int) Math.ceil((double)rowsCount / itemsPerPage);
        validatePage(page, pages);
        return new PageWrapper<>(pages, repository.findAllPaginated(page, itemsPerPage));
    }

    private void validatePage(int page, int pages) {
        if (page <= 0 || page > pages) {
            throw new RuntimeException("page is wrong");
        }
    }

    @Override
    public User save(UserDto userDto, MultipartFile image) {
        User mappedUser = modelMapper.map(userDto, User.class);
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        mappedUser.setImageFilePath(fileName);
        User user = repository.save(mappedUser);
        String uploadDir = "user-photos/" + user.getId();
        try {
            imageService.transferToLocalStorage(uploadDir, fileName, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
