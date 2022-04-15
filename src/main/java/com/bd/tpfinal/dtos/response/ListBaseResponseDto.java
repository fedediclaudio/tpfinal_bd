package com.bd.tpfinal.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({"status","message","size","data"})
public abstract class ListBaseResponseDto extends BaseResponseDto<List> {

    private int size;

    public int getSize() {
        return getData().size();
    }
}
