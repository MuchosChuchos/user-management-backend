package ua.tartemchuk.usermanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.tartemchuk.usermanagement.controllers.dtos.PageWrapper;
import ua.tartemchuk.usermanagement.controllers.dtos.UserDto;
import ua.tartemchuk.usermanagement.entities.User;
import ua.tartemchuk.usermanagement.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService service;

    @GetMapping
    public PageWrapper<User> getAll(@RequestParam @Min(1) int page) {
        return this.service.findAllPaginated(page);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid UserDto userDto,
                                       @RequestParam MultipartFile image) {
        return ResponseEntity.ok(this.service.save(userDto, image));
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
