package com.bd.tpfinal.model.projections;

import java.time.LocalDate;

public interface OrderMaxPrice {

    String getNumber();

    Integer getTotalPrice();

    LocalDate getDateOfOrder();
}
