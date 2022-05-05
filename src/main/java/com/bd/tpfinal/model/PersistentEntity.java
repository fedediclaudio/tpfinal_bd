package com.bd.tpfinal.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.UUID;


public abstract class PersistentEntity implements Serializable {

    @Id
    private String id = String.valueOf(UUID.randomUUID());

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
