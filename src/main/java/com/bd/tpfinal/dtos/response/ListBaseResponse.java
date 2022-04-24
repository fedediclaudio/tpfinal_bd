package com.bd.tpfinal.dtos.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({"status","message","size","data"})
public abstract class ListBaseResponse extends BaseResponse<List> {

    private int size;

    public int getSize() {
        return getData().size();
    }
}
