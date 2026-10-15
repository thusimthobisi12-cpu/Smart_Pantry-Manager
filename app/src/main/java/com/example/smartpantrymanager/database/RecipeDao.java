package com.example.smartpantrymanager.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.List;


@Dao
public interface RecipeDao {


    @Insert
    long insertRecipe(Recipe recipe);


    @Insert
    void insertIngredients(List<RecipeIngredient> ingredients);


    @Query("SELECT * FROM recipes ORDER BY name ASC")
    List<Recipe> getAllRecipes();


    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    Recipe getRecipeById(int recipeId);


    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);


    @Query("SELECT COUNT(*) FROM recipes")
    int getRecipeCount();
}
