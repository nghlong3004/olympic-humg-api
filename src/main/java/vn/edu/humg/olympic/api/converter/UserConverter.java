package vn.edu.humg.olympic.api.converter;

import org.springframework.stereotype.Component;
import vn.edu.humg.olympic.api.model.Role;
import vn.edu.humg.olympic.api.model.User;
import vn.edu.humg.olympic.api.model.request.RegisterRequest;

@Component
public class UserConverter {
    public User from(RegisterRequest request) {
        return User.builder()
                   .firstName(request.firstName())
                   .lastName(request.lastName())
                   .email(request.email())
                   .gender(request.gender())
                   .birthday(request.birthday())
                   .role(Role.STUDENT)
                   .phone(request.phone())
                   .universityName(request.universityName())
                   .facultyName(request.facultyName())
                   .avatarUrl(null)
                   .isActive(true)
                   .build();
    }
}
