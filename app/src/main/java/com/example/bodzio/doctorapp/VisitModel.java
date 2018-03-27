package com.example.bodzio.doctorapp;

/**
 * Created by Kamila on 2018-03-27.
 */

public class VisitModel {
    private int visitId;
    private int appId;
    private int patientId;
    private String note;

    public VisitModel(int visitId, int appId, int patientId, String note) {
        this.visitId = visitId;
        this.appId = appId;
        this.patientId = patientId;
        this.note = note;
    }

    public int getVisitId(){
        return visitId;
    }

    public int getAppId(){
        return  appId;
    }

    public int getPatientId(){
        return patientId;
    }

    public String getNote(){
        return note;
    }
}
