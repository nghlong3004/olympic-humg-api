package vn.edu.humg.log;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonLogFields {

    API_NAME("api_name", Collections.emptyList()),
    ELAPSED_TIME("elapsed_time", Collections.emptyList()),
    CLIENT_ADDRESS("client_ip", List.of("x-forwarded-for")),
    METHOD("method", Collections.emptyList()),
    REQUEST_URI("request_uri", Collections.emptyList()),
    QUERY_STRING("query_string", Collections.emptyList()),
    REQUEST_HEADERS("request_headers", Collections.emptyList()),
    REQUEST_BODY("request_body", Collections.emptyList()),
    RESPONSE_HEADERS("response_headers", Collections.emptyList()),
    RESPONSE_BODY("response_body", Collections.emptyList()),
    STATUS_CODE("status_code", Collections.emptyList()),
    ;

    private final String name;
    private final List<String> httpHeaders;
}
