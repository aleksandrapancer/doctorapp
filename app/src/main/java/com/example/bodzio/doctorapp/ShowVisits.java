package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.*;
import java.util.Calendar;

public class ShowVisits  extends AppCompatActivity {

    private DatabaseManager dbHelper;
    static int idOfVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_visits);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        ListView listView = findViewById(R.id.listView);

        java.util.Calendar cc = Calendar.getInstance();
        long d = cc.getTimeInMillis();

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
                idOfVisit = idFromList;
                Intent intent = new Intent(ShowVisits.this, AddNotes.class);
                startActivity(intent);
            }
        });
    }
}
