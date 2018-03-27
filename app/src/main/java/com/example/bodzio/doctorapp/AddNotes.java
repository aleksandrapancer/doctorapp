package com.example.bodzio.doctorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AddNotes extends AppCompatActivity {

    private DatabaseManager dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();
    }


}
