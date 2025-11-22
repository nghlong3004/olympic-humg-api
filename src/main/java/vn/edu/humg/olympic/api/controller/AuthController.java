package vn.edu.humg.olympic.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.humg.olympic.api.constant.APIConstant;
import vn.edu.humg.olympic.api.model.request.RegisterRequest;
import vn.edu.humg.olympic.api.service.AuthService;

@RestController
@RequestMapping(APIConstant.API_AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = APIConstant.REGISTER, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @Valid
            @RequestBody
            RegisterRequest request
    ) {
        authService.register(request);
    }
}
