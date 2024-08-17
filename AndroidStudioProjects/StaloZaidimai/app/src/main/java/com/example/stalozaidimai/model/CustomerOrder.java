package com.example.stalozaidimai.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CustomerOrder {

    private int id;
    private LocalDate dateCreated;
    private double totalPrice;





    public CustomerOrder() {
    }

    public CustomerOrder(int id, LocalDate dateCreated, double totalPrice) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;

    }


}
