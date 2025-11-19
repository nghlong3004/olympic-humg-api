package vn.edu.humg.olympic.model.response;

import java.sql.Timestamp;
import java.util.Map;

public record ErrorResponse(
        Timestamp timestamp,
        int status,
        String message,
        Map<String, String> details
) {}
