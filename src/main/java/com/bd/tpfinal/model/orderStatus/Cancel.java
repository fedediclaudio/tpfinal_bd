package com.bd.tpfinal.model.orderStatus;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Cancel")
public class Cancel extends OrderStatus {
    private boolean cancelledByClient;

    public Cancel() {}

    public Cancel(Order order) {
        super("Cancel", LocalDate.now(), order);
    }

    public boolean isCancelledByClient() {
        return cancelledByClient;
    }

    public void setCancelledByClient(boolean cancelledByClient) {
        this.cancelledByClient = cancelledByClient;
    }
}
