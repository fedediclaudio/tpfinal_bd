package com.bd.tpfinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
@Data
@Document(collection = "Client")
public class Client extends User{

    private Date dateOfRegister;

    @DBRef
    private List<Order> orders;

    @DBRef
    private List<Address> addresses;
    /*
    public Client (String name, String email, String userName, String password, Date dateOfBirth){
        super(name, email, userName, password, dateOfBirth);
        this.dateOfRegister = Calendar.getInstance().getTime();
        this.orders = new ArrayList<>();
    }
    */
    public void decreaseScore() throws Exception {
        int actualScore = this.getScore();
        if (actualScore > 0) // para que no quede un score negativo
            this.setScore(actualScore - 1);
    }
    public void increaseScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }
}
