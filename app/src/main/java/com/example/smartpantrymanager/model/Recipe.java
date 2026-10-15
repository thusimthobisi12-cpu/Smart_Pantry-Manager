package com.example.smartpantrymanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This class becomes the "recipes" table.
@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;   // example: "Tomato Omelette"
    public String steps;  // example: "1. Beat the eggs. 2. Chop the tomato..."

    public Recipe(String name, String steps) {
        this.name = name;
        this.steps = steps;
    }
}
