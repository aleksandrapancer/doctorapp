package com.example.bodzio.doctorapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.HOUR_OF_DAY, 10);


            Intent notifyIntent = new Intent(this, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (getApplicationContext(), 100, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    86400000, pendingIntent);


            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this,  AppointmentCalendar.class);
        startActivity(intent);
    }

    public void openAppointmentCreator(View view) {
        Intent intent = new Intent(this,  AppointmentCreator.class);
        startActivity(intent);
    }

    public void addPatient(View view) {
        Intent intent = new Intent(this,  AddPatient.class);
        startActivity(intent);
    }

    public void showPatients(View view) {
        Intent intent = new Intent(this,  ShowPatients.class);
        startActivity(intent);
    }

    public void showVisits(View view) {
        Intent intent = new Intent(this,  ShowVisits.class);
        startActivity(intent);
    }
}
