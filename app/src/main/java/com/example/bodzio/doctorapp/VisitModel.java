package com.example.bodzio.doctorapp;


public class VisitModel {
    private int visitId;
    private String pesel;
    private String note;

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
}
