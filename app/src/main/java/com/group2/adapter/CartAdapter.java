package com.group2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.database_helper.OrderDatabaseHelper;
import com.group2.model.CartItem;
import com.group2.pop4u_app.Activity.CartFragment;
import com.group2.pop4u_app.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    ArrayList<CartItem> carts;
    public OnTotalPriceChangeListener totalPriceChangeListener;
    public OnQuantityChangeListener quantityChangeListener;

    public CartAdapter(Context context, ArrayList<CartItem> carts) {
        this.context = context;
        this.carts = carts;
    }

    public CartAdapter(CartFragment cartFragment, int activity_item_cart, OrderDatabaseHelper orderdb) {

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
        CartItem item = carts.get(position);
        holder.thumb.setImageResource(item.getThumb());
        holder.name.setText(item.getName());
        holder.option.setText(item.getOption());
        DecimalFormat df = new DecimalFormat("#,###"); // Sử dụng "#,###" nếu không muốn hiển thị phần thập phân
        String formattedPrice = df.format(item.getPrice());
        holder.price.setText(formattedPrice);
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.checkbox.setChecked(item.isChecked());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
                calculateTotalPrice();
                // Kiểm tra xem có ít nhất một checkbox cá nhân không được chọn
                boolean atLeastOneUnchecked = false;
                for (CartItem cartItem : carts) {
                    if (!cartItem.isChecked()) {
                        atLeastOneUnchecked = true;
                        break;
                    }
                }

                // Nếu có ít nhất một checkbox cá nhân không được chọn, hãy làm cho checkbox "Chọn tất cả" không được chọn
                if (atLeastOneUnchecked && totalPriceChangeListener != null) {
                    totalPriceChangeListener.onAtLeastOneUnchecked();
                }
            }
        });
    }
    private void calculateTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : carts) {
            if (item.isChecked()) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
        }
        if (totalPriceChangeListener != null) {
            totalPriceChangeListener.onTotalPriceChange(totalPrice);
        }
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    public void setOnTotalPriceChangeListener(OnTotalPriceChangeListener listener) {
        this.totalPriceChangeListener = listener;
    }
    public void selectAllItems(boolean isSelected) {
        for (CartItem item : carts) {
            item.setChecked(isSelected);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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


        }
    }
    public interface OnTotalPriceChangeListener {
        void onTotalPriceChange(double totalPrice);
        void onAtLeastOneUnchecked();
    }
    public interface OnQuantityChangeListener {
        void onQuantityDecrease(int position);
        void onQuantityIncrease(int position);
    }

}
