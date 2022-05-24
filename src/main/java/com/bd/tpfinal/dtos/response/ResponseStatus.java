package com.bd.tpfinal.dtos.response;

public enum ResponseStatus {
    OK_200(200), OK_201(201),OK_204(204), ERROR_404(404);

    private final int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
