package com.example.smartpantrymanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smartpantrymanager.model.PantryItem;

import java.util.List;


@Dao
public interface PantryItemDAO {


    @Insert
    long insert(PantryItem item);


    @Query("SELECT * FROM pantry_items ORDER BY name ASC")
    List<PantryItem> getAllItems();


    @Query("SELECT * FROM pantry_items WHERE id = :id")
    PantryItem getItemById(int id);


    @Update
    void update(PantryItem item);


    @Delete
    void delete(PantryItem item);
}