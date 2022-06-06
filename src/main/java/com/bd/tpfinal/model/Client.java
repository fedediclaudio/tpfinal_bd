package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;
@Data
public class Client extends User{

    private Date dateOfRegister;

    @DocumentReference
    private List<Order> orders;

    @DBRef
    private List<Address> addresses;

}
