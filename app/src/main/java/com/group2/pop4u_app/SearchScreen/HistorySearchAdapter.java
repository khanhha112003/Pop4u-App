package com.group2.pop4u_app.SearchScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group2.pop4u_app.R;

import java.util.List;

public class HistorySearchAdapter extends BaseAdapter {

    Context context;
    List<String> listHistorySearch;

    HistorySearchAdapter(Context context, List<String> listHistorySearch){
        this.context = context;
        this.listHistorySearch = listHistorySearch;
    }

    @Override
    public int getCount() {
        return listHistorySearch.size();
    }

    @Override
    public Object getItem(int i) {
        return listHistorySearch.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView historySearchContext;

        ImageButton copyHistorySearch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_search_item, parent, false);
            holder = new ViewHolder();
            holder.historySearchContext = convertView.findViewById(R.id.txt_history_search_content);
            holder.copyHistorySearch = convertView.findViewById(R.id.btn_copy_history_search);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.historySearchContext.setText(listHistorySearch.get(position));

        return convertView;
    }
}
