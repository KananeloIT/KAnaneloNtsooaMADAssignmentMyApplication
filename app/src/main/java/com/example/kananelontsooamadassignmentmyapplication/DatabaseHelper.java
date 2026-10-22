package com.example.kananelontsooamadassignmentmyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smartpantry.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "pantry_items";
    private static final String ID = "id", NAME = "name", QUANTITY = "quantity", UNIT = "unit", EXPIRY = "expiry_date";

    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + QUANTITY + " REAL NOT NULL, " + UNIT + " TEXT NOT NULL, " + EXPIRY + " TEXT)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public long addPantryItem(String name, double quantity, String unit, String expiry) {
        ContentValues v = values(name, quantity, unit, expiry);
        return getWritableDatabase().insert(TABLE, null, v);
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> items = new ArrayList<>();
        try (Cursor c = getReadableDatabase().query(TABLE, null, null, null, null, null, NAME + " COLLATE NOCASE ASC")) {
            while (c.moveToNext()) items.add(new PantryItem(c.getInt(c.getColumnIndexOrThrow(ID)), c.getString(c.getColumnIndexOrThrow(NAME)), c.getDouble(c.getColumnIndexOrThrow(QUANTITY)), c.getString(c.getColumnIndexOrThrow(UNIT)), c.getString(c.getColumnIndexOrThrow(EXPIRY))));
        }
        return items;
    }

    public int updatePantryItem(int id, String name, double quantity, String unit, String expiry) {
        return getWritableDatabase().update(TABLE, values(name, quantity, unit, expiry), ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deletePantryItem(int id) { return getWritableDatabase().delete(TABLE, ID + "=?", new String[]{String.valueOf(id)}); }

    private ContentValues values(String name, double quantity, String unit, String expiry) {
        ContentValues v = new ContentValues(); v.put(NAME, name); v.put(QUANTITY, quantity); v.put(UNIT, unit); v.put(EXPIRY, expiry); return v;
    }
}
