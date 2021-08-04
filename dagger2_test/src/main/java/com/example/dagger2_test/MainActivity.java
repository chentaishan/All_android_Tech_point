package com.example.dagger2_test;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    DaggerPresenter daggerPresenter;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DaggerAppComponent.builder().appModule(new AppModule(this)).build().inject(this);


        initViews();



        daggerPresenter.showName();

    }

    public void showName(String name) {

        mText.setText(name);
    }

    private void initViews() {
        mText = findViewById(R.id.text);
    }
}