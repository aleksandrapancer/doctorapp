package com.example.bodzio.doctorapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import static com.example.bodzio.doctorapp.R.id.calendarView;

public class AppointmentCalendar extends AppCompatActivity implements OnDateSelectedListener {

    MaterialCalendarView widget;
    private DatabaseManager dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        ArrayList<CalendarDay> cal = getAppointmentsDates();
        widget = findViewById(calendarView);
        widget.setOnDateChangedListener(this);
        cal = getAppointmentsDates();
        decorateAppointments(cal);

        //change view from month to week
        /*widget.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        widget.state().edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        */
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
    }

    //method for displaying all appointments on calendar
    public void decorateAppointments(Collection<CalendarDay> dates){
        EventDecorator decor = new EventDecorator(R.color.blue,dates);
        widget.addDecorator(decor);


        Iterator<CalendarDay> iterator = dates.iterator();
        while (iterator.hasNext()) {
             decor.shouldDecorate(iterator.next());
        }
    }


    public ArrayList<CalendarDay> getAppointmentsDates(){
        Calendar c = Calendar.getInstance();
        ArrayList<CalendarDay> appointmentCollection =  new ArrayList<>();

        final Cursor cursor = dbHelper.getDates();
        while (cursor.moveToNext()){
            long dateInMilis = cursor.getLong(0);
            c.setTimeInMillis(dateInMilis);
            //Date time = c.getTime();
            int year = c.YEAR;
            int month = c.MONTH;
            int day = c.DAY_OF_MONTH;

            CalendarDay date = new CalendarDay(year,month,day);
            appointmentCollection.add(date);
        }

        return appointmentCollection;
    }

}
