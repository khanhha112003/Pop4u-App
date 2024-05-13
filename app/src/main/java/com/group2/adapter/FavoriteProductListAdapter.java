package com.group2.adapter;

import android.app.Activity;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class FavoriteProductListAdapter extends RecyclerView.Adapter<FavoriteProductListAdapter.ViewHolder>{
    Activity activity;
    List<Product> productList;
    OnClickListener onClickListener;

    static OnProductFavoriteListener onProductFavoriteListener;

    public FavoriteProductListAdapter(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_product_layout, parent, false);
        return new FavoriteProductListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product.getProductSalePercent() != 0) {
            holder.txtSalePercent.setText(String.format("%s%%", product.getProductSalePercent()));
        } else {
            holder.crdSalePercent.setVisibility(View.INVISIBLE);
        }
        String image = product.getListProductPhoto().get(0);
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .fit().centerCrop()
                .into(holder.imvProductImage);
        holder.txtProductArtist.setText(product.getProductArtistName());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedPrice = numberFormat.format(product.getProductPrice());

        holder.txtProductPrice.setText(String.format("%s₫", formattedPrice));
        holder.txtProductName.setText(product.getProductName());
        holder.btnAddToFavorite.setSelected(true);

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
        ImageButton btnAddToFavorite;

        public ViewHolder(View view) {
            super(view);
            txtProductArtist = (TextView) view.findViewById(R.id.txtProductArtist);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) view.findViewById(R.id.txtProductPrice);
            imvProductImage = (ImageView) view.findViewById(R.id.imvProductImage);
            txtSalePercent = (TextView) view.findViewById(R.id.txtSalePercent);
            crdSalePercent = (CardView) view.findViewById(R.id.crdSalePercent);
            btnAddToFavorite = (ImageButton) view.findViewById(R.id.btnAddToFavProduct);

            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    btnAddToFavorite.setSelected(true);
                    if (position != RecyclerView.NO_POSITION && onProductFavoriteListener != null) {
                        onProductFavoriteListener.onFavoriteClick(position);
                    }
                }
            });
        }
    }
    public interface OnProductFavoriteListener {
        void onFavoriteClick(int position);
    }

    public void setOnProductFavoriteListener(OnProductFavoriteListener listener) {
        this.onProductFavoriteListener = listener;
    }

}
