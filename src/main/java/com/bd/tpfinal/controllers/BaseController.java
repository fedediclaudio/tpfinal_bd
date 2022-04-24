package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import org.springframework.http.HttpStatus;

public abstract class BaseController {

    protected HttpStatus responseStatus(BaseResponse response){
        return ResponseStatus.OK.equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

}
