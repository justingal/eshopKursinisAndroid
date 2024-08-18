package com.example.stalozaidimai.model;

import java.io.Serializable;
import java.util.List;



public class Warehouse implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
        return title +"  "+ address;
    }


}