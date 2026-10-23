package com.example.kananelontsooamadassignmentmyapplication;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMatcher {

    private static String cleanName(String s) {
        if (s == null) return "";
        s = s.trim().toLowerCase();
        // Handle plurals (eggs -> egg, tomatoes -> tomato)
        if (s.endsWith("ies") && s.length() > 4) return s.substring(0, s.length() - 3) + "y";
        if (s.endsWith("es") && s.length() > 3) return s.substring(0, s.length() - 2);
        if (s.endsWith("s") && s.length() > 3 && !s.endsWith("ss")) return s.substring(0, s.length() - 1);
        return s;
    }

    public static List<Recipe> strictMatches(List<Recipe> recipes, List<Ingredient> pantry) {
        Map<String, Double> pantryMap = new HashMap<>();

        // Add everything from the pantry to our map
        for (Ingredient ing : pantry) {
            String nameKey = cleanName(ing.getName());
            double currentQty = pantryMap.containsKey(nameKey) ? pantryMap.get(nameKey) : 0.0;
            pantryMap.put(nameKey, currentQty + ing.getQuantity());
        }

        List<Recipe> result = new ArrayList<>();

        for (Recipe r : recipes) {
            boolean canMakeIt = true;

            for (RecipeIngredient ri : r.getIngredients()) {
                String reqNameKey = cleanName(ri.name);

                // Check if we have the ingredient
                if (!pantryMap.containsKey(reqNameKey)) {
                    // Log why it failed
                    Log.d("MATCH_DEBUG", "Failed " + r.getName() + ": Missing ingredient " + reqNameKey);
                    canMakeIt = false;
                    break;
                }

                // Check if we have ENOUGH of it
                double availableQty = pantryMap.get(reqNameKey);
                if (availableQty < ri.quantity) {
                    Log.d("MATCH_DEBUG", "Failed " + r.getName() + ": Not enough " + reqNameKey + " (Have " + availableQty + ", Need " + ri.quantity + ")");
                    canMakeIt = false;
                    break;
                }
            }

            if (canMakeIt) {
                Log.d("MATCH_DEBUG", "SUCCESS: " + r.getName());
                result.add(r);
            }
        }
        return result;
    }
}