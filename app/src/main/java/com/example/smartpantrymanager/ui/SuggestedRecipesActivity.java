package com.example.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.database.AppDatabase;
import com.example.smartpantrymanager.database.RecipeMatcher;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.model.Recipe;

import java.util.ArrayList;
import java.util.List;


public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noRecipesText;
    private RecipeAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);
        db = AppDatabase.getInstance(this);

        recyclerView = findViewById(R.id.recyclerRecipes);
        noRecipesText = findViewById(R.id.textNoRecipes);


        adapter = new RecipeAdapter(new ArrayList<>(), recipe -> {
            Intent intent = new Intent(this,RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<PantryItem> pantry = db.pantryItemDAO().getAllItems();
        List<Recipe> suggested = RecipeMatcher.getSuggestedRecipes(db.recipeDao(), pantry);
        adapter.setRecipes(suggested);

        if (suggested.isEmpty()) {
            noRecipesText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noRecipesText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
