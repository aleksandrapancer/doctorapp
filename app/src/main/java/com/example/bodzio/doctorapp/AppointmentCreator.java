package com.example.bodzio.doctorapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;


public class AppointmentCreator extends AppCompatActivity {

    private DatabaseManager dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcreator);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();
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
        TimePickerFragment tpf = new TimePickerFragment();
        int hour = tpf.getHour();
        int minute =  tpf.getMinute();

        DatePickerFragment dpf = new DatePickerFragment();
        long date = dpf.getDate();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        Date d = c.getTime();
        //Toast.makeText(this, "" + d, Toast.LENGTH_LONG).show();

        EditText nameIn = findViewById(R.id.nameText);
        EditText surnameIn = findViewById(R.id.surnameText);
        EditText peselIn = findViewById(R.id.peselText);
        String name = nameIn.getText().toString();
        String surname = surnameIn.getText().toString();
        String pesel = peselIn.getText().toString();
        CheckBox alert =  findViewById(R.id.alertCheckbox);
        boolean alertSet = alert.isChecked();
        int a;

        if(alertSet==true){
             a = 1;
        }else a = 0;

        if (name == null || surname == null || pesel==null) {
            Toast.makeText(this, getResources().getString(R.string.emptyfields), Toast.LENGTH_LONG).show();
        }else if (name.equals("") || surname.equals("") || pesel.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.emptyfields), Toast.LENGTH_LONG).show();
        }else if(pesel.length()<11){
            Toast.makeText(this, getResources().getString(R.string.wrongPesel), Toast.LENGTH_LONG).show();
        }else if(hour == 0 || minute == 0){
            Toast.makeText(this, getResources().getString(R.string.notimeselected), Toast.LENGTH_LONG).show();
        }else{
            long i = dbHelper.insertAppointmentTab(name,surname,pesel,date,hour,minute,a);
            if (i!=-1){
                Toast.makeText(this, getResources().getString(R.string.addedtodatabase), Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
            }
            long j = dbHelper.insertVisitTab(pesel);

            nameIn.setText("");
            surnameIn.setText("");
            peselIn.setText("");
        }
    }
}



