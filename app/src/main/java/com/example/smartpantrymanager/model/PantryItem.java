package com.example.smartpantrymanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This class becomes the "pantry_items" table in the database.
@Entity(tableName = "pantry_items")
public class PantryItem {

    // Room gives each item its own number automatically.
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;        // example: "tomato"
    public double quantity;    // example: 500
    public String unit;        // example: "g"
    public String expiryDate;  // optional, example: "2026-11-01"

    public PantryItem(String name, double quantity, String unit, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }
}