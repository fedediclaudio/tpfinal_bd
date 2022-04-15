package com.bd.tpfinal.exceptions.parameters;

public class ParameterErrorException extends Exception {
    public ParameterErrorException(String parameter) {
        super("Need a valid value for parameter '" + parameter+"'.");
    }
}
