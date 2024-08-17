package com.example.stalozaidimai.model;



import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;



public abstract class User implements Serializable {


    int id;
    String username;
    String password;
    LocalDate birthDate;
    String name;
    String surname;


    public User() {
    }

    public User(int id, String login, String password, LocalDate birthDate, String name, String surname) {
        this.id = id;
        this.username = login;
        this.password = password;
        this.birthDate = birthDate;
        this.name = name;
        this.surname = surname;}

    public User(String login, String password, LocalDate birthDate, String name, String surname) {
        this.username = login;
        this.password = password;
        this.birthDate = birthDate;
        this.name = name;
        this.surname = surname;
    }

    public User(int id, String login, String password, LocalDate birthDate) {
        this.id = id;
        this.username = login;
        this.password = password;
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}