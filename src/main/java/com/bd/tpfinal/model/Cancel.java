package com.bd.tpfinal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;


public class Cancel extends OrderStatus {
    public Cancel() {
        setName("CANCELLED");
    }

    @JsonProperty("cancelled_by_client")
    private boolean cancelledByClient;

    public boolean isCancelledByClient() {
        return cancelledByClient;
    }

    public void setCancelledByClient(boolean cancelledByClient) {
        this.cancelledByClient = cancelledByClient;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
