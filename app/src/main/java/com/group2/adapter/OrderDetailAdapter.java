package com.group2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.CartItem;
import com.group2.model.Order;
import com.group2.model.OrderDetail;
import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderViewHolder> {
    private final List<OrderDetail> orders;
    NumberFormat numberFormat;
    OnClickListener onClickListener;
    Activity activity;
    public OrderDetailAdapter(Activity activity, List<OrderDetail> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_list_item_layout, parent, false);
        return new OrderDetailAdapter.OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderDetail order = orders.get(position);
        holder.oId.setText("Đơn hàng # " + order.getOrderCode());
        holder.txtOrderStatus.setText(order.getStatus());
        holder.txtShipDate.setText("Dự kiến nhận hàng vào ngày " + order.getOrderDate());
        if (!order.getCartItems().isEmpty()) {
            CartItem firstProduct = order.getCartItems().get(0);
            holder.oName.setText(firstProduct.getName());
            numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            holder.oPricebuy.setText(String.format("%s₫", numberFormat.format(firstProduct.getPrice())));
            holder.oQuantity.setText(String.valueOf(firstProduct.getQuantity()));
            Picasso.get()
                    .load(firstProduct.getThumb())
                    .fit()
                    .error(R.drawable.error_image)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.oThumb);
        }
        holder.oTotalPrice.setText(String.format("%s₫", numberFormat.format(order.getTotalPrice())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.OnClick(position, order);
                }
            }
        });
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void OnClick(int position, OrderDetail order);
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView oId, txtOrderStatus, txtShipDate, oName, oArtist, oOption, oPricebuy, oQuantity, oTotalPrice, oTotalQuantity;
        ImageView oThumb;

        public OrderViewHolder(View itemView) {
            super(itemView);
            oId = itemView.findViewById(R.id.o_id);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtShipDate = itemView.findViewById(R.id.txtShipDate);
            oThumb = itemView.findViewById(R.id.o_thumb);
            oName = itemView.findViewById(R.id.o_name);
            oArtist = itemView.findViewById(R.id.o_artist);
            oOption = itemView.findViewById(R.id.o_option);
            oPricebuy = itemView.findViewById(R.id.o_pricebuy);
            oQuantity = itemView.findViewById(R.id.o_quantity);
            oTotalPrice = itemView.findViewById(R.id.o_total_price);
            oTotalQuantity = itemView.findViewById(R.id.o_total_quantity);
        }
    }
}