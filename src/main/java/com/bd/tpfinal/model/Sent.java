package com.bd.tpfinal.model;

public class Sent extends OrderStatus{
    public Sent() {
        setName("SENT");
    }
    @Override
    public void setName(String name) {
        super.setName(name);
    }


    @Override
    public boolean canFinish() {
        return true;
    }
}
