package com.example.kananelontsooamadassignmentmyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.VH> {
    public interface OnItemClick { void onClick(Ingredient i); }
    public interface OnItemLongClick { void onLongClick(Ingredient i); }

    private final List<Ingredient> items;
    private final OnItemClick click;
    private final OnItemLongClick longClick;

    public PantryAdapter(List<Ingredient> items, OnItemClick click, OnItemLongClick longClick) {
        this.items = items; this.click = click; this.longClick = longClick;
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int position) {
        Ingredient i = items.get(position);
        h.text1.setText(i.getName());
        h.text2.setText(i.getQuantity() + " " + i.getUnit());
        h.itemView.setOnClickListener(v -> click.onClick(i));
        h.itemView.setOnLongClickListener(v -> { longClick.onLongClick(i); return true; });
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView text1, text2;
        VH(@NonNull View v) { super(v); text1 = v.findViewById(android.R.id.text1); text2 = v.findViewById(android.R.id.text2); }
    }
}
