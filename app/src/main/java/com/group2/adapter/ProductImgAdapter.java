package com.group2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.group2.pop4u_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductImgAdapter extends PagerAdapter {
    private Context mContext;

    private List<String> imagesUrl;

    public ProductImgAdapter(Context context) {
        mContext = context;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    @Override
    public int getCount() {
        if (imagesUrl == null) {
            return 1;
        } else {
            return imagesUrl.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (imagesUrl != null) {
            Picasso.get()
                    .load(imagesUrl.get(position))
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .fit().centerInside()
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.placeholder_image);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
