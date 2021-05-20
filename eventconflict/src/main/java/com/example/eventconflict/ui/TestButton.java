package com.example.eventconflict.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

public class TestButton extends TextView {
    private static final String TAG = "TestButton";
    private int mScaledTouchSlop;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public TestButton(Context context) {
        this(context, null);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext())
                .getScaledTouchSlop();
        Log.d(TAG, "sts:" + mScaledTouchSlop);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取 屏幕里坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        Log.d(TAG, "onTouchEvent: x="+x+"  y="+y);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            //记录这次和上次滑动的偏移量
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            Log.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
            //获取当前的位置+ 偏移量 = 要位移的坐标
            int translationX = (int)ViewHelper.getTranslationX(this) + deltaX;
            int translationY = (int)ViewHelper.getTranslationY(this) + deltaY;
            if (translationX<0)
                translationX=0;
            if (translationY<0)
                translationY=0;
            ViewHelper.setTranslationX(this, translationX);
            ViewHelper.setTranslationY(this, translationY);
            break;
        }
        case MotionEvent.ACTION_UP: {
            break;
        }
        default:
            break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

}
