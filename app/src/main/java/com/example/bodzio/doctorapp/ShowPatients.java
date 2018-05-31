package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ShowPatients extends AppCompatActivity {

    private DatabaseManager dbHelper;
    EditText surname, pesel;
    static int idOfPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_show_patients);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        showList();
    }

    public void showList(){
        ListView listView = findViewById(R.id.listView);
        final ArrayList<Model> customerList = dbHelper.getAllDataPatient();

        CustomUserAdapter adapter = new CustomUserAdapter(this, customerList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int idFromList = customerList.get(position).getId();
               idOfPatient = idFromList;
                Intent intent = new Intent(ShowPatients.this, PatientData.class);
                startActivity(intent);
            }
        });
    }

    public void showFilteredList(View view){
        final ArrayList<Model> customerListFilter;
        ListView listView = findViewById(R.id.listView);

        surname = findViewById(R.id.surnameFiltrField);
        pesel = findViewById(R.id.peselFiltrField);
        String surnameText = surname.getText().toString().trim();
        String peselText = pesel.getText().toString().trim();

        if(!surnameText.equals("") && peselText.equals(""))
            customerListFilter = dbHelper.getDataBySurnamePatient(surnameText);
        else if(surnameText.equals("") && !peselText.equals(""))
            customerListFilter = dbHelper.getDataByPeselPatient(peselText);
        else
            customerListFilter = dbHelper.getDataPatient(surnameText, peselText);


        CustomUserAdapter adapter = new CustomUserAdapter(this, customerListFilter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idFromList = customerListFilter.get(position).getId();
                idOfPatient = idFromList;
                Intent intent = new Intent(ShowPatients.this, PatientData.class);
                startActivity(intent);
            }
        });
    }

    public void addPatient(View view) {
        Intent intent = new Intent(ShowPatients.this,  AddPatient.class);
        startActivity(intent);
    }
}