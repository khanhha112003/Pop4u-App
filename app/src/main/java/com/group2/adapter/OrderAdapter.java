package com.group2.adapter;

import android.content.Context;
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

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
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
        View view = inflater.inflate(R.layout.order_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.txtOrderID.setText("Đơn hàng: " + order.getO_id());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rvDetail.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(orders.size());
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orders);
        holder.rvDetail.setLayoutManager(layoutManager);
        holder.rvDetail.setAdapter(orderDetailAdapter);
        holder.rvDetail.setRecycledViewPool(viewPool);

//        holder.o_thumb.setImageResource(order.getO_thumb());
        holder.o_name.setText(order.getO_name());
        holder.o_option.setText(order.getO_option());
        holder.o_price.setText(String.valueOf(Math.round(order.getO_price())));
        holder.o_quantity.setText(String.valueOf(Math.round(order.getO_quantity())));
    }

    @Override
    public int getItemCount() {
        return orders.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtOrderID;
        RecyclerView rvDetail;
        ImageView o_thumb;
        TextView o_name;
        TextView o_artist;
        TextView o_option;
        TextView o_price;
        TextView o_quantity;
        CheckBox checkbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderID = itemView.findViewById(R.id.txtOrderID);
            rvDetail = itemView.findViewById(R.id.rvOrdered);
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

