package com.example.bodzio.doctorapp;

//visit table model
public class VisitModel {
    private int visitId;
    private String pesel;
    private String note;
    private String data;
    private  String hour;
    private String minute;

    public VisitModel(int visitId, String pesel, String note, String data, String hour, String minute) {
        this.visitId = visitId;
        this.pesel = pesel;
        this.note = note;
        this.data = data;
        this.hour = hour;
        this.minute = minute;
    }

    public VisitModel(int visitId, String pesel, String note) {
        this.visitId = visitId;
        this.pesel = pesel;
        this.note = note;
    }

    public int getVisitId(){
        return visitId;
    }

    public String getPesel(){
        return  pesel;
    }

    public String getNote(){
        return note;
    }

    public String getData() { return data; }

    public String getHour() { return hour; }

    public String getMinute() { return minute; }
}
