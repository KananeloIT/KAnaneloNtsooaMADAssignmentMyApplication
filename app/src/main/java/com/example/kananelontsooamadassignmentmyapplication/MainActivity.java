package com.example.kananelontsooamadassignmentmyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private PantryAdapter adapter;
    private RecyclerView recycler;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        RecipeSeeder.seedIfEmpty(db);

        recycler = findViewById(R.id.recyclerPantry);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // Add Ingredient Button
        findViewById(R.id.fabAdd).setOnClickListener(v -> startActivity(new Intent(this, AddEditIngredientActivity.class)));

        // Normal Buttons
        findViewById(R.id.btnViewRecipes).setOnClickListener(v -> startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        findViewById(R.id.btnSettings).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        // BOTTOM NAVIGATION LOGIC
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_recipes) {
                startActivity(new Intent(this, SuggestedRecipesActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override protected void onResume() {
        super.onResume();
        loadPantry();
    }

    private void loadPantry() {
        List<Ingredient> list = db.getAllIngredients();
        adapter = new PantryAdapter(list,
                i -> { // Click to edit
                    Intent intent = new Intent(this, AddEditIngredientActivity.class);
                    intent.putExtra("INGREDIENT_ID", i.getId());
                    startActivity(intent);
                },
                i -> new AlertDialog.Builder(this) // Long press to delete
                        .setTitle("Delete " + i.getName() + "?")
                        .setPositiveButton("Yes", (d, w) -> { db.deleteIngredient(i.getId()); loadPantry(); })
                        .setNegativeButton("No", null).show()
        );
        recycler.setAdapter(adapter);
    }
}