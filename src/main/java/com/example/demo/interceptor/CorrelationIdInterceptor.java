package com.example.demo.interceptor;

import brave.baggage.BaggageField;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class CorrelationIdInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        String correlationId = request.getHeader("X-Correlation-Id");
        if (StringUtils.isEmpty(correlationId)) {
            UUID uuid = UUID.randomUUID();
            correlationId = "frontend-" + uuid.toString();
            BaggageField.getByName("X-Correlation-Id").updateValue(correlationId);

            // need to update value to MDC manually, as it won't be flushed immediately
            // to ensure all logs get the new correlation id printed
            // TODO: try "flushOnUpdate" setting in upcoming Sleuth version
            // https://github.com/spring-cloud/spring-cloud-sleuth/wiki/Spring-Cloud-Sleuth-3.0-Migration-Guide#new-features
            ThreadContext.put("X-Correlation-Id", correlationId);
        }

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex) {

        ThreadContext.clearAll();
    }
}