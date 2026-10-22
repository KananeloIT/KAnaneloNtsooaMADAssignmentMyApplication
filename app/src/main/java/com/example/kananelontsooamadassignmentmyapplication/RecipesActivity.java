package com.example.kananelontsooamadassignmentmyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    private static final String[] RECIPES = {"Tomato & egg skillet", "Creamy spinach pasta", "Chickpea vegetable curry", "Roasted potato bowl"};
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        android.widget.LinearLayout root = Screen.page(this, "Suggested recipes");
        List<PantryItem> pantry = new DatabaseHelper(this).getAllPantryItems();
        StringBuilder ingredients = new StringBuilder(); for (PantryItem item : pantry) { if (ingredients.length() > 0) ingredients.append(", "); ingredients.append(item.getName()); }
        root.addView(Screen.text(this, ingredients.length() == 0 ? "Add pantry items to see what you can cook with." : "Ideas inspired by your pantry: " + ingredients, 15));
        ListView list = new ListView(this); list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, RECIPES));
        list.setOnItemClickListener((parent, view, position, id) -> { Intent i = new Intent(this, RecipeDetailActivity.class); i.putExtra("recipe", RECIPES[position]); startActivity(i); });
        root.addView(list, new android.widget.LinearLayout.LayoutParams(-1, 0, 1)); Screen.navigation(this, root); setContentView(root);
    }
}
