package com.example.thread_producer_consumer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                // TODO 21/03/28
                testThread();
                break;
            default:
                break;
        }
    }

    private void testThread() {
        Msg msg = new Msg();

        for (int i = 0; i < 100; i++) {
            new Thread(new Producer(msg,i)).start();
            new Thread(new Consumer(msg,i)).start();
        }

    }
}
