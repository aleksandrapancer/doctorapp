package com.example.bodzio.doctorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomVisitAdapter extends ArrayAdapter<AppModel>{

    public CustomVisitAdapter(Context context, ArrayList<AppModel> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_visit, parent, false);
        }

        AppModel user = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.textName);
        TextView time = (TextView) convertView.findViewById(R.id.textTime);

        name.setText(user.getName() + " " + user.getSurname());

        if(user.getHour().length()==1 && user.getMinute().length()==1){
            time.setText("0"+user.getHour() + " : 0" + user.getMinute());
       }
       else if(user.getHour().length()==1){
           time.setText("0"+user.getHour() + " : " + user.getMinute());
       }
       else if(user.getMinute().length()==1){
            time.setText(user.getHour() + " : 0" + user.getMinute());
       }
       else
           time.setText(user.getHour() + " : " + user.getMinute());

        return convertView;
    }
}
