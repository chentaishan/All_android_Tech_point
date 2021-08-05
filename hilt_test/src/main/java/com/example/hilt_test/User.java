package com.example.hilt_test;

import javax.inject.Inject;

public class User {
    String name;

    Address address;

    @Inject
    public User( Address address) {

        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
