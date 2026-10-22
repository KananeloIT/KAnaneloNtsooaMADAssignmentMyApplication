package com.example.kananelontsooamadassignmentmyapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class PantryAdapter extends ArrayAdapter<PantryItem> {
    private final Context context;
    private final DatabaseHelper database;
    private final Runnable refresh;

    public PantryAdapter(Context context, List<PantryItem> items, DatabaseHelper database, Runnable refresh) {
        super(context, android.R.layout.simple_list_item_1, items); this.context = context; this.database = database; this.refresh = refresh;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        PantryItem item = getItem(position);
        LinearLayout row = new LinearLayout(context); row.setGravity(Gravity.CENTER_VERTICAL); row.setPadding(Screen.dp(context, 12), Screen.dp(context, 8), Screen.dp(context, 4), Screen.dp(context, 8)); row.setBackgroundColor(0xFFFFFFFF);
        LinearLayout info = new LinearLayout(context); info.setOrientation(LinearLayout.VERTICAL);
        TextView name = Screen.text(context, item.getName(), 17); name.setTypeface(null, 1);
        String details = item.getQuantity() + " " + item.getUnit() + (item.getExpiryDate().isEmpty() ? "" : "  ·  expires " + item.getExpiryDate());
        info.addView(name); info.addView(Screen.text(context, details, 13));
        row.addView(info, new LinearLayout.LayoutParams(0, -2, 1));
        Button delete = Screen.button(context, "Delete"); delete.setTextSize(12); row.addView(delete);
        row.setOnClickListener(v -> { Intent i = new Intent(context, IngredientActivity.class); i.putExtra("item_id", item.getId()); i.putExtra("name", item.getName()); i.putExtra("quantity", item.getQuantity()); i.putExtra("unit", item.getUnit()); i.putExtra("expiry", item.getExpiryDate()); context.startActivity(i); });
        delete.setOnClickListener(v -> new AlertDialog.Builder(context).setTitle("Delete " + item.getName() + "?").setMessage("This removes the ingredient from your pantry.").setNegativeButton("Cancel", null).setPositiveButton("Delete", (d, w) -> { database.deletePantryItem(item.getId()); refresh.run(); }).show());
        return row;
    }
}
