package com.example.aroute_test;


import java.io.Serializable;


public class User implements  Serializable {
    String name;


    public User(String lili) {

        this.name = lili;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
