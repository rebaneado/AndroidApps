package com.example.sqliteintro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    Context mContext;
    SQLiteDatabase db;
    DatabaseHelper dbOpenHelper;
    NotesDAO notesDAO;


    public DatabaseManager(Context context) {
        this.mContext = context;
        dbOpenHelper = new DatabaseHelper(mContext);
        db = dbOpenHelper.getWritableDatabase();
        notesDAO = new NotesDAO(db);
    }

    public NotesDAO getNotesDAO() {
        return notesDAO;
    }
}
