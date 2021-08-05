package com.example.hilt_test.car;

import android.util.Log;

import javax.inject.Inject;

public class Truck {
    @Inject
    public Truck() {

    }


    @BindElectricEngine
    @Inject
    Engine engine1;

    @Inject
    @BindGasEngine
    Engine engine2;


    public void deliver() {
        engine1.start();
        engine2.start();
        Log.d(TAG, "deliver: ---------------");
        engine1.shutdown();
        engine2.shutdown();
    }

    private static final String TAG = "Truck";
}
