package com.lelcc.common;

import lombok.Data;

@Data
public class Result<T> {
    private T data;
    private String message;
    private Integer code;

    public Result() {}

    public static <T>Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.data = data;
        result.message = "success";
        result.code = 200;
        return result;
    }
    public static <T>Result<T> error(Integer code ,String message) {
        Result<T> result = new Result<T>();

        result.message = message;
        result.code = code;
        return result;
    }
}
