package com.example.bodzio.doctorapp;

//appointment table model
public class AppModel {
    private int id;
    private String name;
    private String surname;
    private String pesel;
    private String date;
    private String hour;
    private String minute;
    private int setAlat;

    public AppModel(int id, String name,  String surname, String pesel){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }

    public AppModel(int id, String name,  String surname, String pesel, String date){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.date = date;
    }

    public AppModel(String name,  String surname, String pesel){
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
    }

    public AppModel(String name,  String surname){
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPesel() {
        return pesel;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public int getSetAlat() {
        return setAlat;
    }
}
