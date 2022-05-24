package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.response.BaseResponse;
import org.springframework.http.HttpStatus;

public abstract class BaseController {

    protected HttpStatus responseStatus(BaseResponse response){
        return HttpStatus.valueOf(response.getStatus().getCode());
    }

}
