package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.utils.DataNotFoundException;
import com.opitz.iotprototype.utils.ErrorResource;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Pascal
 * Date: 02.05.13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(KeyAlreadyExistsException.class)
    @ResponseBody
    public ErrorResource handleKeyAlreadyExistsException(KeyAlreadyExistsException e, HttpServletResponse response) throws Exception{
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return new ErrorResource(response.getStatus(), e.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseBody
    public ErrorResource handleAccountNotFoundException(AccountNotFoundException e, HttpServletResponse response) throws Exception{
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return new ErrorResource(response.getStatus(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResource handleException(Exception e, HttpServletResponse response) throws Exception{
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return new ErrorResource(response.getStatus(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ErrorResource handleHttpException(HttpRequestMethodNotSupportedException e, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return new ErrorResource(response.getStatus(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ErrorResource handleContraintViolationException(ConstraintViolationException e, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return new ErrorResource(response.getStatus(), e.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    public ErrorResource handleDataNotFoundException(DataNotFoundException e, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return new ErrorResource(response.getStatus(), e.getMessage());
    }
}
