package com.group2.pop4u_app.ItemOffsetDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;
    private RecyclerView parent;

    public ItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildLayoutPosition(view);
        boolean isLeft = (index % 2 == 0);
        outRect.set(
                isLeft ? mItemOffset : mItemOffset / 2,
                0,
                isLeft ? mItemOffset / 2 : mItemOffset,
                mItemOffset
        );
    }
}
