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
import com.group2.pop4u_app.R;

import java.util.List;

public class SettingListAdapter extends RecyclerView.Adapter<SettingListAdapter.ViewHolder> {
    Activity activity;
    List<SettingItem> settingItemList;

    private OnClickListener onClickListener;


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
        SettingItem settingItem = settingItemList.get(position);
        holder.imvSettingIcon.setImageResource(settingItem.getSettingIcon());
        holder.txtSettingTitle.setText(settingItem.getSettingTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, settingItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return settingItemList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, SettingItem settingItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtSettingTitle;
        ImageView imvSettingIcon;

        public ViewHolder(View view) {
            super(view);

            txtSettingTitle = (TextView) view.findViewById(R.id.txtSettingTitle);
            imvSettingIcon = (ImageView) view.findViewById(R.id.imvSettingIcon);
        }
    }
}
