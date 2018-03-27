package com.example.bodzio.doctorapp;


public class VisitModel {
    private int visitId;
    private int pesel;
    private String note;

    public VisitModel(int visitId, int pesel, String note) {
        this.visitId = visitId;
        this.pesel = pesel;
        this.note = note;
    }

    public int getVisitId(){
        return visitId;
    }

    public int getPesel(){
        return  pesel;
    }

    public String getNote(){
        return note;
    }
}
