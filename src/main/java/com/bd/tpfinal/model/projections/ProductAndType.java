package com.bd.tpfinal.model.projections;

import org.springframework.beans.factory.annotation.Value;

public interface ProductAndType {

    String getName();

    Float getPrice();

    @Value("#{target.type.name}")
    String getType();
}
