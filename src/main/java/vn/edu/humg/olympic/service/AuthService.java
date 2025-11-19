package vn.edu.humg.olympic.service;

import vn.edu.humg.olympic.model.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
}
