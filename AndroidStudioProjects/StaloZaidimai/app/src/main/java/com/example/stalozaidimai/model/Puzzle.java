package com.example.stalozaidimai.model;


import java.util.List;



public class Puzzle extends Product {

    private int piecesQuantity;
    private String puzzleSize;
    private String puzzleMaterial;

    public Puzzle() {

    }




    public Puzzle(int id, String title, String description, String author, double price, int piecesQuantity, String puzzleSize, String puzzleMaterial) {
        super(id, title, description, author, price);
        this.piecesQuantity = piecesQuantity;
        this.puzzleSize = puzzleSize;
        this.puzzleMaterial = puzzleMaterial;
    }

    public Puzzle(String title, String description, String author, double price, int piecesQuantity, String puzzleSize, String puzzleMaterial) {
        super(title, description, author, price);
        this.piecesQuantity = piecesQuantity;
        this.puzzleSize = puzzleSize;
        this.puzzleMaterial = puzzleMaterial;
    }



}