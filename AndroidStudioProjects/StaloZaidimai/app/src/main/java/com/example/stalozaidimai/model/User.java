package com.example.stalozaidimai.model;



import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;



public class User implements Serializable {


    int id;
    String username;
    String password;
    LocalDate birthDate;
    String name;
    String surname;


    public User() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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