package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "DeliveryMan")
public class DeliveryMan extends User {

    private int numberOfSuccessOrders = 0;

    private boolean free = true;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfAdmission;

    @DocumentReference
    private List<Order> ordersPending;

    @Override
    public String toString() {
        return super.toString() + " DeliveryMan [numberOfSuccessOrders=" + numberOfSuccessOrders + ", free=" + free + ", dateOfAdmission="
                + dateOfAdmission + ", ordersPending=" + ordersPending + "]";
    }

    public void deductScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore - 2);
    }

    public void addScore() throws Exception {
        int actualScore = this.getScore();
        this.setScore(actualScore + 1);
    }

    public void removePendingOrder(Order order) {
        this.ordersPending.removeIf(o -> o.getNumber() == order.getNumber());
    }
}
