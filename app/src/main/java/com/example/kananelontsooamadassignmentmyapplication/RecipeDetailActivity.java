package com.example.kananelontsooamadassignmentmyapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        long recipeId = getIntent().getLongExtra("RECIPE_ID", -1);
        DatabaseHelper db = new DatabaseHelper(this);
        List<Recipe> all = db.getAllRecipes();
        Recipe recipe = null;
        for (Recipe r : all) if (r.getId() == recipeId) recipe = r;

        if (recipe != null) {
            ((TextView) findViewById(R.id.txtDetailName)).setText(recipe.getName());
            ((TextView) findViewById(R.id.txtDetailSteps)).setText(recipe.getSteps());

            StringBuilder sb = new StringBuilder();
            for (RecipeIngredient ri : recipe.getIngredients()) {
                sb.append("• ").append(ri.quantity).append(" ").append(ri.unit).append(" ").append(ri.name).append("\n");
            }
            ((TextView) findViewById(R.id.txtDetailIngredients)).setText(sb.toString());
        }
    }
}