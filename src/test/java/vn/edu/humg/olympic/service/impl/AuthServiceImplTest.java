package vn.edu.humg.olympic.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.edu.humg.olympic.exception.BadRequestException;
import vn.edu.humg.olympic.model.User;
import vn.edu.humg.olympic.model.UserGender;
import vn.edu.humg.olympic.model.request.RegisterRequest;
import vn.edu.humg.olympic.repository.UserRepository;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_shouldCreateNewUser_whenEmailNotExists() {
        RegisterRequest request = new RegisterRequest("Long", "Nguyen", "long@example.com", "123456", UserGender.MALE,
                                                      Date.valueOf("2004-03-30"), "0987123456", "HUMG",
                                                      "Control Engineering");

        when(userRepository.findByEmail("long@example.com")).thenReturn(Optional.empty());

        when(passwordEncoder.encode("123456")).thenReturn("hashed-password");

        authService.register(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).insert(userCaptor.capture());
    }

    @Test
    void register_shouldThrowBadRequest_whenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("Long", "Nguyen", "long@example.com", "123456", UserGender.MALE,
                                                      Date.valueOf("2004-03-30"), "0987123456", "HUMG",
                                                      "Control Engineering");

        User existing = User.builder()
                            .id(1L)
                            .email("long@example.com")
                            .build();

        when(userRepository.findByEmail("long@example.com")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> authService.register(request)).isInstanceOf(BadRequestException.class)
                                                               .hasMessage("Email already exists");

        verify(userRepository, never()).insert(any());
    }
}
