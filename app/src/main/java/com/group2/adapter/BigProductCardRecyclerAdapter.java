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

public class BigProductCardRecyclerAdapter extends RecyclerView.Adapter<BigProductCardRecyclerAdapter.ViewHolder> {
    Activity activity;
    List<Product> productList;

    public BigProductCardRecyclerAdapter(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_large_card_sale, parent, false);
        return new BigProductCardRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imvLargeProductImage.setImageResource(productList.get(position).getProductImage1());
        holder.txtProductLabel.setText(productList.get(position).getProductLabel());
        holder.txtRating.setText(String.valueOf(productList.get(position).getProductRating()));
        holder.txtSalePercent.setText("-" + String.valueOf(productList.get(position).getProductSalePercent()) + "%");
        holder.txtProductArtist.setText(productList.get(position).getProductArtistName());
        holder.txtProductPrice.setText(String.valueOf(productList.get(position).getProductPrice()));
        holder.txtProductName.setText(productList.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductArtist, txtProductPrice, txtProductLabel, txtRating, txtSalePercent;
        ImageView imvLargeProductImage;

        public ViewHolder(View view) {
            super(view);

            txtProductArtist = (TextView) view.findViewById(R.id.txtProductArtist);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
            imvLargeProductImage = (ImageView) view.findViewById(R.id.imvLargeProductImage);
            txtProductLabel = view.findViewById(R.id.txtProductLabel);
            txtRating = view.findViewById(R.id.txtRating);
            txtSalePercent = view.findViewById(R.id.txtSalePercent);
        }
    }


}
