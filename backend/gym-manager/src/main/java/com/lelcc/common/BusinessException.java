package com.lelcc.common;

//Ai 借助

public class BusinessException extends RuntimeException{
    private int code;


    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;

    }
    public int getCode() {
        return code;
    }
}
