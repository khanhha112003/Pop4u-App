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

import java.util.List;

public class MiniProductCardRecyclerAdapter extends RecyclerView.Adapter<MiniProductCardRecyclerAdapter.ViewHolder>{
    Activity activity;
    List<Product> productList;

    OnClickListener onClickListener;

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
            holder.txtSalePercent.setText("-" + String.valueOf(product.getProductSalePercent()) + "%");
        } else {
            holder.crdSalePercent.setVisibility(View.INVISIBLE); // or View.INVISIBLE or View.GONE based on your requirement
        }

        holder.imvProductImage.setImageResource(product.getProductImage1());
        holder.txtProductPrice.setText(String.valueOf(product.getProductPrice()) + "₫");
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

        CardView crdSalePercent;

        public ViewHolder(View view) {
            super(view);

            txtProductArtist = (TextView) view.findViewById(R.id.txtProductArtist);
            txtSalePercent = (TextView) view.findViewById(R.id.txtSalePercent);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
            imvProductImage = (ImageView) view.findViewById(R.id.imvProductImage);
            crdSalePercent = (CardView) view.findViewById(R.id.crdSalePercent);

        }
    }


}
