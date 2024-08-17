package com.example.stalozaidimai.model;



import java.util.List;


public class Dice extends Product {

    int diceNumber;



    public Dice() {

    }



    public Dice(int id, String title, String description, String author, double price, int diceNumber) {
        super(id, title, description, author, price);
        this.diceNumber = diceNumber;
    }

    public Dice(String title, String description, String author, double price, int diceNumber) {
        super(title, description, author, price);
        this.diceNumber = diceNumber;
    }

}

