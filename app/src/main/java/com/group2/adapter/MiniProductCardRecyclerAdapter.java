package com.group2.adapter;

import android.app.Activity;
import android.icu.text.NumberFormat;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MiniProductCardRecyclerAdapter extends RecyclerView.Adapter<MiniProductCardRecyclerAdapter.ViewHolder>{
    Activity activity;
    List<Product> productList;

    OnClickListener onClickListener;
    NumberFormat numberFormat;

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
        Product product = productList.get(position);
        if (product.getProductSalePercent() != 0) {
            holder.txtSalePercent.setText(String.format("%s%%", String.valueOf(product.getProductSalePercent())));
        } else {
            holder.crdSalePercent.setVisibility(View.INVISIBLE); // or View.INVISIBLE or View.GONE based on your requirement
        }
        Picasso.get()
                .load(product.getBannerPhoto())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .fit().centerCrop()
                .into(holder.imvProductImage);
        numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        holder.txtProductPrice.setText(String.format("%s₫", numberFormat.format(product.getProductPrice())));
        holder.txtProductName.setText(product.getProductName());
        holder.txtProductArtist.setText(product.getProductArtistName());

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
        TextView txtProductName, txtProductArtist, txtProductPrice, txtSalePercent;
        ImageView imvProductImage;

        CardView crdSalePercent, crdProductMiniCard;

        public ViewHolder(View view) {
            super(view);

            txtProductArtist = (TextView) view.findViewById(R.id.txtProductArtist);
            txtSalePercent = (TextView) view.findViewById(R.id.txtSalePercent);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
            imvProductImage = (ImageView) view.findViewById(R.id.imvProductImage);
            crdSalePercent = (CardView) view.findViewById(R.id.crdSalePercent);
            crdProductMiniCard = (CardView) view.findViewById(R.id.crdProductMiniCard);

        }
    }


}
