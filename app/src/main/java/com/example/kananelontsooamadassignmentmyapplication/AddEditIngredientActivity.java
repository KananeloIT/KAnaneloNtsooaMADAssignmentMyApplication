package com.example.kananelontsooamadassignmentmyapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText editName, editQty, editUnit, editExpiry;
    private long ingredientId = -1;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        db = new DatabaseHelper(this);
        editName = findViewById(R.id.editName);
        editQty = findViewById(R.id.editQty);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);
        Button btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("INGREDIENT_ID")) {
            ingredientId = getIntent().getLongExtra("INGREDIENT_ID", -1);
            Ingredient i = db.getIngredient(ingredientId);
            if (i != null) {
                editName.setText(i.getName());
                editQty.setText(String.valueOf(i.getQuantity()));
                editUnit.setText(i.getUnit());
                editExpiry.setText(i.getExpiryDate());
            }
        }

        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String qtyStr = editQty.getText().toString().trim();
            String unit = editUnit.getText().toString().trim();
            String expiry = editExpiry.getText().toString().trim();

            if (name.isEmpty() || qtyStr.isEmpty()) {
                Toast.makeText(this, "Name and Quantity are required", Toast.LENGTH_SHORT).show();
                return;
            }

            double qty = Double.parseDouble(qtyStr);
            if (ingredientId == -1) {
                db.addIngredient(name, qty, unit, expiry);
            } else {
                db.updateIngredient(ingredientId, name, qty, unit, expiry);
            }
            finish();
        });
    }
}