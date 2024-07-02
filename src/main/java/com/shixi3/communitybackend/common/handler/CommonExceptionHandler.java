package com.shixi3.communitybackend.common.handler;

import com.shixi3.communitybackend.common.enums.ErrorCodes;
import com.shixi3.communitybackend.common.exception.BizException;
import com.shixi3.communitybackend.common.model.CommonResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResult.error(ErrorCodes.BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResult.error(ErrorCodes.BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CommonResult.error(ErrorCodes.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<?> bindExceptionHandler(BindException ex) {
        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CommonResult.error(ErrorCodes.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return CommonResult.error(ErrorCodes.METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
    }

    @ExceptionHandler(BizException.class)
    public CommonResult<?> bizExceptionHandler(BizException ex) {
        log.error("[bizExceptionHandler]", ex);
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult<?>> defaultExceptionHandler(Exception ex) {
        log.error("[defaultExceptionHandler]", ex);
        return new ResponseEntity<>(
                CommonResult.error(ErrorCodes.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
