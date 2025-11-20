package vn.edu.humg.olympic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vn.edu.humg.olympic.model.request.LoginRequest;
import vn.edu.humg.olympic.model.request.RegisterRequest;
import vn.edu.humg.olympic.model.response.LoginResponse;
import vn.edu.humg.olympic.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @Valid
            @RequestBody
            RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(
            @Valid
            @RequestBody
            LoginRequest request,
            HttpServletRequest httpRequest) {
        LoginResponse response = authService.login(request);

        var session = httpRequest.getSession(true);
        session.setAttribute("USER_ID", response.id());
        session.setAttribute("USER_ROLE", response.role());

        return response;
    }
}

