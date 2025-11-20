package vn.edu.humg.olympic.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vn.edu.humg.olympic.constant.ApplicationConstant;
import vn.edu.humg.olympic.model.request.LoginRequest;
import vn.edu.humg.olympic.model.request.RegisterRequest;
import vn.edu.humg.olympic.model.response.LoginResponse;
import vn.edu.humg.olympic.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
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
        session.setAttribute(ApplicationConstant.SESSION_USER_ID, response.id());
        session.setAttribute(ApplicationConstant.SESSION_USER_ROLE, response.role());

        return response;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        
        session.invalidate();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

