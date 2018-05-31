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
    static final String patientBirthDataDay = "birthDataDay";
    static final String patientBirthDataMonth = "birthDataMonth";
    static final String patientBirthDataYear = "birthDataYear";
    static final String patientAddress = "address";
    static final String patientEmail = "email";
    static final String patientPhone = "phone";

    //visit table
    static final String visitID = "id";
    static final String visitPatientPesel = "pesel";
    static final String visitNotes = "notes";
    static final String visitData = "data";
    static final String visitHour = "hour";
    static final String visitMinute = "minute";

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
                    patientName+ " TEXT,"+patientSurname+" TEXT,"+patientPesel+" TEXT,"+patientBirthDataDay+" TEXT,"+
                    patientBirthDataMonth+" TEXT,"+patientBirthDataYear+" TEXT,"+
                    patientAddress+" TEXT,"+patientEmail+" TEXT," +patientPhone+" TEXT)";

    private static final String VISIT_PATIENT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+VISIT_TABLE+" ("+visitID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    visitPatientPesel+ " TEXT,"+visitNotes+ " TEXT," + visitData + " INTEGER," + visitHour+" TEXT,"+visitMinute+
                    " TEXT, FOREIGN KEY ("+visitPatientPesel+") REFERENCES "+APP_TABLE+"("+appointmentPesel+")," +
                    "FOREIGN KEY ("+visitPatientPesel+") REFERENCES "+PATIENT_TABLE+"("+patientPesel+"))";

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
    public long insertPatientTab(String name, String surname, String pesel, String day, String month, String year, String address, String email, String phone){
        Log.d("Logcat", "insert patient table - trial");

        ContentValues contentValues = new ContentValues();
        contentValues.put(patientName, name);
        contentValues.put(patientSurname, surname);
        contentValues.put(patientPesel, pesel);
        contentValues.put(patientBirthDataDay, day);
        contentValues.put(patientBirthDataMonth, month);
        contentValues.put(patientBirthDataYear, year);
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

    public long insertVisitTab(String pesel, long data, int hour, int minute){
        ContentValues contentValues = new ContentValues();
        contentValues.put(visitPatientPesel, pesel);
        contentValues.put(visitNotes, "");
        contentValues.put(visitData, data);
        contentValues.put(visitHour, hour);
        contentValues.put(visitMinute, minute);

        return mDb.insert(VISIT_TABLE, null, contentValues);
    }


    //update values
    public long updatePatientTable(int id, String name, String surname, String pesel, String day, String month, String year, String address, String email, String phone){

        Log.d("Logcat", "update patient table - trial");

        ContentValues contentValues = new ContentValues();
        contentValues.put(patientName, name);
        contentValues.put(patientSurname, surname);
        contentValues.put(patientPesel, pesel);
        contentValues.put(patientBirthDataDay, day);
        contentValues.put(patientBirthDataMonth, month);
        contentValues.put(patientBirthDataYear, year);
        contentValues.put(patientAddress, address);
        contentValues.put(patientEmail, email);
        contentValues.put(patientPhone, phone);

        return mDb.update(PATIENT_TABLE, contentValues, patientID +"= "+id, null);
    }

    public long updateVisitTable(String pesel, long date, String hour, String minute, String note){

        Log.d("Logcat", "update visit table - trial");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        month = month +1;
        int year = calendar.get(java.util.Calendar.YEAR);

        ContentValues contentValues = new ContentValues();
        contentValues.put(visitNotes, note);

        ArrayList<VisitModel> list = new ArrayList<>();

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + VISIT_TABLE + " WHERE " + visitPatientPesel + " = '" + pesel + "' AND "
                + visitHour + " = '" + hour + "' AND " + visitMinute + " = '" + minute + "' AND "
                + "strftime('%d%m%Y', "+ visitData +" / 1000, 'unixepoch') == '"+ String.format("%02d%02d%d", day,month,year)+"'", null);

        while (cursor.moveToNext()){
            list.add(new VisitModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }

        int id = list.get(0).getVisitId();

        return mDb.update(VISIT_TABLE, contentValues, visitID + " = " + id, null);
    }

    //delete values
    public void deleteData(int id) {
        mDb.delete(PATIENT_TABLE, patientID + "=" + id, null);
    }


    public Cursor displayName(int id){
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE + " WHERE " + patientID + " = '" + id + "'", null);

        return cursor;
    }


    //select from tables
    //patient table
    public ArrayList<Model> getAllDataPatient(){
        ArrayList<Model> list = new ArrayList<>();
        Cursor res = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE, null);
        while (res.moveToNext()){
            list.add((new Model(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4), res.getString(5),
                    res.getString(6), res.getString(7), res.getString(8), res.getString(9))));
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
                    res.getString(6), res.getString(7), res.getString(8), res.getString(9))));        }

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
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(visitDate);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);
        int month = fullTime.getMonth();
        int year = c.get(java.util.Calendar.YEAR);
        ArrayList<AppModel> list = new ArrayList<>();
        String sql = "SELECT * FROM " + APP_TABLE +
                " WHERE " +
                "strftime('%d%m%Y', "+ appointmentDate +" / 1000, 'unixepoch') == '"+ String.format("%02d%02d%d", day,month + 1,year)+"'";
        Cursor res = mDb.rawQuery(sql, null);
        while (res.moveToNext()){
            list.add((new AppModel(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4), res.getString(5), res.getString(6))));
        }

        return list;
    }



    //app table ---- calendarview
    public ArrayList<AppModel> getDataByPickedDate(long appDate){
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(appDate);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);
        int month = c.get(java.util.Calendar.MONTH);
        int year = c.get(java.util.Calendar.YEAR);
        ArrayList<AppModel> list = new ArrayList<>();
        String sql = "SELECT * FROM " + APP_TABLE +
                " WHERE " +
                "strftime('%d%m%Y', "+ appointmentDate +" / 1000, 'unixepoch') == '"+ String.format("%02d%02d%d", day,month + 1,year)+"'";
        Cursor res = mDb.rawQuery(sql, null);
        while (res.moveToNext()){
            list.add((new AppModel(res.getInt(0), res.getString(1), res.getString(2),
                    res.getString(3), res.getString(4))));
        }

        return list;
    }


    //get names of all patients visit with alert
    public String getNameWitchAppointmentNotification(){
        ArrayList<AppModel> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        long date = calendar.getTimeInMillis();

        Date fullTime = new Date(date);
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(date);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);
        int month = fullTime.getMonth();
        int year = c.get(java.util.Calendar.YEAR);
        String sql = "SELECT * FROM " + APP_TABLE +
                " WHERE " +
                "strftime('%d%m%Y', "+ appointmentDate +" / 1000, 'unixepoch') == '"+ String.format("%02d%02d%d", day,month + 1,year)+"'"
                + " AND " + appointmentSetAlert + " = '1'";
        Cursor res = mDb.rawQuery(sql, null);

        while (res.moveToNext()){
            list.add(new AppModel(res.getString(1), res.getString(2)));
        }

        String notification = "";
        int sizeOfTheList = list.size();

        if(sizeOfTheList!=0) {
            notification = "Witaj dzisiaj masz powiadomienia o wizycie: ";
            for (int i = 0; i < list.size(); i++) {
                notification = notification + list.get(i).getName() + " " + list.get(i).getSurname() + "  ";
            }
        }
        else{
            notification = "Witaj. Dzisiaj nie masz powiadomień o wizytach.";
        }
        return notification;
    }


    public Cursor getPesel() {
        Cursor res = mDb.rawQuery("SELECT "+patientPesel+" FROM " + PATIENT_TABLE + "", null);
        return res;
    }

    public Cursor getSurname(String p) {
        Cursor res = mDb.rawQuery("SELECT "+patientSurname+" FROM " + PATIENT_TABLE + " WHERE " + patientPesel + " = ' " + p +" ' " , null);
        return res;
    }

    public Cursor getName(String p) {
        Cursor res = mDb.rawQuery("SELECT "+patientName+" FROM " + PATIENT_TABLE + " WHERE " + patientPesel + " = ' " + p +" ' " , null);
        return res;
    }

    //visit table
    public Cursor getNotesByPeselVisit(String pesel){
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + VISIT_TABLE + " WHERE " + visitPatientPesel + " = '" + pesel + "'", null);
        return cursor;
    }

    public Cursor getDates() {
        Cursor res =  mDb.rawQuery( "SELECT " +appointmentDate+ " FROM "+ APP_TABLE +"", null );
        return res;
    }

    public Cursor getAllVisitForPatient(String pesel) {
        Cursor res =  mDb.rawQuery( "SELECT * FROM "+ APP_TABLE +" WHERE " + appointmentPesel + " = '" + pesel + "'", null );
        return res;
    }


}
