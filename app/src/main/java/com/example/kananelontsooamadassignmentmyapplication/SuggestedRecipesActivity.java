package com.example.kananelontsooamadassignmentmyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recycler = findViewById(R.id.recyclerRecipes);
        emptyView = findViewById(R.id.textEmptyRecipes);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    // onResume runs EVERY time this screen is opened
    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }

    private void loadRecipes() {
        DatabaseHelper db = new DatabaseHelper(this);
        List<Recipe> allRecipes = db.getAllRecipes();
        List<Ingredient> pantry = db.getAllIngredients();

        List<Recipe> matched = RecipeMatcher.strictMatches(allRecipes, pantry);

        if (matched.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            recycler.setAdapter(new RecipeAdapter(matched, r -> {
                Intent intent = new Intent(this, RecipeDetailActivity.class);
                intent.putExtra("RECIPE_ID", r.getId());
                startActivity(intent);
            }));
        }
    }
}