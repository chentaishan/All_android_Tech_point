package com.example.dagger2_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    //创建当前对象的时候，也会创建该实例对象，不能私有

    @Inject
    DaggerPresenter daggerPresenter;
    private TextView mText;
    private Button mClick1;

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


    }

    public void showName(String name) {

        mText.setText(name + "user==" + daggerPresenter.user);
    }

    private void initViews() {
        mText = findViewById(R.id.text);
        mClick1 = findViewById(R.id.click1);
        mClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用presenter的方法
                daggerPresenter.showName();

            }
        });
    }
}