package com.example.dagger2_test;

import dagger.Component;

@Component(modules = AppModule.class)
public  interface AppComponent {

    void inject(MainActivity activity);
}
