package vn.edu.humg.olympic.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vn.edu.humg.olympic.api.model.Gender;

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
        Gender gender,
        @NotNull
        Date birthday,
        @Size(max = 20)
        String phone,
        @Size(max = 50)
        String universityName,
        @Size(max = 50)
        String facultyName
) {
}
