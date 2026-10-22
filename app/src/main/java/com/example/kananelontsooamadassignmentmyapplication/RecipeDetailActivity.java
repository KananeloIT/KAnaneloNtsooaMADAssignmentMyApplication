package com.example.kananelontsooamadassignmentmyapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        String recipe = getIntent().getStringExtra("recipe"); if (recipe == null) recipe = "Recipe";
        android.widget.LinearLayout root = Screen.page(this, recipe);
        root.addView(Screen.text(this, "Ingredients", 19));
        root.addView(Screen.text(this, recipeContent(recipe), 16));
        root.addView(Screen.text(this, "Method", 19));
        root.addView(Screen.text(this, "1. Prepare and measure the ingredients.\n\n2. Cook gently in a pan until tender and well combined.\n\n3. Season to taste and serve warm.", 16));
        android.widget.Button back = Screen.button(this, "Back to recipes"); back.setOnClickListener(v -> finish()); root.addView(back, Screen.fill());
        Screen.navigation(this, root); setContentView(root);
    }
    private String recipeContent(String recipe) {
        if (recipe.contains("Tomato")) return "2 tomatoes\n2 eggs\n1 small onion\nSalt and pepper";
        if (recipe.contains("pasta")) return "200 g pasta\n2 handfuls spinach\n150 ml cream\nGarlic and seasoning";
        if (recipe.contains("Chickpea")) return "1 can chickpeas\n1 carrot\n1 onion\nCurry spices and coconut milk";
        return "3 potatoes\n1 bell pepper\n1 tbsp olive oil\nSalt and herbs";
    }
}
