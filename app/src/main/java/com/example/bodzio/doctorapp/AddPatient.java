package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddPatient extends AppCompatActivity {

    private DatabaseManager dbHelper;

    EditText name, surname, pesel, address, email, phone;
    Spinner daySpiner, monthSpiner, yearSpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        //show dropdown (day, month, year)
        daySpiner = findViewById(R.id.spinerDay);
        monthSpiner = findViewById(R.id.spinerMonth);
        yearSpiner = findViewById(R.id.spinerYear);

        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        daySpiner.setAdapter(dayAdapter);
        ArrayAdapter<String> month = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.monthArray));
        month.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpiner.setAdapter(month);

        ArrayList<String> year = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1900; i--) {
            year.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        yearSpiner.setAdapter(yearAdapter);
    }

    public void add(View v) {
                name = findViewById(R.id.nameField);
                surname = findViewById(R.id.surnameField);
                pesel = findViewById(R.id.peselField);
                address = findViewById(R.id.addressField);
                email = findViewById(R.id.emailField);
                phone = findViewById(R.id.phoneField);

                //check if all the fields are fill correct
        if(isNotEmpty(name.getText().toString(),
                surname.getText().toString(),
                pesel.getText().toString(),
                address.getText().toString(),
                email.getText().toString(),
                phone.getText().toString())) {
            if(dbHelper.ifPatientNotExist(pesel.getText().toString())) {

                //insert values to database
                long i = dbHelper.insertPatientTab(name.getText().toString(),
                        surname.getText().toString(),
                        pesel.getText().toString(),
                        daySpiner.getSelectedItem().toString(),
                        monthSpiner.getSelectedItem().toString(),
                        yearSpiner.getSelectedItem().toString(),
                        address.getText().toString().toLowerCase(),
                        email.getText().toString().toLowerCase(),
                        phone.getText().toString().toLowerCase());
                Log.d("Logcat", "insert patient table - success");

                if (i != -1) {
                    Toast.makeText(this, "Zapisano pacjenta", Toast.LENGTH_LONG).show();
                    final ArrayList<Model> userList = dbHelper.getAllDataPatient();
                    CustomUserAdapter adapter = new CustomUserAdapter(this, userList);
                    adapter.refresh(userList);

                    Intent intent = new Intent(AddPatient.this,  ShowPatients.class);
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

    //check if fields are fill correct
    public boolean isNotEmpty(String name, String surname, String pesel, String address, String email, String phone){

        if(name.equals("") || name == null){
            Toast.makeText(this, "Podaj imię", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(surname.equals("") || surname == null){
            Toast.makeText(this, "Podaj nazwisko", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(pesel.equals("") || pesel == null || pesel.length()<11){
            Toast.makeText(this, "Podaj pesel", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(address.equals("") || address == null){
            Toast.makeText(this, "Podaj adress", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(email.equals("") || email == null || !email.contains("@")){
            Toast.makeText(this, "Podaj email", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(phone.equals("") || phone == null){
            Toast.makeText(this, "Podaj numer telefonu", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

 }

