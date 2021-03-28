package com.example.thread_producer_consumer;

import android.util.Log;

public class Producer extends Thread {
    Msg msg;
    int i;

    public Producer(Msg msg,int i) {
        this.msg = msg;
        this.i = i;
    }

    private static final String TAG = "Producer";
    @Override
    public  void run() {
        super.run();


        Log.d(TAG, "run: 生产=="+i);
                this.msg.setContent("pro---title-"+i,"pro---content-"+i);





    }
}
