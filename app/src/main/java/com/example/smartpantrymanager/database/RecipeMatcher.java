package com.example.smartpantrymanager.database;

import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RecipeMatcher {


    public static List<Recipe> getSuggestedRecipes(RecipeDao recipeDao, List<PantryItem> pantry) {
        List<Recipe> suggested = new ArrayList<>();

        for (Recipe recipe : recipeDao.getAllRecipes()) {
            List<RecipeIngredient> needed = recipeDao.getIngredientsForRecipe(recipe.id);


            if (canCook(needed, pantry)) {
                suggested.add(recipe);
            }
        }
        return suggested;
    }


    public static boolean canCook(List<RecipeIngredient> needed, List<PantryItem> pantry) {
        if (needed.isEmpty()) {
            return false;
        }
        for (RecipeIngredient ingredient : needed) {
            if (!hasEnough(ingredient, pantry)) {
                return false; // one missing ingredient means NO match
            }
        }
        return true;
    }


    private static boolean hasEnough(RecipeIngredient ingredient, List<PantryItem> pantry) {
        String wantedName = normalize(ingredient.name);
        String wantedUnit = UnitConvertor.baseUnit(ingredient.unit);
        double wantedAmount = UnitConvertor.toBase(ingredient.quantity, ingredient.unit);

        double have = 0;
        for (PantryItem item : pantry) {
            boolean sameName = normalize(item.name).equals(wantedName);
            boolean sameUnit = UnitConvertor.baseUnit(item.unit).equals(wantedUnit);
            if (sameName && sameUnit) {
                have += UnitConvertor.toBase(item.quantity, item.unit);
            }
        }
        return have >= wantedAmount;
    }


    public static String normalize(String name) {
        String n = name.trim().toLowerCase(Locale.ROOT);

        if (n.endsWith("ies") && n.length() > 4) {
            n = n.substring(0, n.length() - 3) + "y";      // berries -> berry
        } else if (n.endsWith("oes") && n.length() > 4) {
            n = n.substring(0, n.length() - 2);            // tomatoes -> tomato
        } else if (n.endsWith("s") && !n.endsWith("ss") && n.length() > 3) {
            n = n.substring(0, n.length() - 1);            // eggs -> egg
        }
        return n;
    }
}
