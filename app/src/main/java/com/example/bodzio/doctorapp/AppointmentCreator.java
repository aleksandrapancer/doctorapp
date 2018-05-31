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
    String nm;
    String snm;
    AutoCompleteTextView peselIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcreator);
        peselIn = findViewById(R.id.peselText);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        peselList = getPeselArray();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, peselList);
        peselIn.setAdapter(adapter);


        peselIn.setOnDismissListener(new AutoCompleteTextView.OnDismissListener(){
            @Override
            public void onDismiss() {
                if(peselIn.getText().toString().length() == 11){
                    if(!peselList.contains(peselIn.getText().toString())){
                        Toast.makeText(AppointmentCreator.this, "Brak pacjenta o podanym nr PESEL w bazie", Toast.LENGTH_LONG).show();
                    }else{
                        Cursor res = dbHelper.getName(peselIn.getText().toString());
                        if (res.moveToFirst()) {
                            nm = res.getString(1);
                            snm = res.getString(2);
                        }

                        new MaterialDialog.Builder(AppointmentCreator.this)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        name = nm;
                                        surname = snm;
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        peselIn.setText("");
                                    }
                                })
                                .title("Dane pacjenta")
                                .content("Imię: " + nm+ "\nNazwisko: " + snm)
                                .positiveText("Potwierdź")
                                .negativeText("Odrzuć")
                                .show();

                    }
                }
            }
        });

        peselIn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedPesel = (String)adapterView.getItemAtPosition(i);
                Cursor res = dbHelper.getName(clickedPesel);
                if (res.moveToFirst()) {
                    nm = res.getString(1);
                    snm = res.getString(2);
                }

                new MaterialDialog.Builder(AppointmentCreator.this)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                name = nm;
                                surname = snm;
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                peselIn.setText("");
                            }
                        })
                        .title("Dane pacjenta")
                        .content("Imię: " + nm+ "\nNazwisko: " + snm)
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

        String pesel = peselIn.getText().toString();
        CheckBox alert = findViewById(R.id.alertCheckbox);
        boolean alertSet = alert.isChecked();
        int a;

        if (alertSet == true) {
            a = 1;
        } else a = 0;

        if (pesel.length() != 11) {
            Toast.makeText(this, getResources().getString(R.string.wrongPesel), Toast.LENGTH_LONG).show();
        } else if(!peselList.contains(peselIn.getText().toString())){
            Toast.makeText(this, "Brak pacjenta o podanym nr PESEL w bazie", Toast.LENGTH_LONG).show();
        }
         else if (hour == 0 || minute == 0) {
            Toast.makeText(this, getResources().getString(R.string.notimeselected), Toast.LENGTH_LONG).show();
        } else {
            long i = dbHelper.insertAppointmentTab(name, surname, pesel, date, hour, minute, a);
            if (i != -1) {
                Toast.makeText(this, getResources().getString(R.string.addedtodatabase), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.failed), Toast.LENGTH_LONG).show();
            }
            long j = dbHelper.insertVisitTab(pesel, date, hour, minute);
            if (j!=-1){
                //Toast.makeText(this, "dodano tablice wizyt", Toast.LENGTH_LONG).show();
            }else {
                //Toast.makeText(this, "nie dodano tablicy wizyt", Toast.LENGTH_LONG).show();
            }

            peselIn.setText("");
        }
    }


    public ArrayList getPeselArray() {
        ArrayList pesel = new ArrayList<>();
        final Cursor cursor = dbHelper.getPesel();
        while (cursor.moveToNext()) {
            String p = cursor.getString(0);
            pesel.add(p);
        }
        return pesel;
    }
}
