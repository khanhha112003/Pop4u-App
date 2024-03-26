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

public class MiniProductCardRecyclerAdapter extends RecyclerView.Adapter<MiniProductCardRecyclerAdapter.ViewHolder>{
    Activity activity;
    List<Product> productList;

    public MiniProductCardRecyclerAdapter(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_mini_card, parent, false);

        return new MiniProductCardRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imvProductImage.setImageResource(productList.get(position).getProductImage1());
        holder.txtProductPrice.setText(String.valueOf(productList.get(position).getProductPrice()) + "₫");
        holder.txtProductName.setText(productList.get(position).getProductName());
        holder.txtProductArtist.setText(productList.get(position).getProductArtistName());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductArtist, txtProductPrice;
        ImageView imvProductImage;

        public ViewHolder(View view) {
            super(view);

            txtProductArtist = (TextView) view.findViewById(R.id.txtProductArtist);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
            imvProductImage = (ImageView) view.findViewById(R.id.imvProductImage);
        }
    }

}
