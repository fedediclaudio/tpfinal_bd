package com.bd.tpfinal.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class BaseResponse<T> {
    private ResponseStatus status = ResponseStatus.OK;
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
