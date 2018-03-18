package com.example.bodzio.doctorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Currency;
import java.util.LinkedList;
import java.util.List;


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
    static final String patientTab = "patient";
    static final String patientID = "id";
    static final String patientName = "name";
    static final String patientSurname = "surname";
    static final String patientPesel = "pesel";
    static final String patientBirthData = "birthData";
    static final String patientAddress = "address";
    static final String patientEmail = "email";
    static final String patientPhone = "phone";


    public DatabaseHelper(Context context) {
        super(context, dbname,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+appointmentTab+" ("+appointmentID+" INTEGER PRIMARY KEY , "+
                startTime+ " INTEGER,"+endTime+ "TEXT,"+duration+"TEXT,"+setAlert+"TEXT"+notes+" TEXT)");

        db.execSQL("CREATE TABLE "+patientTab+" ("+patientID+" INTEGER PRIMARY KEY , "+
                patientName+ " TEXT,"+patientSurname+ "TEXT,"+patientPesel+"INTEGER,"+patientBirthData+"TEXT"+
                patientAddress+"TEXT"+patientEmail+" TEXT," +patientPhone+"INTEGER)");
        //onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ appointmentTab);
        db.execSQL("DROP TABLE IF EXISTS "+ patientTab);
        onCreate(db);
    }

    public void insertPatientTab(String name, String surmane, String pesel, String birthData, String address, String email, String phone){
        Log.d("Logcat", "insert patient table - trial");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(patientName, name);
        contentValues.put(patientSurname, surmane);
        contentValues.put(patientPesel, pesel);
        contentValues.put(patientBirthData, birthData);
        contentValues.put(patientAddress, address);
        contentValues.put(patientEmail, email);
        contentValues.put(patientPhone, phone);
        db.insert(patientTab, null, contentValues);
        db.close();
        Log.d("Logcat", "insert patient table - success");
    }


    //to jest do poprawy
   public Cursor getDatas(){
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor res = db.rawQuery("SELECT * FROM " + patientTab, null);
       return res;
   }
}