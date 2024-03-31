package com.group2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.Product;
import com.group2.pop4u_app.R;

import java.util.List;

public class SaleProductCardRecyclerAdapter extends RecyclerView.Adapter<SaleProductCardRecyclerAdapter.ViewHolder> {
    Activity activity;
    List<Product> productList;

    OnClickListener onClickListener;

    public SaleProductCardRecyclerAdapter(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_mini_card_sale, parent, false);

        return new SaleProductCardRecyclerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imvProductImage.setImageResource(product.getProductImage1());
        holder.txtProductSalePercent.setText("-" + String.valueOf(product.getProductSalePercent()) + "%");
        holder.txtProductSaleSoldAmount.setText("Đã bán " + String.valueOf(product.getProductSoldAmount()));
        holder.txtProductPrice.setText(String.valueOf(product.getProductPrice()) + "₫");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener{
        void onClick(int position, Product product);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView  txtProductPrice, txtProductSalePercent, txtProductSaleSoldAmount;
        ImageView imvProductImage;

        public ViewHolder(View view) {
            super(view);

            txtProductSaleSoldAmount = (TextView) view.findViewById(R.id.txtProductSaleSoldAmount);
            txtProductSalePercent = (TextView) view.findViewById(R.id.txtProductSalePercent);
            txtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
            imvProductImage = (ImageView) view.findViewById(R.id.imvProductImage);
        }
    }

}
