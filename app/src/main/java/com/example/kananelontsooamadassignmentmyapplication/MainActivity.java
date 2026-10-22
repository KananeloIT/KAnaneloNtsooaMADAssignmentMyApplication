package com.example.kananelontsooamadassignmentmyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper database;
    private PantryAdapter adapter;
    private ListView list;
    private TextView empty;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        database = new DatabaseHelper(this);
        android.widget.LinearLayout root = Screen.page(this, "My Pantry");
        root.addView(Screen.text(this, "Keep track of ingredients and their expiry dates.", 15));
        android.widget.Button add = Screen.button(this, "+  Add ingredient"); add.setOnClickListener(v -> startActivity(new Intent(this, IngredientActivity.class))); root.addView(add, Screen.fill());
        empty = Screen.text(this, "Your pantry is empty. Add an ingredient to get started.", 15); empty.setGravity(android.view.Gravity.CENTER);
        list = new ListView(this); list.setDividerHeight(Screen.dp(this, 8)); list.setBackgroundColor(0xFFF8F7F1);
        root.addView(empty, new android.widget.LinearLayout.LayoutParams(-1, 0, 1)); root.addView(list, new android.widget.LinearLayout.LayoutParams(-1, 0, 1));
        Screen.navigation(this, root); setContentView(root);
    }

    @Override protected void onResume() { super.onResume(); if (database != null && list != null) refreshItems(); }
    private void refreshItems() {
        java.util.List<PantryItem> items = database.getAllPantryItems();
        if (adapter == null) { adapter = new PantryAdapter(this, items, database, this::refreshItems); list.setAdapter(adapter); }
        else { adapter.clear(); adapter.addAll(items); adapter.notifyDataSetChanged(); }
        boolean isEmpty = items.isEmpty(); empty.setVisibility(isEmpty ? View.VISIBLE : View.GONE); list.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
    @Override protected void onDestroy() { if (database != null) database.close(); super.onDestroy(); }
}
