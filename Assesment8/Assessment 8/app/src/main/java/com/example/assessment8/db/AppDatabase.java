package com.example.assessment8.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Drink.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DrinkDao drinkDao();
}
