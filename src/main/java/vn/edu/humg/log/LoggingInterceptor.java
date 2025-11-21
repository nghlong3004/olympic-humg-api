package vn.edu.humg.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import org.jboss.logging.MDC;

public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler
    ) {
        if (handler instanceof HandlerMethod handlerMethod) {
            MDC.put(CommonLogFields.API_NAME.getName(), handlerMethod.getMethod().getName());
        }

//        MDC.put(CommonLogFields.TRACE_ID.getName(), CommonLogFields.TRACE_ID.getHttpHeaders()
//            .stream()
//            .map(request::getHeader)
//            .filter(Objects::nonNull)
//            .findAny()
//            .orElse(null));

        return true;
    }
}
