package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class EditPatient extends AppCompatActivity {

    private DatabaseManager dbHelper;
    EditText name, surname, pesel, birthData, address, email, phone;
    Button editButton, deleteButton, showNotesButton;
    Spinner daySpiner, monthSpiner, yearSpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();
        final int id =ShowPatients.idOfPatient;
        ArrayList<Model> patientList = dbHelper.getDataByIdPatient(id);

        //show spinners
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
        for (int i = 1900; i <= thisYear; i++) {
            year.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        yearSpiner.setAdapter(yearAdapter);


        name = findViewById(R.id.nameField);
        surname = findViewById(R.id.surnameField);
        pesel = findViewById(R.id.peselField);
        address = findViewById(R.id.addressField);
        email = findViewById(R.id.emailField);
        phone = findViewById(R.id.phoneField);

        //find position of day, month and year
        int dayPosition = dayAdapter.getPosition(patientList.get(0).getDay());
        int monthPosition = month.getPosition(patientList.get(0).getMonth());
        int yearPosition = month.getPosition(patientList.get(0).getYear());

        //set old values in fields
        name.setText(patientList.get(0).getName());
        surname.setText(patientList.get(0).getSurname());
        pesel.setText(patientList.get(0).getPesel());
        daySpiner.setSelection(dayPosition, true);
        monthSpiner.setSelection(monthPosition, true);
        yearSpiner.setSelection(yearPosition, true);
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

        //update the table
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = dbHelper.updatePatientTable(id, name.getText().toString().toLowerCase(),
                        surname.getText().toString().toLowerCase(),
                        pesel.getText().toString().toLowerCase(),
                        daySpiner.getSelectedItem().toString(),
                        monthSpiner.getSelectedItem().toString(),
                        yearSpiner.getSelectedItem().toString(),
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

        //show patient notes
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

    //chreate message whit notes
    public void showMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Notatki");
        builder.setMessage(message);
        builder.show();
    }
}
