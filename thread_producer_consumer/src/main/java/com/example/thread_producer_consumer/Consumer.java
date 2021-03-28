package com.example.thread_producer_consumer;

import android.util.Log;

public class Consumer extends Thread {
    Msg msg;
    private int i;

    private static final String TAG = "Consumer";

    public Consumer(Msg msg,int i) {
        this.msg = msg;
        this.i = i;
    }

    @Override
    public  void run() {
        super.run();



            Log.d(TAG, i+": "+this.msg.get());



    }
}
