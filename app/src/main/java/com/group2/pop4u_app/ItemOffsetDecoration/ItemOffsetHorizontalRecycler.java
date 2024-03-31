package com.group2.pop4u_app.ItemOffsetDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetHorizontalRecycler extends RecyclerView.ItemDecoration {
    private int mItemOffset;
    private RecyclerView parent;

    public ItemOffsetHorizontalRecycler(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ItemOffsetHorizontalRecycler(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildLayoutPosition(view);
        int lastIndex = parent.getAdapter().getItemCount() - 1;
        boolean isLast = (lastIndex == index);
        outRect.set(
                mItemOffset,
                0,
                isLast ? mItemOffset : 0,
                mItemOffset
        );
    }


}
