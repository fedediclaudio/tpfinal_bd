package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.enums.OrderStatusAction;
import com.bd.tpfinal.proxy.repositories.command.ChangeStatusCommand;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    protected HttpStatus responseStatus(BaseResponse response){
        return HttpStatus.valueOf(response.getStatus().getCode());
    }

}
