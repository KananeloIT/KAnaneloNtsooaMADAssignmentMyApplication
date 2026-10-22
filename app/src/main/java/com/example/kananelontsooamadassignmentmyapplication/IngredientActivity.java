package com.example.kananelontsooamadassignmentmyapplication;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IngredientActivity extends AppCompatActivity {
    private EditText name, quantity, unit, expiry;
    private DatabaseHelper database;
    private int itemId = -1;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state); database = new DatabaseHelper(this);
        itemId = getIntent().getIntExtra("item_id", -1);
        android.widget.LinearLayout root = Screen.page(this, itemId < 0 ? "Add ingredient" : "Edit ingredient");
        root.addView(Screen.text(this, "Enter the details for an item in your pantry.", 15));
        name = field("Ingredient name", InputType.TYPE_CLASS_TEXT); quantity = field("Quantity", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); unit = field("Unit (e.g. g, ml, pieces)", InputType.TYPE_CLASS_TEXT); expiry = field("Expiry date (optional, e.g. 2026-12-31)", InputType.TYPE_CLASS_TEXT);
        root.addView(name, Screen.fill()); root.addView(quantity, Screen.fill()); root.addView(unit, Screen.fill()); root.addView(expiry, Screen.fill());
        if (itemId >= 0) { name.setText(getIntent().getStringExtra("name")); quantity.setText(String.valueOf(getIntent().getDoubleExtra("quantity", 0))); unit.setText(getIntent().getStringExtra("unit")); expiry.setText(getIntent().getStringExtra("expiry")); }
        android.widget.Button save = Screen.button(this, "Save ingredient"); save.setOnClickListener(v -> saveItem()); root.addView(save, Screen.fill());
        android.widget.Button cancel = Screen.button(this, "Cancel"); cancel.setOnClickListener(v -> finish()); root.addView(cancel, Screen.fill());
        Screen.navigation(this, root); setContentView(root);
    }

    private EditText field(String hint, int type) { EditText e = new EditText(this); e.setSingleLine(true); e.setHint(hint); e.setInputType(type); e.setPadding(Screen.dp(this, 12), 0, Screen.dp(this, 12), 0); return e; }
    private void saveItem() {
        String n = name.getText().toString().trim(), q = quantity.getText().toString().trim(), u = unit.getText().toString().trim(), ex = expiry.getText().toString().trim();
        if (n.isEmpty()) { name.setError("Enter an ingredient name"); name.requestFocus(); return; }
        if (q.isEmpty()) { quantity.setError("Enter a quantity"); quantity.requestFocus(); return; }
        double amount; try { amount = Double.parseDouble(q); } catch (NumberFormatException e) { quantity.setError("Enter a valid number"); return; }
        if (amount <= 0) { quantity.setError("Quantity must be greater than zero"); return; }
        if (u.isEmpty()) { unit.setError("Enter a unit"); unit.requestFocus(); return; }
        if (itemId < 0) database.addPantryItem(n, amount, u, ex); else database.updatePantryItem(itemId, n, amount, u, ex);
        Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show(); finish();
    }
    @Override protected void onDestroy() { if (database != null) database.close(); super.onDestroy(); }
}
