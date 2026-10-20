package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.database.AppDatabase;
import com.example.smartpantrymanager.database.RecipeSeeder;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.ui.PantryAdapter;
import android.content.Intent;
import com.example.smartpantrymanager.ui.AddEditActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyText;
    private PantryAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);
        RecipeSeeder.seedIfEmpty(this);

        recyclerView = findViewById(R.id.recyclerPantry);
        emptyText = findViewById(R.id.textEmpty);


        adapter = new PantryAdapter(new ArrayList<>(), item -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra(AddEditActivity.EXTRA_ITEM_ID, item.id);
            startActivity(intent);
        });

        findViewById(R.id.fabAdd).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEditActivity.class)));

        findViewById(R.id.textTitle).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, com.example.smartpantrymanager.ui.SuggestedRecipesActivity.class)));



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }


    private void loadItems() {
        List<PantryItem> items = db.pantryItemDAO().getAllItems();
        adapter.setItems(items);

        if (items.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}