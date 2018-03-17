package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
}


    public void openCalendar(View view) {
        Intent intent = new Intent(this,  Calendar.class);
        startActivity(intent);
    }


    public void openAppointmentCreator(View view) {
        Intent intent = new Intent(this,  AppointmentCreator.class);
        startActivity(intent);
    }
}
