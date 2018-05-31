package com.example.bodzio.doctorapp;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class AppointmentCreator extends AppCompatActivity {

    private DatabaseManager dbHelper;
    ArrayList peselList = new ArrayList();
    String name;
    String surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcreator);
        final AutoCompleteTextView peselIn = findViewById(R.id.peselText);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        peselList = getPeselArray();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, peselList);
        peselIn.setAdapter(adapter);

        peselIn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new MaterialDialog.Builder(AppointmentCreator.this)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                name = getPatientName(peselIn.getText().toString());
                                surname = getPatientSurname(peselIn.getText().toString());
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                peselIn.setText("");
                            }
                        })
                        .title("Dane pacjenta")
                        .content("Imię: "+getPatientName(peselIn.getText().toString())+"\nNazwisko: "+ getPatientSurname(peselIn.getText().toString()))
                        .positiveText("Potwierdź")
                        .negativeText("Odrzuć")
                        .show();
            }
        });
    }

    public void pickDate(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Date Picker");
    }

    public void pickTime(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "Time Picker");
    }

    public void saveData(View v) {
        TimePickerFragment tpf = new TimePickerFragment();
        int hour = tpf.getHour();
        int minute = tpf.getMinute();

        DatePickerFragment dpf = new DatePickerFragment();
        long date = dpf.getDate();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        Date d = c.getTime();
        //Toast.makeText(this, "" + d, Toast.LENGTH_LONG).show();

        AutoCompleteTextView peselIn = findViewById(R.id.peselText);

        //String name = getPatientName(peselIn.getText().toString());
        //String surname = getPatientSurname(peselIn.getText().toString());
        String pesel = peselIn.getText().toString();
        CheckBox alert = findViewById(R.id.alertCheckbox);
        boolean alertSet = alert.isChecked();
        int a;

        if (alertSet == true) {
            a = 1;
        } else a = 0;
        if(!peselList.contains(peselIn.getText().toString())){
            Toast.makeText(this, "Brak pacjenta o podanym nr PESEL w bazie", Toast.LENGTH_LONG).show();
        } else if (pesel.length() != 11) {
            Toast.makeText(this, getResources().getString(R.string.wrongPesel), Toast.LENGTH_LONG).show();
        } else if (hour == 0 || minute == 0) {
            Toast.makeText(this, getResources().getString(R.string.notimeselected), Toast.LENGTH_LONG).show();
        } else {
            long i = dbHelper.insertAppointmentTab(name, surname, pesel, date, hour, minute, a);
            if (i != -1) {
                Toast.makeText(this, getResources().getString(R.string.addedtodatabase), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
            }
            long j = dbHelper.insertVisitTab(pesel);

            peselIn.setText("");
        }
    }

    public String getPatientSurname(String p) {
        String surname = new String();

        final Cursor cursor = dbHelper.getSurname(p);
        while (cursor.moveToNext()) {
            surname = cursor.getString(0);
        }
        return surname;
    }

    public String getPatientName(String p) {
        String name = new String();

        final Cursor cursor = dbHelper.getName(p);
        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }
        return name;
    }

    public ArrayList getPeselArray() {
        ArrayList surnames = new ArrayList<>();
        final Cursor cursor = dbHelper.getPesel();
        while (cursor.moveToNext()) {
            String surname = cursor.getString(0);
            surnames.add(surname);
        }

        return surnames;
    }
}
