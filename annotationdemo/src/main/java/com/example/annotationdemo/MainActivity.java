package com.example.annotationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.annotaionprocessor.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: "+title);

    }

    private static final String TAG = "MainActivity";
}