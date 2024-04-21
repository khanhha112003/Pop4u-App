package com.group2.pop4u_app.SearchScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.model.SearchItem;
import com.group2.pop4u_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class HistorySearchAdapter extends BaseAdapter {

    Context context;

    List<SearchItem> listSearchResultItem;

    HistorySearchAdapter(Context context, List<SearchItem> listSearchResultItem){
        this.context = context;
        this.listSearchResultItem = listSearchResultItem;
    }

    @Override
    public int getCount() {
        return listSearchResultItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listSearchResultItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class HistoryViewHolder {
        TextView historySearchContext;

        ImageButton copyHistorySearch;
    }

    private static class ArtistViewHolder {
        TextView artistName;
        ImageView artistAvatar;
    }

    private static class ProductViewHolder {
        TextView productName;
        ImageView productImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryViewHolder historyItem;
        ArtistViewHolder artistItem;
        ProductViewHolder productItem;
        SearchItem searchItem = listSearchResultItem.get(position);
        if (Objects.equals(searchItem.getItemType(), SearchItem.HISTORY_TYPE)) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.search_screen_history_search_item, parent, false);
                historyItem = new HistoryViewHolder();
                historyItem.historySearchContext = convertView.findViewById(R.id.txt_history_search_content);
                historyItem.copyHistorySearch = convertView.findViewById(R.id.btn_copy_history_search);
                convertView.setTag(historyItem);
            } else {
                historyItem = (HistoryViewHolder) convertView.getTag();
            }
            historyItem.historySearchContext.setText(searchItem.getItemContext());
        } else if (searchItem.getItemType() == SearchItem.ARTIST_TYPE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.search_screen_artist_item, parent, false);
                artistItem = new ArtistViewHolder();
                artistItem.artistName = convertView.findViewById(R.id.txt_search_item_artist_name);
                artistItem.artistAvatar = convertView.findViewById(R.id.img_search_item_artist_avatar);
                convertView.setTag(artistItem);
            } else {
                artistItem = (ArtistViewHolder) convertView.getTag();
            }
            artistItem.artistName.setText(searchItem.getItemContext());
            Picasso
                    .get()
                    .load(searchItem.getItemImage())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(artistItem.artistAvatar);
        } else if (Objects.equals(searchItem.getItemType(), SearchItem.PRODUCT_TYPE)) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.search_screen_product_item, parent, false);
                productItem = new ProductViewHolder();
                productItem.productName = convertView.findViewById(R.id.txt_search_item_product_name);
                productItem.productImage = convertView.findViewById(R.id.img_search_item_product_image);
                convertView.setTag(productItem);
            } else {
                productItem = (ProductViewHolder) convertView.getTag();
            }
            productItem.productName.setText(searchItem.getItemContext());
            Picasso
                    .get()
                    .load(searchItem.getItemImage())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(productItem.productImage);
        }
        return convertView;
    }
}
