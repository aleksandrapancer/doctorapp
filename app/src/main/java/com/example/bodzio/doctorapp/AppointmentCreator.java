package com.example.bodzio.doctorapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class AppointmentCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcreator);
    }

    public void pickDate(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }

    public void pickTime(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"Time Picker");
    }

    public void saveData(View v){
        EditText nameIn = findViewById(R.id.nameText);
        EditText surnameIn = findViewById(R.id.surnameText);
        String name = nameIn.getText().toString();
        String surname = surnameIn.getText().toString();
        CheckBox alert =  findViewById(R.id.alertCheckbox);
        boolean alertSetted = alert.isChecked();


        if (name == null || surname == null) {
            Toast.makeText(this, getResources().getString(R.string.emptyfields), Toast.LENGTH_LONG).show();
        }else if (name.equals("") || surname.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.emptyfields), Toast.LENGTH_LONG).show();
        }
    }

}



