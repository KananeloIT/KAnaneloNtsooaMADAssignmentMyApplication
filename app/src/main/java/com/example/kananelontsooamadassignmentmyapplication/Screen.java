package com.example.kananelontsooamadassignmentmyapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

final class Screen {
    static final int GREEN = Color.rgb(43, 105, 78);
    private Screen() {}

    static LinearLayout page(Context c, String title) {
        LinearLayout root = new LinearLayout(c); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(Color.rgb(248, 247, 241));
        root.setPadding(dp(c, 20), dp(c, 24), dp(c, 20), dp(c, 12));
        TextView heading = new TextView(c); heading.setText(title); heading.setTextSize(27); heading.setTypeface(null, Typeface.BOLD); heading.setTextColor(GREEN);
        root.addView(heading, new LinearLayout.LayoutParams(-1, -2));
        return root;
    }

    static TextView text(Context c, String value, int size) { TextView t = new TextView(c); t.setText(value); t.setTextSize(size); t.setTextColor(Color.rgb(48, 54, 48)); t.setPadding(0, dp(c, 8), 0, dp(c, 8)); return t; }
    static Button button(Context c, String label) { Button b = new Button(c); b.setText(label); b.setAllCaps(false); return b; }
    static LinearLayout.LayoutParams fill() { return new LinearLayout.LayoutParams(-1, -2); }
    static int dp(Context c, int value) { return (int)(value * c.getResources().getDisplayMetrics().density + 0.5f); }

    static void navigation(Context c, LinearLayout root) {
        LinearLayout nav = new LinearLayout(c); nav.setGravity(Gravity.CENTER); nav.setPadding(0, dp(c, 8), 0, 0);
        addNav(c, nav, "Pantry", MainActivity.class); addNav(c, nav, "Recipes", RecipesActivity.class); addNav(c, nav, "Settings", SettingsActivity.class);
        root.addView(nav, new LinearLayout.LayoutParams(-1, -2));
    }
    private static void addNav(Context c, LinearLayout nav, String label, Class<?> target) {
        Button b = button(c, label); b.setTextSize(13); b.setOnClickListener(v -> c.startActivity(new Intent(c, target)));
        nav.addView(b, new LinearLayout.LayoutParams(0, -2, 1));
    }
}
