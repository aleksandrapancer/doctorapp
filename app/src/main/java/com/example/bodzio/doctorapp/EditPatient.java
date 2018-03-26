package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class EditPatient extends AppCompatActivity {

    private DatabaseManager dbHelper;
    EditText name, surname, pesel, birthData, address, email, phone;
    Button editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();
        final int id =ShowPatients.idOfPatient;
        ArrayList<Model> patientList = createList(id);

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
        pesel.setText(String.valueOf(patientList.get(0).getPesel()));
        birthData.setText(patientList.get(0).getBirthData());
        address.setText(patientList.get(0).getAddress());
        email.setText(patientList.get(0).getEmail());
        phone.setText(String.valueOf(patientList.get(0).getPhone()));

        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

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
                dbHelper.updatePatientTable(id, name.getText().toString().toLowerCase(),
                        surname.getText().toString().toLowerCase(),
                        pesel.getText().toString().toLowerCase(),
                        birthData.getText().toString().toLowerCase(),
                        address.getText().toString().toLowerCase(),
                        email.getText().toString().toLowerCase(),
                        phone.getText().toString().toLowerCase());
                Toast.makeText(EditPatient.this, "Dane zostały zmienione!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditPatient.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public ArrayList<Model> createList(int id){
        final Cursor cursor = dbHelper.getDataById(id);
        final ArrayList<Model> customerList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String nameText = cursor.getString(1);
            String surnameText = cursor.getString(2);
            int peselText = cursor.getInt(3);
            String birthDataText = cursor.getString(4);
            String addressText = cursor.getString(5);
            String emailText = cursor.getString(6);
            int phoneText = cursor.getInt(7);

            customerList.add(new Model(nameText, surnameText, peselText, birthDataText, addressText, emailText, phoneText));
        }
            return customerList;
    }
}
