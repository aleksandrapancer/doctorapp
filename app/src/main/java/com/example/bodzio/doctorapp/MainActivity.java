package com.example.bodzio.doctorapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), 100, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
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


    public void addNotes(View view) {
        Intent intent = new Intent(this,  AddNotes.class);
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
