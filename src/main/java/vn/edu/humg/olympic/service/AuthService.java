package vn.edu.humg.olympic.service;

import vn.edu.humg.olympic.model.request.LoginRequest;
import vn.edu.humg.olympic.model.request.RegisterRequest;
import vn.edu.humg.olympic.model.response.LoginResponse;

public interface AuthService {
    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
