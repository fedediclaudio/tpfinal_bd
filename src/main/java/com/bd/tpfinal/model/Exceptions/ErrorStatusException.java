package com.bd.tpfinal.model.Exceptions;

public class ErrorStatusException extends Exception
{
    private String status_str;

    public ErrorStatusException(String message, String status_str)
    {
        super("error en el nombre del status: "+status_str);
        this.status_str = status_str;
    }

    public String getStatus_str()
    {
        return status_str;
    }
}
