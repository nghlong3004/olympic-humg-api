package vn.edu.humg.olympic.api.service;

import vn.edu.humg.olympic.api.model.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
}
