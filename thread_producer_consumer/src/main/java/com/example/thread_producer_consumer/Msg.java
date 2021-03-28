package com.example.thread_producer_consumer;

import android.util.Log;

public class Msg {
    private String title;
    private String content;

    boolean isFlag = true;// true 可取



    public synchronized String get () {
        if (isFlag){

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isFlag = true;
        return title+"--->" +content;
    }

    public synchronized void setContent(String title,String content) {

        if (!isFlag){

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.content = content;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.title = title;
        isFlag = false;


    }

    private static final String TAG = "Msg";
}
