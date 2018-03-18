package com.example.bodzio.doctorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, dbname,null,1);
    }

    static final String dbname = "doctorDB";
    SQLiteDatabase db = openOrCreateDatabase(dbname, null,null);

    static final String appointmentTab = "appointment";
    static final String appointmentID = "id";
    static final String patientName = "name";
    static final String patientSurname = "surname";
    static final String date = "date";
    static final String hour = "hour";
    static final String minute = "minute";
    static final String setAlert = "alert";

    @Override
    public void onCreate(SQLiteDatabase db){
        //appointments table
        db.execSQL("CREATE TABLE IF NOT EXISTS "+appointmentTab+" ("+appointmentID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                patientName+" TEXT," + patientSurname+" TEXT,"+
                date+" INTEGER,"+hour+" INTEGER,"+minute+" INTEGER,"+
                setAlert+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public long insertAppointmentData(String name,String surname,long d,int h,int min, boolean alert) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(patientName,name);
        contentValues.put(patientSurname,surname);
        contentValues.put(date,d);
        contentValues.put(hour,h);
        contentValues.put(minute,min);
        contentValues.put(setAlert,alert);

        return db.insert(appointmentTab,null ,contentValues);
    }
}