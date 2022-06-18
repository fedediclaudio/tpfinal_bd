package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "client")
public class Client extends User {

    private Date dateOfRegister;

    @DBRef
    @JsonBackReference
    private List<Order> orders;

    @DBRef
    private List<Address> addresses;

    public void decreaseScore() throws Exception {
        int actualScore = this.getScore();
        if (actualScore > 0)
            this.setScore(actualScore - 1);
    }

    public void increaseScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }
}
