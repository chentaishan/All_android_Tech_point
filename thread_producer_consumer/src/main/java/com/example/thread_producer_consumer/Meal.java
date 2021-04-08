package com.example.thread_producer_consumer;

public class Meal {
    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal{ "+orderNum+" + orderNum }";
    }
}
