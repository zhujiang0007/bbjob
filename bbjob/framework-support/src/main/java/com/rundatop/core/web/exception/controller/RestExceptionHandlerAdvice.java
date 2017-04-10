package com.rundatop.core.web.exception.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.rundatop.core.exception.BizException;

@ControllerAdvice(annotations={RestController.class})
public class RestExceptionHandlerAdvice {

    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String,Object> exception(Exception exception,WebRequest request){
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("errorMessage", exception.getMessage());
        return map;
    }
    @ExceptionHandler(value=BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,Object> bizException(Exception exception,WebRequest request){
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("errorMessage", exception.getMessage());
        return map;
    }
}