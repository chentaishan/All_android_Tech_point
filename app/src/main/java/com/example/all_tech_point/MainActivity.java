package com.example.all_tech_point;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//
//            Log.d(TAG, "handleMessage: ");
//        }
//    };
    private TextView mMyview;
    private MyLinear mLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Message obtain = Message.obtain();
//        obtain.obj = "hello";
//        obtain.what = 1;
//        handler.sendMessage(obtain);
    }

    private void initView() {
//        mMyview =   findViewById(R.id.myview);
        mLinear = (MyLinear) findViewById(R.id.linear);



    }

    @Override
    protected void onResume() {
        super.onResume();

        mMyview.post(new Runnable() {
            @Override
            public void run() {
                int width = mMyview.getMeasuredWidth();
                int height = mMyview.getMeasuredHeight();
                Log.d(TAG, "1mMyview.post: "+width);
                Log.d(TAG, "1mMyview.post: "+height);


            }
        });

        mMyview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {


            @Override
            public boolean onPreDraw() {

                mMyview.getViewTreeObserver().removeOnPreDrawListener(this);
                int width = mMyview.getMeasuredWidth();
                int height = mMyview.getMeasuredHeight();
                Log.d(TAG, "2mMyview.post: "+width);
                Log.d(TAG, "2mMyview.post: "+height);
                return false;
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus){
            int width = mMyview.getMeasuredWidth();
            int height = mMyview.getMeasuredHeight();
            Log.d(TAG, "mMyv3iew.post: "+width);
            Log.d(TAG, "mMyvie3w.post: "+height);
        }

    }
}
