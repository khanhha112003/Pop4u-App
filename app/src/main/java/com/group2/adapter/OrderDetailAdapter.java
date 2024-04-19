package com.group2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.Order;
import com.group2.pop4u_app.OrderDetailSrc.OrderDetail;
import com.group2.pop4u_app.R;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    ArrayList<Order> orders;

    public OrderDetailAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public OrderDetailAdapter(Context context, int order_item_layout, List<Order> initData) {
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imvThumb;
        TextView txtName, txtArtist, txtQuantity, txtPrice, txtOption;

        public ViewHolder(@NonNull View view){
            super(view);
            imvThumb = view.findViewById(R.id.o_thumb);
            txtName = view.findViewById(R.id.o_name);
            txtArtist = view.findViewById(R.id.o_artist);
            txtQuantity = view.findViewById(R.id.o_quantity);
            txtPrice = view.findViewById(R.id.o_pricebuy);
            txtOption = view.findViewById(R.id.o_option);
        }
    }
    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.txtName.setText(order.getO_name() + "");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
