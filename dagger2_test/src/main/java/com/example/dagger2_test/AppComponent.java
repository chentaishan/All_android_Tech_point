package com.example.dagger2_test;

import dagger.Component;

/**
 * 桥梁作用，是界面组件和映射关系的桥梁
 * modules  配置AppModule.class 说明需要哪些对象的创建。
 */
@Component(modules = AppModule.class)
public  interface AppComponent {

    void inject(MainActivity activity);
}
