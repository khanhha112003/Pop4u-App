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

import java.util.ArrayList;
import java.util.Objects;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class HistorySearchAdapter extends BaseAdapter {

    Context context;

    ArrayList<SearchItem> listSearchResultItem;

    HistorySearchAdapter(Context context, ArrayList<SearchItem> listSearchResultItem){
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

    private static class SearchViewHolder {
        TextView itemSearchText;

        ImageButton copySearchText;

        ImageView itemSearchImage;

        ImageView arrowImage;

        ImageView historySearchIcon;

        TextView searchItemSubtitle;
        TextView historySearchText;

        public SearchViewHolder(View view) {
            itemSearchText = view.findViewById(R.id.txt_search_item_text);
            itemSearchImage = view.findViewById(R.id.img_search_item_avatar);
            arrowImage = view.findViewById(R.id.img_search_item_arrow);
            searchItemSubtitle = view.findViewById(R.id.txt_search_item_subtitle);

            historySearchIcon = view.findViewById(R.id.imv_history_search_icon);
            historySearchText = view.findViewById(R.id.txt_history_search_text);
            copySearchText = view.findViewById(R.id.btn_copy_history_search);
        }

        public void bind(SearchItem searchItem) {

            if (!Objects.equals(searchItem.getItemType(), SearchItem.HISTORY_TYPE)) {
                itemSearchText.setText(searchItem.getItemContext());
                itemSearchImage.setVisibility(View.VISIBLE);
                arrowImage.setVisibility(View.VISIBLE);
                searchItemSubtitle.setVisibility(View.VISIBLE);

                copySearchText.setVisibility(View.GONE);
                historySearchIcon.setVisibility(View.GONE);
                historySearchText.setVisibility(View.GONE);
                if (Objects.equals(searchItem.getItemType(), SearchItem.ARTIST_TYPE)) {
                    searchItemSubtitle.setText("Nghệ sĩ");
                    Picasso
                            .get()
                            .load(searchItem.getItemImage())
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .fit().centerCrop()
                            .transform(new CropCircleTransformation())
                            .into(itemSearchImage);
                } else if (Objects.equals(searchItem.getItemType(), SearchItem.PRODUCT_TYPE)) {
                    searchItemSubtitle.setText("Sản phẩm");
                    Picasso
                            .get()
                            .load(searchItem.getItemImage())
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .fit()
                            .into(itemSearchImage);
                }
            } else {
                historySearchText.setText(searchItem.getItemContext());
                copySearchText.setVisibility(View.VISIBLE);
                historySearchIcon.setVisibility(View.VISIBLE);
                historySearchText.setVisibility(View.VISIBLE);

                itemSearchText.setVisibility(View.GONE);
                itemSearchImage.setVisibility(View.GONE);
                arrowImage.setVisibility(View.GONE);
                searchItemSubtitle.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchViewHolder searchViewItem;
        SearchItem searchItem = listSearchResultItem.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_screen_search_item, parent, false);
            searchViewItem = new SearchViewHolder(convertView);
            convertView.setTag(searchViewItem);
        } else {
            searchViewItem = (SearchViewHolder) convertView.getTag();
        }
        searchViewItem.bind(searchItem);
        return convertView;
    }
}
