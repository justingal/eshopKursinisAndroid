package com.example.stalozaidimai.model;


import java.time.LocalDate;



public class Admin extends Manager {
    public Admin(String employeeId, String medCertificate, LocalDate employmentDate) {
        super(employeeId, medCertificate, employmentDate);
    }

    public Admin(String username, String password, LocalDate birthDate, String name, String surname) {
        super(username, password, birthDate, name, surname);
    }

    public Admin(String username, String password, LocalDate birthDate, String name, String surname, String employeeId, String medCertificate, LocalDate employmentDate) {
        super(username, password, birthDate, name, surname, employeeId, medCertificate, employmentDate);
    }

    public Admin() {
        super();
    }
}