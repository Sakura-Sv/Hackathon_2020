package com.testdb.demo.utils.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Result<T> {

    private Integer status;
    private String message;
    private T data;

    private Result(ResultStatus resultStatus, T data) {
        this.status = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    public static Result<Void> success() {
        return new Result<Void>(ResultStatus.SUCCESS, null);
    }

    public static Result<Void> success(ResultStatus resultStatus) {
        return new Result<Void>(ResultStatus.SUCCESS, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultStatus.SUCCESS, data);
    }

    public static <T> Result<T> success(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return success(data);
        }
        return new Result<T>(resultStatus, data);
    }

    public static <T> Result<T> failure() {
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR, null);
    }

    public static <T> Result<T> failure(ResultStatus resultStatus) {
        return failure(resultStatus, null);
    }

    public static <T> Result<T> failure(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR, null);
        }
        return new Result<T>(resultStatus, data);
    }
}