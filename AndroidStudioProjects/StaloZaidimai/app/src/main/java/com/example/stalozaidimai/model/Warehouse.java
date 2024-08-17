package com.example.stalozaidimai.model;

import java.io.Serializable;
import java.util.List;



public class Warehouse implements Serializable {


    private int id;
    private String title;
    private String address;

    public Warehouse() {
    }

    public Warehouse(int id, String title, String address) {
        this.id = id;
        this.title = title;
        this.address = address;

    }


    public Warehouse(String title, String address) {
        this.title = title;
        this.address = address;
    }

    @Override
    public String toString() {
        return title;
    }


}