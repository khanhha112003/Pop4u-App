package com.group2.pop4u_app.CartScreen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.pop4u_app.CartScreen.model.CartItem;
import com.group2.pop4u_app.R;
import com.group2.pop4u_app.databinding.ActivityCartScreenBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context context;
    ArrayList<CartItem> carts;
    private OnQuantityChangeListener quantityChangeListener;

    private boolean isAllSelected = false;
    public CartAdapter(Context context, ArrayList<CartItem> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.thumb.setImageResource(carts.get(position).getThumb());
        holder.name.setText(carts.get(position).getName());
        holder.option.setText(carts.get(position).getOption());
        holder.price.setText(carts.get(position).getPrice());
        holder.quantity.setText(carts.get(position).getQuantity());
    }
    public interface OnQuantityChangeListener {
        void onQuantityDecrease(int position);
        void onQuantityIncrease(int position);
    }
    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }
    public void selectAllItems(boolean isSelected) {
        isAllSelected = isSelected;
        for (CartItem item : carts) {
            item.setChecked(isSelected);
        }
        notifyDataSetChanged();
    }

    public boolean isAllSelected() {
        return isAllSelected;
    }

    @Override
    public int getItemCount() {
        return carts.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumb;
        TextView name;
        TextView option;
        TextView price;
        TextView quantity;
        CheckBox checkbox;
        ImageButton btnDecrease;
        ImageButton btnIncrease;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.thumb);
            name = itemView.findViewById(R.id.name);
            option = itemView.findViewById(R.id.option);
            price = itemView.findViewById(R.id.pricebuy);
            quantity = itemView.findViewById(R.id.quantity);
            checkbox = itemView.findViewById(R.id.checkbox);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);

            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && quantityChangeListener != null) {
                        quantityChangeListener.onQuantityDecrease(position);
                    }
                }
            });

            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && quantityChangeListener != null) {
                        quantityChangeListener.onQuantityIncrease(position);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getAdapterPosition());
                }
            });
        }
    }
    private void removeItem(int pos){
        carts.remove(pos);
        notifyItemRemoved(pos);
    }
}

