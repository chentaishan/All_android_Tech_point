package com.example.dagger2_test;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    //创建当前对象的时候，也会创建该实例对象，不能私有

    @Inject
    DaggerPresenter daggerPresenter;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //模块对象
        AppModule appModule = new AppModule(this);
        //注入dagger的依赖关系
        //DaggerAppComponent 是按照规则生成的名字
        DaggerAppComponent.builder().appModule(appModule)
                .build()
                .inject(this);


        initViews();

        //调用presenter的方法
        daggerPresenter.showName();

    }

    public void showName(String name) {

        mText.setText(name);
    }

    private void initViews() {
        mText = findViewById(R.id.text);
    }
}