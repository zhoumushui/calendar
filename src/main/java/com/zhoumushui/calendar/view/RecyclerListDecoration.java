package com.zhoumushui.calendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerListDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable drawableDivider;
    private int orientation;

    public static final int LIST_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int LIST_VERTICAL = LinearLayoutManager.VERTICAL;

    public RecyclerListDecoration(Context context, int orientation) {
        final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        drawableDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(orientation);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == LIST_VERTICAL)
            drawVertical(c, parent);
        else
            drawHorizontal(c, parent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (orientation == LIST_VERTICAL)
            outRect.set(0, 0, 0, drawableDivider.getIntrinsicHeight());
        else
            outRect.set(0, 0, drawableDivider.getIntrinsicWidth(), 0);
    }

    public void setOrientation(int orientation) {
        if (orientation != LIST_HORIZONTAL && orientation != LIST_VERTICAL)
            throw new IllegalArgumentException("Invalid orientation argument");
        this.orientation = orientation;
    }

    public void drawVertical(Canvas canvas, RecyclerView recyclerView) {
        final int left = recyclerView.getLeft();
        final int right = recyclerView.getWidth() - recyclerView.getPaddingRight();

        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)
                    child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + drawableDivider.getIntrinsicHeight();
            drawableDivider.setBounds(left, top, right, bottom);
            drawableDivider.draw(canvas);
        }
    }

    public void drawHorizontal(Canvas canvas, RecyclerView recyclerView) {
        final int top = recyclerView.getPaddingTop();
        final int bottom = recyclerView.getHeight() - recyclerView.getPaddingBottom();

        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)
                    child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + drawableDivider.getIntrinsicHeight();
            drawableDivider.setBounds(left, top, right, bottom);
            drawableDivider.draw(canvas);
        }
    }

}
