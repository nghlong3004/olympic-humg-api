package vn.edu.humg.olympic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.humg.olympic.exception.BadRequestException;
import vn.edu.humg.olympic.model.User;
import vn.edu.humg.olympic.model.request.LoginRequest;
import vn.edu.humg.olympic.model.request.RegisterRequest;
import vn.edu.humg.olympic.model.response.LoginResponse;
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

        userRepository.insert(RegisterRequest.from(request, passwordEncoder.encode(request.password())));
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                                  .orElseThrow(() -> new BadRequestException("Bad credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadRequestException("Bad credentials");
        }

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new BadRequestException("User account is disabled");
        }

        return LoginResponse.from(user);
    }

}
