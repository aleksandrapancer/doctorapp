package com.example.bodzio.doctorapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

public class AppointmentAdapter  extends ArrayAdapter<Model> {

    public AppointmentAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
