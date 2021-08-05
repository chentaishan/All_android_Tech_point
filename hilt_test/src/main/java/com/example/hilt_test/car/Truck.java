package com.example.hilt_test.car;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class Truck {
    private Context context;

    @Inject
    public Truck(@ActivityContext Context context) {

        this.context = context;
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
