package ua.tartemchuk.usermanagement.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @JsonProperty
    private boolean isActive;

    @NotBlank
    private String location;

    @NotBlank
    private String phoneNumber;

}