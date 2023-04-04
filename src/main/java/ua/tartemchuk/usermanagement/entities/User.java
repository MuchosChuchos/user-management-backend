package ua.tartemchuk.usermanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private boolean isActive;

    private String location;

    private String phoneNumber;

    private String imageFilePath;

    public String getImageFilePath() {
        if (id != null) {
            return "/user-photos/" + id + "/" + imageFilePath;
        }
        return imageFilePath;
    }

}
