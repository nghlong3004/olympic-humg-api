package vn.edu.humg.olympic.api.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.logging.MDC;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        if (handler instanceof HandlerMethod handlerMethod) {
            MDC.put(CommonLogFields.API_NAME.getName(), handlerMethod.getMethod()
                                                                     .getName());
        }

        return true;
    }
}
