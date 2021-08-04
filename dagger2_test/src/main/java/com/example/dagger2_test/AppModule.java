package com.example.dagger2_test;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * 模块为了维护各个对象  activity user  Presenter对象
 *  1、如何创建？
 *  2、对象之间的关系？
 *
 *  3、注意Provides注解，是提供对象实例的创建方法
 */
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
