package com.example.bodzio.doctorapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AppointmentsAdapter extends ArrayAdapter<AppModel>{

    public AppointmentsAdapter(Context context, ArrayList<AppModel> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom, parent, false);
        }

        AppModel user = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.textName);
        TextView surname = (TextView) convertView.findViewById(R.id.textSurname);
        name.setText(user.getName());
        surname.setText(user.getSurname());
        return convertView;
    }
}
