package vn.edu.humg.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.function.Function;

enum HttpRequestAccessLogFields implements LogField<HttpServletRequest> {
    // Request (Required)
    REQUEST_METHOD("http.request.method", HttpServletRequest::getMethod),
    REQUEST_MIME_TYPE("http.request.mime_type", HttpServletRequest::getContentType),
    REQUEST_BYTES("http.request.bytes", request -> {
        if (request instanceof ContentCachingRequestWrapper requestWrapper) {
            return String.valueOf(requestWrapper.getContentAsByteArray().length + getHeaderSize(request));
        }

        return "0";
    }),
    REQUEST_HEADER_REFERRER("http.request.referrer", request -> request.getHeader("Referer")),
    // Request (Optional)
    REQUEST_HEADER_REQUEST_ID("http.request.id", request -> request.getHeader("sa-request-id")),
    REQUEST_BODY_CONTENT("http.request.body.content", request -> {
        if (request instanceof ContentCachingRequestWrapper requestWrapper) {
            // request max size
            final int maxSize = 1024;
            String requestText = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            return requestText.length() > maxSize ? requestText.substring(0, maxSize) : requestText;
        }
        return "";
    }),
    REQUEST_BODY_BYTES("http.request.body.bytes",
            request -> String.valueOf(REQUEST_BODY_CONTENT.getValue(request).getBytes(StandardCharsets.UTF_8).length)),

    // URL (Required)
    URL_PATH("url.path", HttpServletRequest::getRequestURI),
    URL_QUERY("url.query", HttpServletRequest::getQueryString),
    URL_DOMAIN("url.domain", HttpServletRequest::getServerName),

    // Client (Required)
    CLIENT_ADDRESS("client.address", HttpServletRequest::getRemoteAddr),
    CLIENT_IP("client.ip", RequestAddressUtil::getClientIP),
    CLIENT_DOMAIN("client.domain", HttpServletRequest::getRemoteHost),

    // User agent (Optional)
    USER_AGENT_ORIGINAL("user_agent.original", request -> request.getHeader("User-Agent")),;

    @Getter
    private final String key;
    private final Function<HttpServletRequest, String> valueFunction;

    HttpRequestAccessLogFields(String key, Function<HttpServletRequest, String> valueFunction) {
        this.key = key;
        this.valueFunction = valueFunction;
    }

    public String getValue(HttpServletRequest request) {
        return valueFunction.apply(request);
    }

    static int getHeaderSize(HttpServletRequest request) {
        int headerSize = 0;
        for (String name : Collections.list(request.getHeaderNames())) {
            String value = request.getHeader(name);
            headerSize += name.length() + value.length() + 2; // \r\n
        }
        return headerSize;
    }
}
