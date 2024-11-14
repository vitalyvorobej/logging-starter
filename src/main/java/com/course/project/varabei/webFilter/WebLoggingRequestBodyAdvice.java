package com.course.project.varabei.webFilter;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

import static com.course.project.varabei.utils.HttpRequestUtils.getFormattedQuery;


@ControllerAdvice
public class WebLoggingRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLoggingRequestBodyAdvice.class);

    @Autowired
    private HttpServletRequest request;

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        String method = request.getMethod();
        String uri = request.getRequestURI() + getFormattedQuery(request);

        LOGGER.info("Request body: {} {} {}", body, method, uri);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}