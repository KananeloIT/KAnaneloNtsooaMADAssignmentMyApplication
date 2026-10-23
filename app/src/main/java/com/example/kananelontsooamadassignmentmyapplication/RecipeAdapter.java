package com.example.kananelontsooamadassignmentmyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.VH> {
    public interface OnRecipeClick { void onClick(Recipe r); }
    private final List<Recipe> items;
    private final OnRecipeClick click;

    public RecipeAdapter(List<Recipe> items, OnRecipeClick click) { this.items = items; this.click = click; }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int position) {
        Recipe r = items.get(position);
        h.text.setText(r.getName());
        h.itemView.setOnClickListener(v -> click.onClick(r));
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView text;
        VH(@NonNull View v) { super(v); text = v.findViewById(android.R.id.text1); }
    }
}