package com.example.dagger2_test;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {


    MainActivity activity;
    User user;


    public AppModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public MainActivity getActivity(){

        return activity;
    }

    @Provides
    public User getUser(){

        return new User("li----dan");
    }

    @Provides
    public DaggerPresenter daggerPresenter(MainActivity activity,User user){

        return new DaggerPresenter(activity,user);
    }
}
