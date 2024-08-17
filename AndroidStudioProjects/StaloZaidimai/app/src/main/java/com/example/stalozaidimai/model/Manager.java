package com.example.stalozaidimai.model;


import java.time.LocalDate;
import java.util.List;



public class Manager extends User {

    private String employeeId;
    private String medCertificate;
    private LocalDate employmentDate;

    public Manager() {

    }

    public Manager(String login, String password, LocalDate birthDate, String name, String surname) {
        super(login, password, birthDate, name, surname);
    }

    public Manager(String employeeId, String medCertificate, LocalDate employmentDate) {
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    public Manager(String login, String password, LocalDate birthDate, String name, String surname, String employeeId, String medCertificate, LocalDate employmentDate) {
        super(login, password, birthDate, name, surname);
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
    }

    public String toString() {
        return getFullName();
    }
}