package com.group2.pop4u_app.VoucherScreen.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.pop4u_app.R;
import com.group2.pop4u_app.VoucherScreen.model.ItemVoucher;

import java.util.List;

public class VoucherAdapter extends BaseAdapter {
    Activity context;
    int item_list;
    List<ItemVoucher> voucherList;

    //Constructor


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
        if(view ==null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_list, null);


            holder.voucher_id = view.findViewById(R.id.voucher_id);
            holder.voucher_description = view.findViewById(R.id.voucher_description);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)  view.getTag();

        }
        //Lien ket du lieu
        ItemVoucher b = voucherList.get(i);
        holder.voucher_id.setText(b.getVoucher_id());
        holder.voucher_description.setText(b.getVoucher_description());

        return view;
    }
    public static class  ViewHolder{
        TextView voucher_id, voucher_description;
    }
}


