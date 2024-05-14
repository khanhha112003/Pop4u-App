package com.group2.adapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.NumberFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.group2.model.Order;
import com.group2.pop4u_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Activity activity;
    ArrayList<Order> orders;
    NumberFormat numberFormat;

    public OrderAdapter(Activity activity, ArrayList<Order> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        Picasso.get()
                .load(order.getO_thumb())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.o_thumb);
        holder.o_artist.setText(order.getO_artist());
        holder.o_name.setText(order.getO_name());
        numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        holder.o_price.setText(String.format("%s₫", numberFormat.format(order.getO_price())));
        holder.o_quantity.setText(String.valueOf(order.getO_quantity()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView o_thumb;
        TextView o_name;
        TextView o_artist;
        TextView o_price;
        TextView o_quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            o_thumb = itemView.findViewById(R.id.o_thumb);
            o_name = itemView.findViewById(R.id.o_name);
            o_artist = itemView.findViewById(R.id.o_artist);
            o_price = itemView.findViewById(R.id.o_pricebuy);
            o_quantity = itemView.findViewById(R.id.o_quantity);
        }
    }
}

