package com.example.aroute_test;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;


@Route(path = "/test/activity2")
public class MainActivity2 extends AppCompatActivity {

    @Autowired
    String xx;

    @Autowired
    User user;
    private TextView mTextview;

    private static final String TAG = "MainActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_main2);

        User user = (User) getIntent().getSerializableExtra("user");

        Log.d(TAG, "onCreate: "+user.toString());


        initViews();
    }

    private void initViews() {
        mTextview = findViewById(R.id.textView);

        mTextview.setText(xx+"---user="+user.name);
    }
}