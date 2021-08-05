package com.example.hilt_test.car;

import android.util.Log;

import javax.inject.Inject;


public class EleEngine implements Engine{
    @Inject
    public EleEngine() {
    }

    @Override
    public void start() {
        Log.d(TAG, "EleEngine  start: ");
    }

    @Override
    public void shutdown() {

        Log.d(TAG, "EleEngine  shutdown: ");
    }

    private static final String TAG = "EleEngine";
}
