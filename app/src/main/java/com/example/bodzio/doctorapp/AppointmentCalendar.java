package com.example.bodzio.doctorapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.example.bodzio.doctorapp.R.id.calendarView;
import static com.prolificinteractive.materialcalendarview.CalendarDay.from;

public class AppointmentCalendar extends AppCompatActivity implements OnDateSelectedListener {

    MaterialCalendarView widget;
    private DatabaseManager dbHelper;
    private ArrayList<CalendarDay> dates = new ArrayList<>();
    // CalendarDay day = new CalendarDay(2018,3,20);

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private static final int color = Color.parseColor("#a50029");

    ListView listView = findViewById(R.id.listView);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        widget = findViewById(calendarView);
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        widget.setSelectedDate(instance.getTime());

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        dates = getAppointmentsDates();
        widget.addDecorators(
                new HighlightWeekendsDecorator(),
                oneDayDecorator,
                new EventDecorator(color,dates)
        );
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();

        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();

        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        long pickedDate = c.getTimeInMillis();

        //show list of patients who have appointment
        final ArrayList<AppModel> appList = dbHelper.getDataByPickedDate(pickedDate);
        AppointmentsAdapter adapter = new AppointmentsAdapter(this, appList);
        listView.setAdapter(adapter);
    }


    public ArrayList<CalendarDay> getAppointmentsDates(){
        Calendar c = Calendar.getInstance();
        ArrayList<CalendarDay> appointmentCollection =  new ArrayList<>();

        final Cursor cursor = dbHelper.getDates();
        while (cursor.moveToNext()){
            long dateInMilis = cursor.getLong(0);
            c.setTimeInMillis(dateInMilis);
            CalendarDay date = CalendarDay.from(c);
            appointmentCollection.add(date);
        }

        return appointmentCollection;
    }

}
