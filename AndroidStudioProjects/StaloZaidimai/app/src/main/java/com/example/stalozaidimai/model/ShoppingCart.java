package com.example.stalozaidimai.model;


import java.util.ArrayList;
import java.util.List;



public class ShoppingCart {

    private List<CartItem> items = new ArrayList<>();


    public ShoppingCart() {
    }

    public ShoppingCart(List<CartItem> items) {
        this.items = items;
    }


}