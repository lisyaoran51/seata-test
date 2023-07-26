package org.example.orderService.common.exception;


import org.apache.catalina.connector.ClientAbortException;
import org.example.orderService.http.HttpException;
import org.example.orderService.http.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ControllerAdvice
@RestController
public class GlobalException {

    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler
    public HttpResult handle(Exception e) {
        if(e instanceof BindException){
            BindingResult result = ((BindException) e).getBindingResult();
            return MyExceptionHandle(result);
        } else if(e instanceof MethodArgumentNotValidException){
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            return MyExceptionHandle(result);
        }else if(e instanceof ClientAbortException){
            return HttpResult.buildError(e.getMessage());
        }
        log.error("系统异常：", e);
        return HttpResult.buildError(HttpException.SYSTEM_ERROR.getCode(), e.getMessage());
    }


    public HttpResult MyExceptionHandle(BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                return HttpResult.buildError(fieldError.getDefaultMessage());
            }
        }
        return HttpResult.buildError(HttpException.SYSTEM_ERROR);
    }

}

