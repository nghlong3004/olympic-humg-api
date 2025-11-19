package vn.edu.humg.olympic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.humg.olympic.exception.BadRequestException;
import vn.edu.humg.olympic.model.User;
import vn.edu.humg.olympic.model.UserRole;
import vn.edu.humg.olympic.model.request.RegisterRequest;
import vn.edu.humg.olympic.repository.UserRepository;
import vn.edu.humg.olympic.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {

        userRepository.findByEmail(request.email())
                      .ifPresent(u -> {
                          throw new BadRequestException("Email already exists");
                      });

        User user = User.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .email(request.email())
                        .passwordHash(passwordEncoder.encode(request.password()))
                        .gender(request.gender())
                        .birthday(request.birthday())
                        .role(UserRole.STUDENT)
                        .phone(request.phone())
                        .universityName(request.universityName())
                        .facultyName(request.facultyName())
                        .avatarUrl(null)
                        .isActive(true)
                        .build();

        userRepository.insert(user);
    }
}
