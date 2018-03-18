package com.example.bodzio.doctorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPatient extends AppCompatActivity {

    EditText name, surname, pesel, birthData, address, email, phone;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        name = findViewById(R.id.nameField);
        surname = findViewById(R.id.surnameField);
        pesel = findViewById(R.id.peselField);
        birthData = findViewById(R.id.birthDataField);
        address = findViewById(R.id.addressField);
        email = findViewById(R.id.emailField);
        phone = findViewById(R.id.phoneField);
        addButton = findViewById(R.id.addButton);

        final DatabaseHelper db = new DatabaseHelper(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertPatientTab(name.getText().toString(),
                        surname.getText().toString(),
                        pesel.getText().toString(),
                        birthData.getText().toString(),
                        address.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString());
            }
        });
    }
}
