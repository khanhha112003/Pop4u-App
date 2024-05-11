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

import androidx.recyclerview.widget.RecyclerView;

import com.group2.model.Address;
import com.group2.model.CartItem;
import com.group2.pop4u_app.R;


import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Activity context;
    int item_list;
    List<Address> addressList;
    int selectedPosition = -1;

    public AddressAdapter.OnTapSelectAddressListener onTapSelectAddressListener;

    public AddressAdapter(Activity context, int item_list, List<Address> addressList) {
        this.context = context;
        this.item_list = item_list;
        this.addressList = addressList;
        for (Address address : addressList) {
            if (address.isDefault()) {
                selectedPosition = addressList.indexOf(address);
            }
        }
    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(item_list, parent, false);
        return new AddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, int position) {
        Address b = addressList.get(position);
        holder.cus_name.setText(b.getCus_name());
        holder.cus_phone.setText(b.getCus_phone());
        holder.cus_address.setText(b.getCus_address());
        holder.radioButton.setChecked(b.isDefault());
        holder.radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                for (Address address : addressList) {
                    if (address.isDefault()) {
                        notifyItemChanged(addressList.indexOf(address));
                    }
                    address.setDefault(false);
                }
                b.setDefault(true);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cus_name, cus_phone, cus_address;
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            cus_name = itemView.findViewById(R.id.cus_name);
            cus_phone = itemView.findViewById(R.id.cus_phone);
            cus_address = itemView.findViewById(R.id.cus_address);
            radioButton = itemView.findViewById(R.id.rbAddressCheckbox);
        }
    }

    public interface OnTapSelectAddressListener {
        void onTapCheckAddress(Address address);
    }

}