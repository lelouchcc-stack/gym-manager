package com.lelcc.common;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
