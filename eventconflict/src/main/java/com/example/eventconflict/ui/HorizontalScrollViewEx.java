package com.example.eventconflict.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    // 分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int scaledTouchSlop;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();

        scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    int downScrollX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downScrollX = getScrollX();

                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }


        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE: {
                int scrollX = getScrollX();
                Log.d(TAG, "ACTION_MOVE: scrollX:" + scrollX);
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                if ((mChildIndex == 0 && deltaX > 0) || mChildIndex == 2 && deltaX < 0) {
                    mLastX = x;
                    mLastY = y;
                    return false;
                }
                Log.d(TAG, "ACTION_MOVE:22 (x - mLastX):" + -deltaX);
                scrollBy(-deltaX, 0);
                break;
            }
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();

                mVelocityTracker.computeCurrentVelocity(100);
                float xVelocity = mVelocityTracker.getXVelocity();
                Log.d(TAG, "ACTION_UP: 11 速度=" + xVelocity + "--scrollX - downScrollX=" + (scrollX - downScrollX));

                int distance = scrollX - downScrollX;
                if (Math.abs(distance) > scaledTouchSlop) {
                    //向右滑动
                    if (xVelocity >= 30) {
                        mChildIndex = mChildIndex > 0 ? mChildIndex-- : 0;

                        int dx = mChildIndex * mChildWidth - scrollX;

                        Log.d(TAG, "onTouchEvent: 1   单项宽度:"+dx +"  抬起时滑动距离:"+distance);
                        smoothScrollBy(scrollX,  dx);
                    } else if (xVelocity <= -30) { //向左滑动
                        mChildIndex++;
                        if (mChildIndex >= getChildCount()) {
                            mChildIndex = getChildCount() - 1;
                        }
                        int dx = mChildIndex * mChildWidth - scrollX;


                        Log.d(TAG, "onTouchEvent: 2 dx："+dx  +"  scrollX:"+scrollX);
                        smoothScrollBy(scrollX, dx );
                    }
                    mVelocityTracker.clear();
                }
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        Log.d(TAG, "ACTION:4 mLastX=" + mLastX);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measuredWidth, heightSpaceSize);
        } else {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;

                Log.d(TAG, "onLayout: l:" + childLeft + "  r:" + (childLeft + childWidth));
                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    private void smoothScrollBy(int startx, int dx) {
        mScroller.startScroll(startx, 0, dx, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll: " + mScroller.getCurrX());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
