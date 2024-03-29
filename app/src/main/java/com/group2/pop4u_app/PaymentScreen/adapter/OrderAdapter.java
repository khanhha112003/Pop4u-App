package com.group2.pop4u_app.PaymentScreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.group2.pop4u_app.CartScreen.adapter.CartAdapter;
import com.group2.pop4u_app.PaymentScreen.model.Order;
import com.group2.pop4u_app.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    ArrayList<Order> orders;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
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
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.o_thumb.setImageResource(orders.get(position).getO_thumb());
        holder.o_name.setText(orders.get(position).getO_name());
        holder.o_option.setText(orders.get(position).getO_option());
        holder.o_price.setText(String.valueOf(Math.round(orders.get(position).getO_price())));
        holder.o_quantity.setText(String.valueOf(Math.round(orders.get(position).getO_quantity())));
    }

    @Override
    public int getItemCount() {
        return orders.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView o_thumb;
        TextView o_name;
        TextView o_artist;
        TextView o_option;
        TextView o_price;
        TextView o_quantity;
        CheckBox checkbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            o_thumb = itemView.findViewById(R.id.o_thumb);
            o_name = itemView.findViewById(R.id.o_name);
            o_artist = itemView.findViewById(R.id.o_artist);
            o_option = itemView.findViewById(R.id.o_option);
            o_price = itemView.findViewById(R.id.o_pricebuy);
            o_quantity = itemView.findViewById(R.id.o_quantity);
            checkbox = itemView.findViewById(R.id.checkbox);

        }
    }

}

