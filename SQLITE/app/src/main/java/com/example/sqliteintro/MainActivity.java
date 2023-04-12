package com.example.sqliteintro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    DatabaseManager dm;
    final String TAG = "demo";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = new DatabaseManager(this);

        dm.getNotesDAO().save(new Note("Subject 1", "Text 1"));
        dm.getNotesDAO().save(new Note("Subject 2", "Text 2"));
        dm.getNotesDAO().save(new Note("Subject 3", "Text 3"));

        Log.d(TAG, "onCreate: " + dm.getNotesDAO().getAll());


    }
}