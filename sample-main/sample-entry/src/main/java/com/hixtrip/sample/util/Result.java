package com.hixtrip.sample.util;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8004639252556522119L;

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(){
        return builderResult(200, "success", null);
    }

    public static <T> Result<T> success(T data){
        return builderResult(200, "success", data);
    }

    public static <T> Result<T> fail(){
        return builderResult(500, "fail", null);
    }

    public static <T> Result<T> fail(T data){
        return builderResult(500, "fail", data);
    }

    public static <T> Result<T> builderResult(int code, String msg, T data){
        return  (Result<T>)Result.builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }
}
