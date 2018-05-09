package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class PatientData extends AppCompatActivity{

    private DatabaseManager dbHelper;
    Button editButton, deleteButton, showNotesButton, showVisitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();
        final int id =ShowPatients.idOfPatient;
        final ArrayList<Model> patientList = dbHelper.getDataByIdPatient(id);

        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        showNotesButton = findViewById(R.id.showNotesButton);
        showVisitButton = findViewById(R.id.showVisitButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteData(id);
                Toast.makeText(PatientData.this, "Dane zostały usunięte!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PatientData.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //show patient notes
        showNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = dbHelper.getNotesByPeselVisit(patientList.get(0).getPesel());
                if(res.getCount()==0){
                    showMessage("Nie ma w bazie notatek", "Notatki");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append(res.getString(2)+"\n\n");
                }
                showMessage(buffer.toString(), "Notatki");

            }
        });

        //show patient visits
        showVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                Cursor res = dbHelper.getAllVisitForPatient(patientList.get(0).getPesel());
                if(res.getCount()==0){
                    showMessage("Brak wizyt w bazie", "Wizyty");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){

                    calendar.setTimeInMillis(Long.valueOf(res.getString(4)));
                    int d = calendar.get(Calendar.DAY_OF_MONTH);
                    int m = calendar.get(Calendar.MONTH);
                    int y = calendar.get(Calendar.YEAR);

                    if(d<10 && m<10){
                        buffer.append("0"+d+".0"+m+"."+y+"  ");
                    }else if(m<10){
                        buffer.append(d+".0"+m+"."+y+"  ");
                    }else if(d<10){
                        buffer.append("0"+d+"."+m+"."+y+"  ");
                    }else
                        buffer.append(d+"."+m+"."+y+"  ");

                    buffer.append(res.getString(5)+":");
                    if(res.getString(6).length()!=1)
                        buffer.append(res.getString(6)+"\n\n");
                    else
                        buffer.append("0"+res.getString(6)+"\n\n");
                }
                showMessage(buffer.toString(), "Wizyty");
            }
        });

    }

    //create message whit notes
    public void showMessage(String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void editPatient(View view) {
        Intent intent = new Intent(PatientData.this,  EditPatient.class);
        startActivity(intent);
    }
}
