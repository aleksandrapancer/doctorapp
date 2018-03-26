package com.example.bodzio.doctorapp;

import android.content.Intent;
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


        if(isNotEmpty(name.getText().toString(),
                surname.getText().toString(),
                pesel.getText().toString(),
                birthData.getText().toString(),
                address.getText().toString(),
                email.getText().toString(),
                phone.getText().toString())){

             long i = dbHelper.insertPatientTab(name.getText().toString().toLowerCase(),
                        surname.getText().toString().toLowerCase(),
                        pesel.getText().toString().toLowerCase(),
                        birthData.getText().toString().toLowerCase(),
                        address.getText().toString().toLowerCase(),
                        email.getText().toString().toLowerCase(),
                        phone.getText().toString().toLowerCase());
               if (i!=-1){
                Toast.makeText(this, getResources().getString(R.string.addedtodatabase), Toast.LENGTH_LONG).show();
                cleanField();
                   Intent intent = new Intent(AddPatient.this, MainActivity.class);
                   startActivity(intent);
             }else {
                 Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
             }
        }
    }

    public boolean isNotEmpty(String name, String surname, String pesel, String birthData, String address, String email, String phone){

        if(name.equals("")){
            Toast.makeText(this, "Wpisz imie.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(surname.equals("")){
            Toast.makeText(this, "Wpisz nazwisko.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(pesel.equals("") && pesel.length()!=11){
            Toast.makeText(this, "Wpisz pesel.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(birthData.equals("")){
            Toast.makeText(this, "Wpisz date urodzenia.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(address.equals("")){
            Toast.makeText(this, "Wpisz adress.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(email.equals("") && !email.contains("@")){
            Toast.makeText(this, "Wpisz email.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(phone.equals("")){
            Toast.makeText(this, "Wpisz numer telefonu.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void cleanField (){
        name.setText("");
        surname.setText("");
        pesel.setText("");
        birthData.setText("");
        address.setText("");
        email.setText("");
        phone.setText("");
    }
 }

