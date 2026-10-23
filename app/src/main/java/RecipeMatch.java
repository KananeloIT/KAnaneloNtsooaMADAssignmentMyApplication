package com.example.kananelontsooamadassignmentmyapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMatcher {
    private static String normName(String s) {
        if (s == null) return "";
        s = s.trim().toLowerCase();
        if (s.endsWith("ies") && s.length() > 4) return s.substring(0, s.length() - 3) + "y";
        if (s.endsWith("es") && s.length() > 3) return s.substring(0, s.length() - 2);
        if (s.endsWith("s") && s.length() > 3 && !s.endsWith("ss")) return s.substring(0, s.length() - 1);
        return s;
    }

    private static double toBase(double qty, String unit, String[] outBase) {
        if (unit == null) unit = "";
        unit = unit.trim().toLowerCase();
        switch (unit) {
            case "kg": outBase[0] = "g"; return qty * 1000;
            case "g":  outBase[0] = "g"; return qty;
            case "l":  outBase[0] = "ml"; return qty * 1000;
            case "ml": outBase[0] = "ml"; return qty;
            default:   outBase[0] = "unit"; return qty;
        }
    }

    public static List<Recipe> strictMatches(List<Recipe> recipes, List<Ingredient> pantry) {
        Map<String, Double> pantryMap = new HashMap<>();
        for (Ingredient ing : pantry) {
            String key = normName(ing.getName());
            String[] base = new String[1];
            double q = toBase(ing.getQuantity(), ing.getUnit(), base);
            String finalKey = key + "_" + base[0];
            pantryMap.put(finalKey, pantryMap.getOrDefault(finalKey, 0.0) + q);
        }

        List<Recipe> result = new ArrayList<>();
        for (Recipe r : recipes) {
            boolean ok = true;
            for (RecipeIngredient ri : r.getIngredients()) {
                String key = normName(ri.name);
                String[] reqBase = new String[1];
                double reqQty = toBase(ri.quantity, ri.unit, reqBase);
                String finalKey = key + "_" + reqBase[0];
                double available = pantryMap.getOrDefault(finalKey, 0.0);
                if (available < reqQty) { ok = false; break; }
            }
            if (ok) result.add(r);
        }
        return result;
    }
}