package com.example.hilt_test.car;

import android.util.Log;

import javax.inject.Inject;

public class GasEngine implements Engine{

    @Inject
    public GasEngine() {
    }

    @Override
    public void start() {
        Log.d(TAG, "GasEngine  start: ");
    }

    @Override
    public void shutdown() {

        Log.d(TAG, "GasEngine  shutdown: ");
    }

    private static final String TAG = "GasEngine";
}
