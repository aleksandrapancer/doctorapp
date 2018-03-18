package com.example.bodzio.doctorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPatient extends AppCompatActivity {

    private DatabaseManager dbHelper;

    EditText name, surname, pesel, birthData, address, email, phone;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();
    }

    public void add(View v) {
                name = findViewById(R.id.nameField);
                surname = findViewById(R.id.surnameField);
                pesel = findViewById(R.id.peselField);
                birthData = findViewById(R.id.birthDataField);
                address = findViewById(R.id.addressField);
                email = findViewById(R.id.emailField);
                phone = findViewById(R.id.phoneField);
                addButton = findViewById(R.id.addButton);

        long i = dbHelper.insertPatientTab(name.getText().toString(),
                        surname.getText().toString(),
                        pesel.getText().toString(),
                        birthData.getText().toString(),
                        address.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString());
        if (i!=-1){
            Toast.makeText(this, getResources().getString(R.string.addedtodatabase), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
        }
    }
 }

