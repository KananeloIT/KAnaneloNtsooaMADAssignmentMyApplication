package com.example.kananelontsooamadassignmentmyapplication;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS = "smart_pantry_settings";
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        android.widget.LinearLayout root = Screen.page(this, "Settings");
        root.addView(Screen.text(this, "Personalise your pantry experience.", 15));
        CheckBox reminders = new CheckBox(this); reminders.setText("Show expiry date reminders"); reminders.setTextSize(16);
        android.content.SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE); reminders.setChecked(prefs.getBoolean("expiry_reminders", true));
        reminders.setOnCheckedChangeListener((button, checked) -> prefs.edit().putBoolean("expiry_reminders", checked).apply()); root.addView(reminders, Screen.fill());
        root.addView(Screen.text(this, "About Smart Pantry", 19)); root.addView(Screen.text(this, "Smart Pantry helps you keep track of ingredients and find simple meal ideas. Pantry items are saved locally on this device using SQLite.", 16));
        android.widget.Button info = Screen.button(this, "Database: SQLite (on device)"); info.setOnClickListener(v -> Toast.makeText(this, "Your pantry is stored on this device.", Toast.LENGTH_SHORT).show()); root.addView(info, Screen.fill());
        Screen.navigation(this, root); setContentView(root);
    }
}
