package com.example.sqliteintro;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotesDAO {
    private SQLiteDatabase db;

    public NotesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    //CRUD create read update delete\\\


    public long save(Note note) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());
        return db.insert(NotesTable.TABLE_NAME, null, values);

    }


    public boolean update(Note note) {
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());
        return db.update(NotesTable.TABLE_NAME,values,NotesTable.COLUMN_ID + " = ?",new String[]{String.valueOf(note.get_id())}) >0;
    }

    public boolean delete(Note note) {
        return db.delete(NotesTable.TABLE_NAME, NotesTable.COLUMN_ID + " = ?",new String[]{String.valueOf(note.get_id())}) >0;
    }

    public Note get(long id) {
        Note note = null;
        Cursor cursor = db.query(NotesTable.TABLE_NAME, new String[]{NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT, NotesTable.COLUMN_TEXT}, NotesTable.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            note = buildFromCursor(cursor);

        }
        return note;
    }

    public ArrayList<Note> getAll() {
        ArrayList<Note> notes = new ArrayList<>();

        Cursor cursor = db.query(NotesTable.TABLE_NAME,
                new String[]{NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT, NotesTable.COLUMN_TEXT},
                null, null, null, null, null);


        while (cursor.moveToNext()) {
            Note note = buildFromCursor(cursor);
            notes.add(note);
        }


        return notes;
    }

    private Note buildFromCursor(Cursor cursor) {
        Note note = new Note();
        note.set_id(cursor.getLong(0));
        note.setSubject(cursor.getString(1));
        note.setText(cursor.getString(2));
        return note;
    }
}
