package com.example.stalozaidimai.model;


import java.time.LocalDate;
import java.util.List;


public class Customer extends User {

    private String address;
    private String cardNo;


    public Customer() {

    }


    public Customer(String login, String password, LocalDate birthDate, String name, String surname) {
        super(login, password, birthDate, name, surname);
    }



    public Customer(String login, String password, LocalDate birthDate, String name, String surname, String address, String cardNo) {
        super(login, password, birthDate, name, surname);
        this.address = address;
        this.cardNo = cardNo;
    }

}