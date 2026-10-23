package com.example.kananelontsooamadassignmentmyapplication;

public class RecipeSeeder {
    public static void seedIfEmpty(DatabaseHelper db) {
        if (db.getRecipeCount() > 0) return;
        seed(db, "Scrambled Eggs", "1. Beat eggs. 2. Fry in butter. 3. Season.", new String[][]{{"eggs", "3", "unit"}, {"butter", "20", "g"}, {"salt", "1", "tsp"}});
        seed(db, "Tomato Pasta", "1. Boil pasta. 2. Fry tomatoes. 3. Mix.", new String[][]{{"pasta", "200", "g"}, {"tomatoes", "3", "unit"}, {"oil", "15", "ml"}});
        seed(db, "Cheese Omelette", "1. Beat eggs. 2. Add cheese. 3. Cook.", new String[][]{{"eggs", "2", "unit"}, {"cheese", "50", "g"}});
        seed(db, "Garlic Fried Rice", "1. Cook rice. 2. Fry garlic. 3. Mix.", new String[][]{{"rice", "200", "g"}, {"garlic", "3", "unit"}, {"oil", "20", "ml"}});
        seed(db, "Simple Salad", "1. Chop veggies. 2. Toss with oil.", new String[][]{{"lettuce", "1", "unit"}, {"tomatoes", "1", "unit"}, {"oil", "10", "ml"}});
        seed(db, "Pancakes", "1. Mix flour, milk, eggs. 2. Cook.", new String[][]{{"flour", "200", "g"}, {"milk", "200", "ml"}, {"eggs", "2", "unit"}});
        seed(db, "Grilled Cheese", "1. Butter bread. 2. Add cheese. 3. Grill.", new String[][]{{"bread", "2", "unit"}, {"cheese", "40", "g"}});
        seed(db, "Tomato Soup", "1. Boil tomatoes. 2. Blend.", new String[][]{{"tomatoes", "4", "unit"}, {"onion", "1", "unit"}});
        seed(db, "Potato Hash", "1. Fry potatoes and onion.", new String[][]{{"potato", "2", "unit"}, {"onion", "1", "unit"}});
        seed(db, "Egg Fried Rice", "1. Scramble eggs. 2. Mix with rice.", new String[][]{{"rice", "200", "g"}, {"eggs", "2", "unit"}});
        seed(db, "Butter Toast", "1. Toast bread. 2. Spread butter.", new String[][]{{"bread", "2", "unit"}, {"butter", "15", "g"}});
        seed(db, "Cheese Toast", "1. Toast bread. 2. Melt cheese.", new String[][]{{"bread", "2", "unit"}, {"cheese", "30", "g"}});
        seed(db, "Garlic Pasta", "1. Boil pasta. 2. Fry garlic in butter.", new String[][]{{"pasta", "200", "g"}, {"garlic", "3", "unit"}});
        seed(db, "Onion Omelette", "1. Fry onion. 2. Add eggs.", new String[][]{{"onion", "1", "unit"}, {"eggs", "3", "unit"}});
        seed(db, "Potato Soup", "1. Boil potatoes. 2. Add milk.", new String[][]{{"potato", "2", "unit"}, {"milk", "150", "ml"}});
        seed(db, "Cheese Sandwich", "1. Put cheese in bread.", new String[][]{{"bread", "2", "unit"}, {"cheese", "40", "g"}});
        seed(db, "Tomato Salad", "1. Slice tomatoes. 2. Add oil.", new String[][]{{"tomatoes", "2", "unit"}, {"oil", "10", "ml"}});
        seed(db, "Milk Pancake", "1. Mix flour, milk. 2. Cook.", new String[][]{{"flour", "150", "g"}, {"milk", "150", "ml"}});
    }

    private static void seed(DatabaseHelper db, String name, String steps, String[][] ings) {
        long rid = db.addRecipe(name, steps);
        for (String[] ing : ings) {
            db.addRecipeIngredient(rid, ing[0], Double.parseDouble(ing[1]), ing[2]);
        }
    }
}
