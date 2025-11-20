package vn.edu.humg.olympic.model.response;

import vn.edu.humg.olympic.model.User;
import vn.edu.humg.olympic.model.UserGender;
import vn.edu.humg.olympic.model.UserRole;

import java.sql.Date;

public record LoginResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserGender gender,
        Date birthday,
        UserRole role,
        String phone,
        String universityName,
        String facultyName,
        String avatarUrl
) {
    public static LoginResponse from(User user) {
        if (user == null) {
            return null;
        }

        return new LoginResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                                 user.getGender(), user.getBirthday(), user.getRole(), user.getPhone(),
                                 user.getUniversityName(), user.getFacultyName(), user.getAvatarUrl());
    }
}
