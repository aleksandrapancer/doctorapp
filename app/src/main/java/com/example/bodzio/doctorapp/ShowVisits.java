package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import static android.text.TextUtils.substring;

public class ShowVisits  extends AppCompatActivity {

    private DatabaseManager dbHelper;
    static String patientPesel, visitHour, visitMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_visits);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        ListView listView = findViewById(R.id.listView);

        //get today data
        Calendar calendar = Calendar.getInstance();
        long date = calendar.getTimeInMillis();

        //show list of patients who have appointment today
        final ArrayList<AppModel> visitList = dbHelper.getDataByDateApp(date);
        CustomVisitAdapter adapter = new CustomVisitAdapter(this, visitList);
        listView.setAdapter(adapter);

        //action after press on the patient
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String peselFromList = visitList.get(position).getPesel();
                patientPesel = peselFromList;
                visitHour = visitList.get(position).getHour();
                visitMinute = visitList.get(position).getMinute();
                Intent intent = new Intent(ShowVisits.this, AddNotes.class);
                startActivity(intent);
            }
        });
    }
}
