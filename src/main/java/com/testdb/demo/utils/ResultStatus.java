package com.testdb.demo.utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public enum ResultStatus {

    SUCCESS( 0, "OK"),
    FAILURE( 1, "Failure"),
    WRONG_PARAMETERS( 2,"Wrong Parameters"),
    BAD_REQUEST( 400, "Bad Request"),
    UNAUTHORIZED(401, "You are unauthorized"),
    FORBIDDEN(403, "Your request is forbidden"),
    INTERNAL_SERVER_ERROR( 500, "Internal Server Error");


    private Integer code;
    private String message;

    ResultStatus( Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultStatus setCode(Integer code) {
        this.code = code;
        return this;
    }

    public ResultStatus setMessage(String message){
        this.message = message;
        return this;
    }
}