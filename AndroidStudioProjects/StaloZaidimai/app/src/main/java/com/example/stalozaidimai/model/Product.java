package com.example.stalozaidimai.model;



import java.io.Serializable;



public class Product implements Serializable {


    int id;
    String title;
    String description;
    String author;
    double price;


    public Product() {
    }

    public Product(int id, String title, String description, String author, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public Product(String title, String description, String author, double price) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return title + " " + price + "€";
    }

}