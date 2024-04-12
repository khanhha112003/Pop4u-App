package com.group2.pop4u_app.SearchScreen;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.group2.adapter.BigProductCardRecyclerAdapter;
import com.group2.model.Product;
import com.group2.pop4u_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductGridviewAdapter extends BaseAdapter {
    Context context;

    ArrayList<Product> products;

    public ProductGridviewAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView txtProductName, txtProductArtist, txtProductPrice, txtLabel, txtRating, txtSalePercent;
        ImageView imvMediumProductImage;

        CardView crdSalePercent;

        public ViewHolder(View view) {
            txtProductArtist = (TextView) view.findViewById(R.id.txtProductArtist);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductPrice = (TextView) view.findViewById(R.id.txtMediumCardProductPrice);
            imvMediumProductImage = (ImageView) view.findViewById(R.id.imvMediumProductImage);
            txtLabel = view.findViewById(R.id.txtLabel);
            txtRating = view.findViewById(R.id.txtRating);
            txtSalePercent = view.findViewById(R.id.txtSalePercent);
            crdSalePercent = view.findViewById(R.id.crdSalePercent);
        }

        public void setCardContent(Product product){
            txtProductArtist.setText(product.getProductArtistName());
            txtProductName.setText(product.getProductName());
            txtProductPrice.setText(String.format("%sđ", product.getProductPrice()));
            txtRating.setText(String.format("-%s", String.valueOf(product.getProductRating() + "%")));
            txtLabel.setText(product.getProductLabel());
            Picasso.get()
                    .load(product.getBannerPhoto())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .fit().centerInside()
                    .into(imvMediumProductImage);
            int isDisplaySaleBadge = product.getProductSalePercent() == 0 ? View.INVISIBLE: View.VISIBLE;
            crdSalePercent.setVisibility(isDisplaySaleBadge);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductGridviewAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_medium_card, parent, false);
            holder = new ProductGridviewAdapter.ViewHolder(convertView);
            //holder.historyPearchContext = convertView.findViewById(R.id.txt_history_search_content);

            convertView.setTag(holder);
        } else {
            holder = (ProductGridviewAdapter.ViewHolder) convertView.getTag();
        }
        //holder.historySearchContext.setText(listHistorySearch.get(position));
        holder.setCardContent(products.get(position));
        return convertView;
    }
}
