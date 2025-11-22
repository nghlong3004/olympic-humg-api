package vn.edu.humg.olympic.api.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.edu.humg.olympic.api.converter.UserConverter;
import vn.edu.humg.olympic.api.exception.ErrorCode;
import vn.edu.humg.olympic.api.exception.ResourceException;
import vn.edu.humg.olympic.api.model.Gender;
import vn.edu.humg.olympic.api.model.Role;
import vn.edu.humg.olympic.api.model.User;
import vn.edu.humg.olympic.api.model.request.RegisterRequest;
import vn.edu.humg.olympic.api.repository.UserRepository;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_shouldCreateNewUser_whenEmailNotExists() {
        var request = new RegisterRequest("Long", "Nguyen", "long@example.com", "123456", Gender.MALE,
                                          Date.valueOf("2004-03-30"), "0987123456", "HUMG", "Control Engineering");

        when(userRepository.findByEmail("long@example.com")).thenReturn(Optional.empty());

        when(passwordEncoder.encode("123456")).thenReturn("hashed-password");
        when(userConverter.from(request)).thenReturn(User.builder()
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
                                                         .build());
        authService.register(request);

        var userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
    }

    @Test
    void register_shouldThrowBadRequest_whenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("Long", "Nguyen", "long@example.com", "123456", Gender.MALE,
                                                      Date.valueOf("2004-03-30"), "0987123456", "HUMG",
                                                      "Control Engineering");

        User existing = User.builder()
                            .id(1L)
                            .email("long@example.com")
                            .build();

        when(userRepository.findByEmail("long@example.com")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> authService.register(request)).isInstanceOf(ResourceException.class)
                                                               .hasMessage(ErrorCode.EMAIL_ALREADY.getMessage());

        verify(userRepository, never()).save(any());
    }
}
