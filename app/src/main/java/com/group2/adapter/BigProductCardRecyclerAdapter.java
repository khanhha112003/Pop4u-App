package com.group2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.Artist;
import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BigProductCardRecyclerAdapter extends RecyclerView.Adapter<BigProductCardRecyclerAdapter.ViewHolder> {
    Activity activity;
    List<Product> productList;

    OnClickListener onClickListener;

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
        Product product = productList.get(position);
        if (product.getProductSalePercent() != 0) {
            holder.txtSalePercent.setText(String.format("%s%%", product.getProductSalePercent()));
        } else {
            holder.crdSalePercent.setVisibility(View.INVISIBLE); // or View.INVISIBLE or View.GONE based on your requirement
        }
        Picasso.get()
                .load(product.getBannerPhoto())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .fit()
                .into(holder.imvLargeProductImage);
        holder.txtProductLabel.setText(product.getProductLabel());
        holder.txtRating.setText(String.valueOf(product.getProductRating()));
        holder.txtProductArtist.setText(product.getProductArtistName());
        holder.txtProductPrice.setText(String.format("%s₫", String.valueOf(product.getProductPrice())));
        holder.txtProductName.setText(product.getProductName());

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
        TextView txtProductName, txtProductArtist, txtProductPrice, txtProductLabel, txtRating, txtSalePercent;
        ImageView imvLargeProductImage;

        CardView crdSalePercent;

        public ViewHolder(View view) {
            super(view);

            txtProductArtist = (TextView) view.findViewById(R.id.txtProductArtist);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
            imvLargeProductImage = (ImageView) view.findViewById(R.id.imvLargeProductImage);
            txtProductLabel = view.findViewById(R.id.txtProductLabel);
            txtRating = view.findViewById(R.id.txtRating);
            txtSalePercent = view.findViewById(R.id.txtSalePercent);
            crdSalePercent = view.findViewById(R.id.crdSalePercent);
        }
    }
}
