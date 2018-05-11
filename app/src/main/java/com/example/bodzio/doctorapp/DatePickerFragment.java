package com.example.bodzio.doctorapp;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static long pickedDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //format for saving in database
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        pickedDate = calendar.getTimeInMillis();

    }

    public long getDate(){
        return pickedDate; }
}