package com.group2.pop4u_app.SplashScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.group2.pop4u_app.R;

public class BoardPagerAdapter extends PagerAdapter {

    Context context;

    int[] BoardImages = {
            R.drawable.onboarding_1,
            R.drawable.onboarding_2,
            R.drawable.onboarding_3,
            R.drawable.onboarding_4,
    };

    int[] BoardHeadings = {
            R.string.BoardTitle1,
            R.string.BoardTitle2,
            R.string.BoardTitle3,
            R.string.BoardTitle4,
    };

    int[] Descriptions = {
            R.string.BoardDes1,
            R.string.BoardDes2,
            R.string.BoardDes3,
            R.string.BoardDes4,
    };

    public BoardPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return BoardHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.boarding_layout, container, false);

        ImageView sliceTitleImage = view.findViewById(R.id.onboardimg1);
        TextView sliceHeading = view.findViewById(R.id.BoardSrcTitle);
        TextView sliceDescription = view.findViewById(R.id.BoardSrcDes);

        sliceTitleImage.setImageResource(BoardImages[position]);
        sliceHeading.setText(context.getString(BoardHeadings[position]));
        sliceDescription.setText(context.getString(Descriptions[position]));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
