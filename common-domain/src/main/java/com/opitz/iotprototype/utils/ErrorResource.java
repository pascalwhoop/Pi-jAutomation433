package com.opitz.iotprototype.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Pascal
 * Date: 30.04.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class ErrorResource{
    private final int status;
    private final String message;

    public ErrorResource(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
