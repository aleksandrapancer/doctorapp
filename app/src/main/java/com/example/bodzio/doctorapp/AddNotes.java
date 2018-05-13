package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNotes extends AppCompatActivity {

    private DatabaseManager dbHelper;
    Calendar calendar = Calendar.getInstance();
    final int d = calendar.get(Calendar.DAY_OF_MONTH);
    final int m = calendar.get(Calendar.MONTH);
    final int y = calendar.get(Calendar.YEAR);
    EditText noteText;
    String patientPesel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        noteText = findViewById(R.id.notesText);
    }

    public void addNoteToPatient(View v) {
        String date;
        if(d<10 && m<10){
            date = "0"+String.valueOf(d)+".0"+String.valueOf(m)+"."+String.valueOf(y)+"\n";
        }else if(m<10){
            date = String.valueOf(d)+".0"+String.valueOf(m)+"."+String.valueOf(y)+"\n";
        }else if(d<10){
            date = "0"+String.valueOf(d)+"."+String.valueOf(m)+"."+String.valueOf(y)+"\n";
        }else
            date = String.valueOf(d)+"."+String.valueOf(m)+"."+String.valueOf(y)+"\n";

        String note = date+noteText.getText().toString();
        patientPesel = ShowVisits.patientPesel;

        long result = dbHelper.updateVisitTable(patientPesel, note);
        if(result!=-1)
            Toast.makeText(AddNotes.this, "Notatka została dodana!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AddNotes.this, "Notatka nie została dodana!", Toast.LENGTH_LONG).show();

        AddNotes.super.onBackPressed();
    }
}
