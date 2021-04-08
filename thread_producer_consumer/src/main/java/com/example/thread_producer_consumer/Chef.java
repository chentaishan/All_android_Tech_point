package com.example.thread_producer_consumer;

import java.util.concurrent.TimeUnit;

public class Chef implements Runnable {
    private Restaurant restaurant;

    int count = 0;

    public Chef(Restaurant restaurant) {

        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            synchronized (this) {
                try {
                    while (restaurant.meal != null) {

                        wait();

                    }

                    if (++count == 10) {
                        System.out.println("out of food closing!");
                        restaurant.exec.shutdown();
                        return;
                    }

                    System.out.println("order up!");

                    synchronized (restaurant.waitPerson) {
                        restaurant.meal = new Meal(count);
                        restaurant.waitPerson.notifyAll();
                    }

                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                    System.out.println("waitperson interrupted!!!");
                }
            }
        }
    }
}
