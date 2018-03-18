package com.example.bodzio.doctorapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {

    //appointments table
    static final String appointmentID = "id";
    static final String name = "name";
    static final String surname = "surname";
    static final String date = "date";
    static final String hour = "hour";
    static final String minute = "minute";
    static final String setAlert = "alert";

    //patients table
    static final String patientID = "id";
    static final String patientName = "name";
    static final String patientSurname = "surname";
    static final String patientPesel = "pesel";
    static final String patientBirthData = "birthData";
    static final String patientAddress = "address";
    static final String patientEmail = "email";
    static final String patientPhone = "phone";

    private static final String TAG = "dbManager";
    private DatabaseHelper mDbHelper;
    public SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "doctorDB";
    public static final String APP_TABLE = "appointmentTable";
    public static final String PATIENT_TABLE = "patientTable";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String APP_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+APP_TABLE+" ("+appointmentID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    name+" TEXT,"+surname+" TEXT,"+
                    date+ " INTEGER,"+hour+" INTEGER,"+minute+" INTEGER,"+setAlert+" INTEGER)";

    private static final String PATIENT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+PATIENT_TABLE+" ("+patientID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    patientName+ " TEXT,"+patientSurname+" TEXT,"+patientPesel+" TEXT,"+patientBirthData+" TEXT,"+
                    patientAddress+" TEXT,"+patientEmail+" TEXT," +patientPhone+" TEXT)";

    static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(APP_TABLE_CREATE);
            db.execSQL(PATIENT_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + APP_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
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

        Log.d("Logcat", "insert patient table - success");
        return mDb.insert(PATIENT_TABLE, null, contentValues);
    }

    public long insertAppointmentTab(String n, String s, long d, int h, int min, int alert){
        ContentValues contentValues = new ContentValues();
        contentValues.put(name, n);
        contentValues.put(surname, s);
        contentValues.put(date, d);
        contentValues.put(hour,h);
        contentValues.put(minute, min);
        contentValues.put(setAlert, alert);

        return mDb.insert(APP_TABLE, null, contentValues);
    }

    public Cursor getData(){
        Cursor res = mDb.rawQuery("SELECT * FROM " + PATIENT_TABLE, null);
        return res;
    }
}
