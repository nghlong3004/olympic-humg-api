package vn.edu.humg.log;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public enum HttpResponseAccessLogFields  implements LogField<HttpServletResponse>  {
    RESPONSE_CODE("http.response.status_code", response -> String.valueOf(response.getStatus())),
    RESPONSE_MIME_TYPE("http.response.mime_type", ServletResponse::getContentType),
    RESPONSE_BYTES("http.response.bytes", response -> {
        if (response instanceof ContentCachingResponseWrapper responseWrapper) {
            return String.valueOf(getHeaderSize(responseWrapper) + responseWrapper.getContentAsByteArray().length);
        }
        return null;
    }),
    RESPONSE_BODY_CONTENT("http.response.body.content", HttpResponseAccessLogFields::getBody),
    RESPONSE_BODY_BYTES("http.response.body.bytes", response ->
            String.valueOf(RESPONSE_BODY_CONTENT.getValue(response).getBytes(StandardCharsets.UTF_8).length));

    @Getter
    private final String key;
    private final Function<HttpServletResponse, String> valueFunction;

    HttpResponseAccessLogFields(String key, Function<HttpServletResponse, String> valueFunction) {
        this.key = key;
        this.valueFunction = valueFunction;
    }

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
            headerSize += name.length() + value.length() + 2; // \r\n
        }
        return headerSize;
    }
}
