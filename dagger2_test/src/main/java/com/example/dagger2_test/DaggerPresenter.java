package com.example.dagger2_test;

import javax.inject.Inject;

import dagger.Module;


public class DaggerPresenter {

    MainActivity activity;

    User user;

    public DaggerPresenter(MainActivity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    public void showName(){

        activity.showName(user.getName());
    }
}
