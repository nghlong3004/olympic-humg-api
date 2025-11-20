package vn.edu.humg.olympic.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vn.edu.humg.olympic.model.User;
import vn.edu.humg.olympic.model.UserGender;
import vn.edu.humg.olympic.model.UserRole;

import java.sql.Date;

public record RegisterRequest(
        @NotBlank
        @Size(max = 25)
        String firstName,
        @NotBlank
        @Size(max = 25)
        String lastName,
        @NotBlank
        @Email
        @Size(max = 255)
        String email,
        @NotBlank
        @Size(min = 6)
        String password,
        @NotNull
        UserGender gender,
        @NotNull
        Date birthday,
        @Size(max = 20)
        String phone,
        @Size(max = 50)
        String universityName,
        @Size(max = 50)
        String facultyName
) {

    public static User from(RegisterRequest request,
                            String passwordHash) {
        if (request == null) {
            return null;
        }
        return User.builder()
                   .firstName(request.firstName())
                   .lastName(request.lastName())
                   .email(request.email())
                   .passwordHash(passwordHash)
                   .gender(request.gender())
                   .birthday(request.birthday())
                   .role(UserRole.STUDENT)
                   .phone(request.phone())
                   .universityName(request.universityName())
                   .facultyName(request.facultyName())
                   .avatarUrl(null)
                   .isActive(true)
                   .build();
    }

}
