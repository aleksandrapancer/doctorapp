package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class EditPatient extends AppCompatActivity {

    private DatabaseManager dbHelper;
    EditText name, surname, pesel, birthData, address, email, phone;
    Button editButton, deleteButton, showNotesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();
        final int id =ShowPatients.idOfPatient;
        ArrayList<Model> patientList = dbHelper.getDataByIdPatient(id);

        name = findViewById(R.id.nameField);
        surname = findViewById(R.id.surnameField);
        pesel = findViewById(R.id.peselField);
        birthData = findViewById(R.id.birthDataField);
        address = findViewById(R.id.addressField);
        email = findViewById(R.id.emailField);
        phone = findViewById(R.id.phoneField);


        //set old values in fields
        name.setText(patientList.get(0).getName());
        surname.setText(patientList.get(0).getSurname());
        pesel.setText(patientList.get(0).getPesel());
        birthData.setText(patientList.get(0).getBirthData());
        address.setText(patientList.get(0).getAddress());
        email.setText(patientList.get(0).getEmail());
        phone.setText(String.valueOf(patientList.get(0).getPhone()));

        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        showNotesButton = findViewById(R.id.showNotesButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteData(id);
                Toast.makeText(EditPatient.this, "Dane zostały usunięte!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditPatient.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = dbHelper.updatePatientTable(id, name.getText().toString().toLowerCase(),
                        surname.getText().toString().toLowerCase(),
                        pesel.getText().toString().toLowerCase(),
                        birthData.getText().toString().toLowerCase(),
                        address.getText().toString().toLowerCase(),
                        email.getText().toString().toLowerCase(),
                        phone.getText().toString().toLowerCase());
                if(result!=-1)
                    Toast.makeText(EditPatient.this, "Dane zostały zmienione!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EditPatient.this, "Dane nie zostały zmienione!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditPatient.this, MainActivity.class);
                startActivity(intent);
            }
        });

        showNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = dbHelper.getNotesByPeselVisit(pesel.getText().toString());
                if(res.getCount()==0){
                    showMessage("Nie ma w bazie notatek");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append(res.getString(2)+"\n\n");
                }
                showMessage(buffer.toString());

            }
        });

    }

    public void showMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Notatki");
        builder.setMessage(message);
        builder.show();
    }
}
