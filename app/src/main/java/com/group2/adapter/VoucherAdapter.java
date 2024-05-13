package com.group2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.group2.model.ItemVoucher;
import com.group2.pop4u_app.R;

import java.util.List;

public class VoucherAdapter extends BaseAdapter {
    Activity context;
    int item_list;
    List<ItemVoucher> voucherList;


    public VoucherAdapter(Activity context, int item_list, List<ItemVoucher> voucherList) {
        this.context = context;
        this.item_list = item_list;
        this.voucherList = voucherList;
    }


    @Override
    public int getCount() {
        return voucherList.size();
    }

    @Override
    public Object getItem(int i) {
        return voucherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_list, null);

            holder.voucher_id = view.findViewById(R.id.voucher_id);
            holder.voucher_description = view.findViewById(R.id.voucher_description);
            holder.voucher_radio = view.findViewById(R.id.rdVoucher);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Populate data
        final ItemVoucher voucher = voucherList.get(i);
        holder.voucher_id.setText(voucher.getVoucher_id());
        holder.voucher_description.setText(voucher.getVoucher_description());
        holder.voucher_radio.setChecked(voucher.isSelected());

        // Toggle radio button
        holder.voucher_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;
                rb.setChecked(true);
                for (ItemVoucher item : voucherList) {
                    item.setSelected(false);
                }
                voucher.setSelected(rb.isChecked());
                notifyDataSetChanged();
            }
        });

        return view;
    }


    public static class  ViewHolder{
        TextView voucher_id, voucher_description;
        RadioButton voucher_radio;
    }
}


