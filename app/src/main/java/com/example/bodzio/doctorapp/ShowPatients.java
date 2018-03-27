package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ShowPatients extends AppCompatActivity {

    Button showButton;
    private DatabaseManager dbHelper;
    EditText surname, pesel;
    static int idOfPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patients);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        showList();

        showButton = findViewById(R.id.filtrButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilteredList();
            }
        });

    }

    public void showList(){

        ListView listView = findViewById(R.id.listView);

        final Cursor cursor = dbHelper.getData();
        final ArrayList<Model> customerList = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String surname = cursor.getString(2);
            int pesel = cursor.getInt(3);

            customerList.add(new Model(id, name, surname, pesel));
        }

        CustomUserAdapter adapter = new CustomUserAdapter(this, customerList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int idFromList = customerList.get(position).getId();
               idOfPatient = idFromList;
                Intent intent = new Intent(ShowPatients.this, EditPatient.class);
                startActivity(intent);
            }
        });
    }

    public void showFilteredList(){

        ListView listView = findViewById(R.id.listView);

        final Cursor cursorFilter;
        surname = findViewById(R.id.surnameFiltrField);
        pesel = findViewById(R.id.peselFiltrField);
        String surnameText = surname.getText().toString().toLowerCase();
        String peselText = pesel.getText().toString().toLowerCase();

        if(!surnameText.equals("") && peselText.equals(""))
            cursorFilter = dbHelper.getData(surnameText);
        else if(surnameText.equals("") && !peselText.equals(""))
            cursorFilter = dbHelper.getData(Integer.parseInt(peselText));
        else
            cursorFilter = dbHelper.getData(surnameText, Integer.parseInt(peselText));
        
        final ArrayList<Model> customerList = new ArrayList<>();
        while (cursorFilter.moveToNext()){
            int id = cursorFilter.getInt(0);
            String name = cursorFilter.getString(1);
            String surname = cursorFilter.getString(2);
            int pesel = cursorFilter.getInt(3);

            customerList.add(new Model(id, name, surname, pesel));
        }

        CustomUserAdapter adapter = new CustomUserAdapter(this, customerList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idFromList = customerList.get(position).getId();
                idOfPatient = idFromList;
                Intent intent = new Intent(ShowPatients.this, EditPatient.class);
                startActivity(intent);
            }
        });
    }
    public void addPatient(View view) {
        Intent intent = new Intent(ShowPatients.this,  AddPatient.class);
        startActivity(intent);
    }
}