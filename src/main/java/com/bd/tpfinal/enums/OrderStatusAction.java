package com.bd.tpfinal.enums;

import java.util.Arrays;

public enum OrderStatusAction {
    FINISH(new OrderStatusAction[]{}),
    CANCEL(new OrderStatusAction[]{}),
    REFUSE(new OrderStatusAction[]{}),
    DELIVER(new OrderStatusAction[]{FINISH}),
    ASSIGN(new OrderStatusAction[]{DELIVER, REFUSE, CANCEL});

    private OrderStatusAction[] nextStatus;
    OrderStatusAction(OrderStatusAction[] orderStatusActions) {
        this.nextStatus = orderStatusActions;
    }

    public boolean hasNext(OrderStatusAction orderStatusAction){
        return Arrays.asList(nextStatus).contains(orderStatusAction);
    }
}
