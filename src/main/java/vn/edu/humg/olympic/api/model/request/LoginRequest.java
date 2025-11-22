package vn.edu.humg.olympic.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull
        @Email
        String email,
        @NotNull
        @Size(min = 6, max = 100)
        String password
) {
}
