package vn.edu.humg.olympic.api.log;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@RequiredArgsConstructor
public enum HttpResponseAccessLogFields implements LogField<HttpServletResponse> {
    RESPONSE_CODE("http.response.status_code", response -> String.valueOf(response.getStatus())),
    RESPONSE_MIME_TYPE("http.response.mime_type", ServletResponse::getContentType),
    RESPONSE_BYTES("http.response.bytes", response -> {
        if (response instanceof ContentCachingResponseWrapper responseWrapper) {
            return String.valueOf(getHeaderSize(responseWrapper) + responseWrapper.getContentAsByteArray().length);
        }
        return null;
    }),
    RESPONSE_BODY_CONTENT("http.response.body.content", HttpResponseAccessLogFields::getBody),
    RESPONSE_BODY_BYTES("http.response.body.bytes", response -> {
        String body = getBody(response);
        return String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);
    });

    @Getter(onMethod_ = {@Override})
    private final String key;
    private final Function<HttpServletResponse, String> valueFunction;

    @Override
    public String getValue(HttpServletResponse response) {
        return valueFunction.apply(response);
    }

    private static String getBody(ServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper responseWrapper) {
            return new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        }
        return "";
    }

    static int getHeaderSize(HttpServletResponse response) {
        int headerSize = 0;
        for (String name : response.getHeaderNames()) {
            String value = response.getHeader(name);
            headerSize += name.length() + (value != null ? value.length() : 0) + 2; // \r\n
        }
        return headerSize;
    }
}
