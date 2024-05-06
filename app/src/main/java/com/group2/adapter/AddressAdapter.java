package com.group2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.group2.model.Address;
import com.group2.model.CartItem;
import com.group2.pop4u_app.R;


import java.util.List;

public class AddressAdapter extends BaseAdapter {
    Activity context;
    int item_list;
    List<Address> addressList;

    int selectedPosition = 0;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public AddressAdapter.OnTapSelectAddressListener onTapSelectAddressListener;


    public AddressAdapter(Activity context, int item_list, List<Address> addressList) {
        this.context = context;
        this.item_list = item_list;
        this.addressList = addressList;
    }


    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int i) {
        return addressList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AddressAdapter.ViewHolder holder;
        if(view ==null) {
            holder = new AddressAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_list, null);


            holder.cus_name = view.findViewById(R.id.cus_name);
            holder.cus_phone = view.findViewById(R.id.cus_phone);
            holder.cus_address = view.findViewById(R.id.cus_address);
            holder.radioButton = view.findViewById(R.id.rbAddressCheckbox);
            view.setTag(holder);
        } else {
            holder = (AddressAdapter.ViewHolder)  view.getTag();

        }
        //Lien ket du lieu
        Address b = addressList.get(i);
        holder.cus_name.setText(b.getCus_name());
        holder.cus_phone.setText(b.getCus_phone());
        holder.cus_address.setText(b.getCus_address());
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onTapSelectAddressListener.onTapCheckAddress(i, b);
                setSelectedPosition(i);
            }
        });

        if (selectedPosition == i) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }

        return view;
    }
    public static class  ViewHolder{
        TextView cus_name, cus_phone, cus_address;

        RadioButton radioButton;
    }

    public interface OnTapSelectAddressListener {
        void onTapCheckAddress(int position, Address address);
    }
}



