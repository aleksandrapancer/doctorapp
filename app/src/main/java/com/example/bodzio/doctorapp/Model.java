package com.example.bodzio.doctorapp;

//patient table model
public class Model {
    private int Id;
    private  String name;
    private  String surname;
    private String pesel;
    private String day;
    private String month;
    private String year;
    private String address;
    private String email;
    private String phone;

    public Model(int id, String name, String surname, String pesel, String day, String month, String year, String address, String email, String phone) {
        this.Id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.day = day;
        this.month = month;
        this.year = year;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public Model(String name, String surname, String pesel, String day, String month, String year, String address, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.day = day;
        this.month = month;
        this.year = year;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public Model(int id, String name, String surname, String pesel) {
        this.Id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }

    public Model(int id, String name, String surname) {
        this.Id = id;
        this.name = name;
        this.surname = surname;
    }


    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getPesel(){
        return pesel;
    }

    public String getDay() { return day; }

    public String getMonth() { return month; }

    public String getYear() { return year; }

    public String getAddress(){
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}