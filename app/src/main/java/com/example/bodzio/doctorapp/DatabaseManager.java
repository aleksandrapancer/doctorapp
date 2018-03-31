package com.example.bodzio.doctorapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.*;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    //appointments table
    static final String appointmentID = "id";
    static final String appointmentName = "name";
    static final String appointmentSurname = "surname";
    static final String appointmentPesel = "pesel";
    static final String appointmentDate = "date";
    static final String appointmentHour = "hour";
    static final String appointmentMinute = "minute";
    static final String appointmentSetAlert = "alert";

    //patients table
    static final String patientID = "id";
    static final String patientName = "name";
    static final String patientSurname = "surname";
    static final String patientPesel = "pesel";
    static final String patientBirthData = "birthData";
    static final String patientAddress = "address";
    static final String patientEmail = "email";
    static final String patientPhone = "phone";

    //visit table
    static final String visitID = "id";
    static final String visitPatientPesel = "pesel";
    static final String visitNotes = "notes";

    private static final String TAG = "dbManager";
    private DatabaseHelper mDbHelper;
    public SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "doctorDB";
    public static final String APP_TABLE = "appointmentTable";
    public static final String PATIENT_TABLE = "patientTable";
    public static final String VISIT_TABLE = "visitTable";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String APP_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+APP_TABLE+" ("+appointmentID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    appointmentName+" TEXT,"+appointmentSurname+" TEXT,"+appointmentPesel+" TEXT,"+
                    appointmentDate+ " INTEGER,"+appointmentHour+" INTEGER,"+appointmentMinute+" INTEGER,"+appointmentSetAlert+" INTEGER)";

    private static final String PATIENT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+PATIENT_TABLE+" ("+patientID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    patientName+ " TEXT,"+patientSurname+" TEXT,"+patientPesel+" TEXT,"+patientBirthData+" TEXT,"+
                    patientAddress+" TEXT,"+patientEmail+" TEXT," +patientPhone+" TEXT)";

    private static final String VISIT_PATIENT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+VISIT_TABLE+" ("+visitID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    visitPatientPesel+ " TEXT,"+visitNotes+" TEXT)";

    static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(APP_TABLE_CREATE);
            db.execSQL(PATIENT_TABLE_CREATE);
            db.execSQL(VISIT_PATIENT_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + VISIT_TABLE);
            onCreate(db);
        }
    }

    public DatabaseManager(Context ctx) {
        this.mCtx = ctx;
    }

    public DatabaseManager open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //insert new values
    public long insertPatientTab(String name, String surname, String pesel, String birthData, String address, String email, String phone){
        Log.d("Logcat", "insert patient table - trial");

        ContentValues contentValues = new ContentValues();
        contentValues.put(patientName, name);
        contentValues.put(patientSurname, surname);
        contentValues.put(patientPesel, pesel);
        contentValues.put(patientBirthData, birthData);
        contentValues.put(patientAddress, address);
        contentValues.put(patientEmail, email);
        contentValues.put(patientPhone, phone);

        return mDb.insert(PATIENT_TABLE, null, contentValues);
    }

    public long insertAppointmentTab(String name, String surname,String pesel, long date, int hour, int minute, int alert){
        Log.d("Logcat", "insert appointment table - trial");

        ContentValues contentValues = new ContentValues();
        contentValues.put(appointmentName, name);
        contentValues.put(appointmentSurname, surname);
        contentValues.put(appointmentPesel, pesel);
        contentValues.put(appointmentDate, date);
        contentValues.put(appointmentHour,hour);
        contentValues.put(appointmentMinute, minute);
        contentValues.put(appointmentSetAlert, alert);

        Log.d("Logcat", "insert appointment table - success");
        return mDb.insert(APP_TABLE, null, contentValues);
    }

    public long insertVisitTab(String pesel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(visitPatientPesel, pesel);
        contentValues.put(visitNotes, "");

        return mDb.insert(VISIT_TABLE, null, contentValues);
    }


    //update values
    public long updatePatientTable(int id, String name, String surname, String pesel, String birthData, String address, String email, String phone){

        Log.d("Logcat", "update patient table - trial");

        ContentValues contentValues = new ContentValues();
        contentValues.put(patientName, name);
        contentValues.put(patientSurname, surname);
        contentValues.put(patientPesel, pesel);
        contentValues.put(patientBirthData, birthData);
        contentValues.put(patientAddress, address);
        contentValues.put(patientEmail, email);
        contentValues.put(patientPhone, phone);

        return mDb.update(PATIENT_TABLE, contentValues, patientID +"= "+id, null);
        //Log.d("Logcat", "update patient table - success");
    }

    public long updateVisitTable(String pesel, String note){

        Log.d("Logcat", "update visit table - trial");

        ContentValues contentValues = new ContentValues();
        contentValues.put(visitNotes, note);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + VISIT_TABLE + " WHERE " + visitPatientPesel + " = '" + pesel + "'", null);
        int e=cursor.getCount();

        return mDb.update(VISIT_TABLE, contentValues, visitPatientPesel + "= " + pesel, null);
        //  Log.d("Logcat", "update visit table - success");
    }


    //delete values
    public void deleteData(int id) {
        mDb.delete(PATIENT_TABLE, patientID + "=" + id, null);
    }


    //select from tables
    //patient table
    public ArrayList<Model> getAllDataPatient(){
        ArrayList<Model> list = new ArrayList<>();
        Cursor res = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE, null);
        while (res.moveToNext()){
            list.add((new Model(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4), res.getString(5),
                    res.getString(6), res.getInt(7))));
        }

        return list;
    }

    public ArrayList<Model> getDataPatient(String surname, String pesel) {
        ArrayList<Model> list = new ArrayList<>();
        Cursor res = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE + " WHERE " + patientSurname + " = '" + surname + "'" + " AND " + patientPesel + " = '" + pesel + "'", null);
        while (res.moveToNext()){
            list.add((new Model(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3))));        }

        return list;
    }

    public ArrayList<Model> getDataBySurnamePatient(String surname) {
        ArrayList<Model> list = new ArrayList<>();
        Cursor res = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE + " WHERE " + patientSurname + " = '" + surname + "'", null);
        while (res.moveToNext()){
            list.add((new Model(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3))));        }

        return list;
    }

    public ArrayList<Model> getDataByIdPatient(int id) {
        ArrayList<Model> list = new ArrayList<>();
        Cursor res = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE + " WHERE " + patientID + " = '" + id + "'", null);
        while (res.moveToNext()){
            list.add((new Model(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4), res.getString(5),
                    res.getString(6), res.getInt(7))));        }

        return list;
    }

    public ArrayList<Model> getDataByPeselPatient(String pesel) {
        ArrayList<Model> list = new ArrayList<>();
        Cursor res = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE + " WHERE " + patientPesel + " = '" + pesel + "'", null);
        while (res.moveToNext()){
            list.add((new Model(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3))));
        }

        return list;
    }

    public boolean ifPatientNotExist(String pesel) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE + " WHERE " + patientPesel + " = '" + pesel + "'", null);
        if (cursor.getCount() == 0){
            return true;
        }
        else{
            return false;
        }
    }


    //app table
    public ArrayList<AppModel> getDataByDateApp(long visitDate){
        Date fullTime = new Date(visitDate);
        int month2 = fullTime.getMonth();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(visitDate);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(java.util.Calendar.YEAR);
        ArrayList<AppModel> list = new ArrayList<>();
        String sql = "SELECT * FROM " + APP_TABLE +
                " WHERE " +
                "strftime('%d%m%Y', "+ appointmentDate +" / 1000, 'unixepoch') == '"+ String.format("%02d%02d%d", day,month2 + 1,year)+"'";
        Cursor res = mDb.rawQuery(sql, null);
        while (res.moveToNext()){
            list.add((new AppModel(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4))));
        }

        return list;
    }


    //method for searching appointments by date selected in calendar
    public Cursor getAppointmentByDate(long selectedDate) {
        Cursor res = mDb.rawQuery("SELECT * FROM " + APP_TABLE + " WHERE " + date + " = '" + selectedDate + "'", null);
        return res;

    //visit table
    public Cursor getNotesByPeselVisit(String pesel){
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + VISIT_TABLE + " WHERE " + visitPatientPesel + " = '" + pesel + "'", null);

        Cursor t = mDb.rawQuery("SELECT * FROM " + VISIT_TABLE, null);
        int r = cursor.getCount();
        int w = t.getCount();
        return cursor;
    }

    public Cursor getDates() {
        Cursor res =  mDb.rawQuery( "SELECT " +date+ " FROM "+ APP_TABLE +"", null );
        return res;
    }


}
