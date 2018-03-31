package com.example.bodzio.doctorapp;


public class Model {
    private int Id;
    private  String name;
    private  String surname;
    private String pesel;
    private String birthData;
    private String address;
    private String email;
    private int phone;

    public Model(int id, String name, String surname, String pesel, String birthData, String address, String email, int phone) {
        this.Id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.birthData = birthData;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public Model(String name, String surname, String pesel, String birthData, String address, String email, int phone) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.birthData = birthData;
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

    public String getBirthData() {
        return birthData;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }
}