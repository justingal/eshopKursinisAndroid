package com.example.stalozaidimai.model;


public
class CartItem {
    private int productId;
    private String name;
    private double price;

    public CartItem(int productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public CartItem() {
    }


    @Override
    public String toString() {
        return name + " " + price;
    }

}
