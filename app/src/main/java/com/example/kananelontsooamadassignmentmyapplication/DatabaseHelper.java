package com.example.kananelontsooamadassignmentmyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pantry.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_PANTRY = "pantry";
    public static final String TABLE_RECIPE = "recipes";
    public static final String TABLE_RECIPE_ING = "recipe_ingredients";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity REAL, unit TEXT, expiry TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RECIPE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, r_name TEXT, r_steps TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_ING + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER, ri_name TEXT, ri_quantity REAL, ri_unit TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_ING);
        onCreate(db);
    }

    public void addIngredient(String name, double qty, String unit, String expiry) {
        ContentValues cv = new ContentValues();
        cv.put("name", name); cv.put("quantity", qty); cv.put("unit", unit); cv.put("expiry", expiry);
        getWritableDatabase().insert(TABLE_PANTRY, null, cv);
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        Cursor c = getReadableDatabase().query(TABLE_PANTRY, null, null, null, null, null, "name ASC");
        while (c.moveToNext()) {
            list.add(new Ingredient(c.getLong(0), c.getString(1), c.getDouble(2), c.getString(3), c.getString(4)));
        }
        c.close();
        return list;
    }

    public void updateIngredient(long id, String name, double qty, String unit, String expiry) {
        ContentValues cv = new ContentValues();
        cv.put("name", name); cv.put("quantity", qty); cv.put("unit", unit); cv.put("expiry", expiry);
        getWritableDatabase().update(TABLE_PANTRY, cv, "_id=?", new String[]{String.valueOf(id)});
    }

    public void deleteIngredient(long id) {
        getWritableDatabase().delete(TABLE_PANTRY, "_id=?", new String[]{String.valueOf(id)});
    }

    public Ingredient getIngredient(long id) {
        Cursor c = getReadableDatabase().query(TABLE_PANTRY, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (c.moveToFirst()) {
            Ingredient i = new Ingredient(c.getLong(0), c.getString(1), c.getDouble(2), c.getString(3), c.getString(4));
            c.close(); return i;
        }
        c.close(); return null;
    }

    public long addRecipe(String name, String steps) {
        ContentValues cv = new ContentValues();
        cv.put("r_name", name); cv.put("r_steps", steps);
        return getWritableDatabase().insert(TABLE_RECIPE, null, cv);
    }

    public void addRecipeIngredient(long recipeId, String name, double qty, String unit) {
        ContentValues cv = new ContentValues();
        cv.put("recipe_id", recipeId); cv.put("ri_name", name); cv.put("ri_quantity", qty); cv.put("ri_unit", unit);
        getWritableDatabase().insert(TABLE_RECIPE_ING, null, cv);
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Cursor rc = getReadableDatabase().query(TABLE_RECIPE, null, null, null, null, null, null);
        while (rc.moveToNext()) {
            long rid = rc.getLong(0);
            String rname = rc.getString(1);
            String steps = rc.getString(2);
            List<RecipeIngredient> ings = new ArrayList<>();
            Cursor ic = getReadableDatabase().query(TABLE_RECIPE_ING, null, "recipe_id=?", new String[]{String.valueOf(rid)}, null, null, null);
            while (ic.moveToNext()) {
                ings.add(new RecipeIngredient(ic.getString(2), ic.getDouble(3), ic.getString(4)));
            }
            ic.close();
            recipes.add(new Recipe(rid, rname, ings, steps));
        }
        rc.close();
        return recipes;
    }

    public int getRecipeCount() {
        Cursor c = getReadableDatabase().rawQuery("SELECT COUNT(*) FROM " + TABLE_RECIPE, null);
        int n = 0; if (c.moveToFirst()) n = c.getInt(0); c.close(); return n;
    }

    public void addPantryItem(String n, double amount, String u, String ex) {
    }

    public void updatePantryItem(int itemId, String n, double amount, String u, String ex) {
    }

    public List<PantryItem> getAllPantryItems() {
        return java.util.Collections.emptyList();
    }
}