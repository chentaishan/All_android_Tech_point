package com.example.aroute_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity {

    private Button mClick1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mClick1 = findViewById(R.id.click1);
//        mClick1.setOnClickListener {
//            view ->
//                    // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
//                    ARouter.getInstance().build("/test/activity2").navigation();
//
//        };

        mClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/test/activity2")

                        .withString("xx","xx")
                        .navigation();
            }
        });

    }
}