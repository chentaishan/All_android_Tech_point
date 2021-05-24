package com.example.all_tech_point;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {


    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetector = new GestureDetector(this);
        //解决长按屏幕后无法拖动的现象 mGestureDetector.setIsLongpressEnabled(false);
        mGestureDetector.setIsLongpressEnabled(false);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consume = mGestureDetector.onTouchEvent(event);

        Log.d(TAG, "onTouchEvent: ");
        return consume;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown: ");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

        Log.d(TAG, "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp: ");
        return false;
    }

    /**
     * 手指按下屏幕并滑动
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll: ");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress: ");

    }

    private static final String TAG = "MainActivity";

    /**
     * 快速滑动，down --move  --up
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: ");
        return false;
    }
}
