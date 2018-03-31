package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                phone.getText().toString())) {
            if(dbHelper.ifPatientNotExist(pesel.getText().toString())) {

                long i = dbHelper.insertPatientTab(name.getText().toString().toLowerCase(),
                        surname.getText().toString().toLowerCase(),
                        pesel.getText().toString().toLowerCase(),
                        birthData.getText().toString().toLowerCase(),
                        address.getText().toString().toLowerCase(),
                        email.getText().toString().toLowerCase(),
                        phone.getText().toString().toLowerCase());
                Log.d("Logcat", "insert patient table - success");

                if (i != -1) {
                    Toast.makeText(this, "Zapisano pacjeta", Toast.LENGTH_LONG).show();
                    cleanField();
                    Intent intent = new Intent(AddPatient.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Błąd zapisu", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, "Istnieje już osoba o peselu: "+pesel.getText().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean isNotEmpty(String name, String surname, String pesel, String birthData, String address, String email, String phone){

        if(name.equals("") || name == null){
            Toast.makeText(this, "Wpisz imie.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(surname.equals("") || surname == null){
            Toast.makeText(this, "Wpisz nazwisko.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(pesel.equals("") || pesel == null || pesel.length()<11){
            Toast.makeText(this, "Wpisz pesel.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(birthData.equals("") || birthData == null){
            Toast.makeText(this, "Wpisz date urodzenia.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(address.equals("") || address == null){
            Toast.makeText(this, "Wpisz adress.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(email.equals("") || email == null || !email.contains("@")){
            Toast.makeText(this, "Wpisz email.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(phone.equals("") || phone == null){
            Toast.makeText(this, "Wpisz numer telefonu.", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
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

