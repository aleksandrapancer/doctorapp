package com.example.bodzio.doctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNotes extends AppCompatActivity {

    private DatabaseManager dbHelper;
    Button addNoteButton;
    EditText noteText;
    String patientPesel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        dbHelper = new DatabaseManager(this);
        dbHelper.open();

        noteText = findViewById(R.id.notesText);

        //add note to visit table after press the button
        addNoteButton = findViewById(R.id.addNoteBuuton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = noteText.getText().toString();
                patientPesel = ShowVisits.patientPesel;

                long result = dbHelper.updateVisitTable(patientPesel, note);
                if(result!=-1)
                    Toast.makeText(AddNotes.this, "Notatka została dodana!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddNotes.this, "Notatka nie została dodana!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddNotes.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
