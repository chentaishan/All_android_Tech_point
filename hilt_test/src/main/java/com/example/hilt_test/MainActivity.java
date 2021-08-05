package com.example.hilt_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hilt_test.car.Truck;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    User user;

    @Inject
    Truck truck;

    private Button mClick1;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mClick1 = findViewById(R.id.click1);
        mResult = findViewById(R.id.result);
        mClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResult.setText(user.toString());

                truck.deliver();
//                mResult.setText("truck="+);
            }
        });
    }
}