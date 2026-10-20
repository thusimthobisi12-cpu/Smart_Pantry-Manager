package com.example.smartpantrymanager.database;


public class UnitConvertor {


    public static String baseUnit(String unit) {
        switch (unit) {
            case "kg":
            case "g":
                return "g";
            case "l":
            case "ml":
                return "ml";
            default:
                return "pcs";
        }
    }


    public static double toBase(double quantity, String unit) {
        switch (unit) {
            case "kg":
            case "l":
                return quantity * 1000;
            default:
                return quantity;
        }
    }
}