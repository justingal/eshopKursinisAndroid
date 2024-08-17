package com.example.stalozaidimai.model;


import java.util.List;



public class BoardGame extends Product {
    private List<Review> reviews;
    private String playersQuantity;
    private String gameDuration;


    public BoardGame() {

    }


    public BoardGame(int id, String title, String description, String author, int price, String playersQuantity, String gameDuration) {
        super(id, title, description, author, price);
        this.playersQuantity = playersQuantity;
        this.gameDuration = gameDuration;
    }

    public BoardGame(String title, String description, String author, double price, String playersQuantity, String gameDuration) {
        super(title, description, author, price);
        this.playersQuantity = playersQuantity;
        this.gameDuration = gameDuration;
    }

}