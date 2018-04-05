package com.infinitesheep.gridgenerator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by Sofie on 2018-04-05.
 */

public class SquareLinearLayout extends LinearLayout {

    public SquareLinearLayout(Context context) {
        super(context);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > height) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        }
        else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
        //int size = width > height ? height : width;
        //setMeasuredDimension(size, size);
    }
}
