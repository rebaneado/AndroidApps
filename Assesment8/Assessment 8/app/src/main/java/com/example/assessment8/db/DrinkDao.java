package com.example.assessment8.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DrinkDao {
    @Query("SELECT * FROM drink")
    List<Drink> getAll();


    @Insert
    void insertAll(Drink... drinks);

    @Delete
    void delete(Drink drink);

    @Update
    public void update(Drink... drinks);

    @Query("DELETE FROM drink")
    public void nukeTable();

    //Void deleteAll(Drink... drinks);

}
