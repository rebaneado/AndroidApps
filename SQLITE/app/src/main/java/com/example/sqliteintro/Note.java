package com.example.sqliteintro;

public class Note {
    long _id;
    String subject;
    String text;


    public Note(long _id, String subject, String text) {
        this._id = _id;
        this.subject = subject;
        this.text = text;
    }

    public Note(String subject, String text) {
        this.subject = subject;
        this.text = text;
   }

    public Note() {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
