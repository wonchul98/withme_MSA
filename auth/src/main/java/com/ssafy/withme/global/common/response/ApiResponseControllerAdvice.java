package com.ssafy.withme.global.common.response;

import com.ssafy.withme.global.exception.ErrorCode;
import com.ssafy.withme.global.exception.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(
        basePackages = "com.ssafy.withme",
        basePackageClasses = GlobalExceptionHandler.class
)

public class ApiResponseControllerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        HttpServletResponse httpServletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = httpServletResponse.getStatus();
        HttpStatus httpStatus = HttpStatus.resolve(status);

        if (httpStatus == null) {
            return body;
        }
        if (body instanceof ErrorCode errorCode) {
            return ApiResponse.onFailure(errorCode);
        }
        if (httpStatus.is2xxSuccessful()) {
            return ApiResponse.onSuccess(httpServletResponse.getStatus(), body);
        }
        return body;
    }
}
