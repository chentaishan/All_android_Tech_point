package com.example.handlerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: " + msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Message obtain = Message.obtain();
        obtain.obj = "hello";
        obtain.what = 1;

        handler.sendMessage(obtain);
        for (int i = 0; i < 10; i++) {
            obtain = Message.obtain();
            obtain.obj = "hello";
            obtain.what = 1;

        }
        handler.sendMessageDelayed(obtain, 100);
    }
}