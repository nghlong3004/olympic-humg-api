package vn.edu.humg.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger COMMON_LOG = LoggerFactory.getLogger("COMMON_LOGGER");
    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("ACCESS_LOGGER");

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest servletRequest, @NonNull HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        COMMON_LOG.info("common log here");

        final var request = new ContentCachingRequestWrapper(servletRequest);
        final var response = new ContentCachingResponseWrapper(servletResponse);
        final long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
            logAccessFields(request, response, start);
        } finally {
            MDC.clear();
        }

    }

    private void logAccessFields(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
                                 long start) throws IOException {
        accessLogFieldContext(HttpRequestAccessLogFields.class, request);
        accessLogFieldContext(HttpResponseAccessLogFields.class, response);

        MDC.put("response.elapsed_time", String.valueOf(System.currentTimeMillis() - start));

        // it just triggers the logging event, any messages aren't required.
        // Custom data will be stored in MDC map and other information will be sourced from another environment.
        ACCESS_LOG.info("");

        response.copyBodyToResponse();
    }

    private <T, E extends Enum<E> & LogField<T>> void accessLogFieldContext(Class<E> fieldsEnum, T source) {
        EnumSet.allOf(fieldsEnum)
            .forEach(field -> {
                var value = field.getValue(source);
                if (value != null) {
                    MDC.put(field.getKey(), value);
                }
            });
    }

    private void logCommonFields(final ContentCachingRequestWrapper request,
                                 final ContentCachingResponseWrapper response) {

        // TODO Elaborate based on https://github.sec.samsung.net/samsungaccount/iam/blob/e90c8653f01ab3f1698370e1e00022fc1593c867/src/main/java/com/samsung/account/iam/utils/WipUtils.java#L306-L335
        MDC.put(CommonLogFields.CLIENT_ADDRESS.getName(), CommonLogFields.CLIENT_ADDRESS.getHttpHeaders()
            .stream()
            .map(request::getHeader)
            .filter(Objects::nonNull)
            .findAny()
            .orElse(null));
        MDC.put(CommonLogFields.METHOD.getName(), request.getMethod());
        MDC.put(CommonLogFields.REQUEST_URI.getName(), request.getRequestURI());
        MDC.put(CommonLogFields.STATUS_CODE.getName(), String.valueOf(response.getStatus()));
    }

    private Map<String, Object> getRequestHeaders(final ContentCachingRequestWrapper request) {
        return Collections.list(request.getHeaderNames())
            .stream()
            .collect(Collectors.toMap(Function.identity(), request::getHeader));
    }

    private String getRequestBody(final ContentCachingRequestWrapper request) {
        return new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
    }

    private Map<String, Object> getResponseHeaders(final ContentCachingResponseWrapper response) {
        return response.getHeaderNames()
            .stream()
            .distinct()
            .collect(Collectors.toMap(Function.identity(), response::getHeader));
    }

    private Object getResponseBody(final ContentCachingResponseWrapper response) {
        try {
            return objectMapper.readValue(response.getContentAsByteArray(), Map.class);
        } catch (final Exception e) {
            return new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        }
    }
}
