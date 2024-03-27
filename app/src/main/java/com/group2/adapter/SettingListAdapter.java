package com.group2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.SettingItem;
import com.group2.pop4u_app.Activity.AccountScreen;
import com.group2.pop4u_app.R;

import java.util.ArrayList;
import java.util.List;

public class SettingListAdapter extends RecyclerView.Adapter<SettingListAdapter.ViewHolder> {
    Activity activity;
    List<SettingItem> settingItemList;

    public SettingListAdapter(Activity activity, List<SettingItem> settingItemList) {
        this.activity = activity;
        this.settingItemList = settingItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.setting_item_layout, parent, false);

        return new SettingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imvSettingIcon.setImageResource(settingItemList.get(position).getSettingIcon());
        holder.txtSettingTitle.setText(settingItemList.get(position).getSettingTitle());
    }

    @Override
    public int getItemCount() {
        return settingItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSettingTitle;
        ImageView imvSettingIcon;

        public ViewHolder(View view) {
            super(view);

            txtSettingTitle = (TextView) view.findViewById(R.id.txtSettingTitle);
            imvSettingIcon = (ImageView) view.findViewById(R.id.imvSettingIcon);
        }
    }
}
