package com.example.smartpantrymanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.PantryItem;

import java.util.List;


public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    private List<PantryItem> items;
    private OnItemClickListener listener;

    public PantryAdapter(List<PantryItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }


    public void setItems(List<PantryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, expiry;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textItemName);
            quantity = view.findViewById(R.id.textItemQuantity);
            expiry = view.findViewById(R.id.textItemExpiry);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.name.setText(item.name);


        String qty = (item.quantity == (long) item.quantity)
                ? String.valueOf((long) item.quantity)
                : String.valueOf(item.quantity);
        holder.quantity.setText(qty + " " + item.unit);

        if (item.expiryDate == null || item.expiryDate.isEmpty()) {
            holder.expiry.setText("No expiry date");
        } else {
            holder.expiry.setText("Expires: " + item.expiryDate);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}