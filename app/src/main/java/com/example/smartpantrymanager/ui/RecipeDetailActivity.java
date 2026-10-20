package com.example.smartpantrymanager.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.database.AppDatabase;
import com.example.smartpantrymanager.model.Recipe;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.List;


public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        AppDatabase db = AppDatabase.getInstance(this);
        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);
        Recipe recipe = db.recipeDao().getRecipeById(recipeId);
        if (recipe == null) {
            finish();
            return;
        }

        List<RecipeIngredient> ingredients = db.recipeDao().getIngredientsForRecipe(recipeId);
        StringBuilder text = new StringBuilder();
        for (RecipeIngredient ing : ingredients) {
            double q = ing.quantity;
            String qty = (q == (long) q) ? String.valueOf((long) q) : String.valueOf(q);
            text.append("• ").append(ing.name).append(" - ")
                    .append(qty).append(" ").append(ing.unit).append("\n");
        }

        ((TextView) findViewById(R.id.textDetailName)).setText(recipe.name);
        ((TextView) findViewById(R.id.textDetailIngredients)).setText(text.toString().trim());
        ((TextView) findViewById(R.id.textDetailSteps)).setText(recipe.steps);
    }
}
