package com.group2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.group2.pop4u_app.R;

public class BannerAdapter extends PagerAdapter {

    Context context;
    int[] BannerImg = {
            R.drawable.img,
            R.drawable.img_1,
            R.drawable.img_2,
    };


    public BannerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return BannerImg.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.banner_layout, container, false);

        ImageView sliceTitleImage = view.findViewById(R.id.imgBanner);

        sliceTitleImage.setImageResource(BannerImg[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
