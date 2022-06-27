package com.bd.tpfinal.model;

import com.bd.tpfinal.repositories.OrderStatusRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Optional;

@Data
@Document(collection = "Pending")
public class Pending extends OrderStatus {

    public Pending() {
        this.setName("Pending");
    }

    public Pending(Order order) {
        super(order, "Pending");
    }

    public Pending(Order order, Date startDate) {
        super(order, "Pending", startDate);
    }

    @Override
    public boolean canAssign() {
        return true;
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public boolean assign(DeliveryMan deliveryMan) throws Exception {
        if (!deliveryMan.isFree())
            throw new Exception("El deliveryman no está libre ");
        deliveryMan.addOrder(this.getOrder());
        deliveryMan.setFree(false);
        this.getOrder().setDeliveryMan(deliveryMan);
        OrderStatus orderStatus = new Assigned(this.getOrder(), new Date());
        this.getOrder().setStatus(orderStatus);
        return true;
    }
}
