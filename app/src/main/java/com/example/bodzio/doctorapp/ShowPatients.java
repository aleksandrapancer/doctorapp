package com.example.bodzio.doctorapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ShowPatients extends AppCompatActivity {

    Button showButton;
    private DatabaseManager dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patients);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        showButton = findViewById(R.id.sortButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = dbHelper.getData();
                if(res.getCount()==0){
                    showMessage("Nic nie ma w bazie");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id: "+res.getString(0)+"\n");
                    buffer.append("Name: "+res.getString(1)+"\n");
                    buffer.append("Surname: "+res.getString(2)+"\n");
                    buffer.append("Pesel: "+res.getString(3)+"\n");
                    buffer.append("Birth data: "+res.getString(4)+"\n");
                    buffer.append("Address: "+res.getString(5)+"\n");
                    buffer.append("Email: "+res.getString(6)+"\n");
                    buffer.append("Phone: "+res.getString(7)+"\n");

                }
                showMessage(buffer.toString());
            }
        });
    }

    public void showMessage(String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("ha");
        builder.setMessage(mess);
        builder.show();
    }
}
