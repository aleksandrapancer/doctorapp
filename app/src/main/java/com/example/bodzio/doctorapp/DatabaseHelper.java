package com.example.bodzio.doctorapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbname = "doctorDB";
    //appointments table
    static final String appointmentTab = "appointment";
    static final String appointmentID = "id";
    static final String startTime = "start";
    static final String endTime = "end";
    static final String duration = "duration";
    static final String setAlert = "alert";
    static final String notes = "notes";

    //patients table
    //...


    public DatabaseHelper(Context context) {
        super(context, dbname,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+appointmentTab+" ("+appointmentID+" INTEGER PRIMARY KEY , "+
                startTime+ " INTEGER,"+endTime+ "TEXT,"+duration+"TEXT,"+setAlert+"TEXT"+notes+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}