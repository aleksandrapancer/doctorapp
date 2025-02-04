package com.example.bodzio.doctorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomUserAdapter extends ArrayAdapter<Model>{

    public CustomUserAdapter(Context context, ArrayList<Model> users) {
        super(context, 0, users);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_user, parent, false);
        }

        Model user = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.textName);
        name.setText(user.getName() + " " + user.getSurname());
        return convertView;
    }

    public void refresh(ArrayList<Model> arr) {
        arr.clear();
        arr.addAll(arr);
        notifyDataSetChanged();
    }
}
