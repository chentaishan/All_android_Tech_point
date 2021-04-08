package com.example.thread_producer_consumer;

public class WaitPerson implements Runnable {
    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {

        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            synchronized (this) {
                while (restaurant.meal == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        System.out.println("waitperson interrupted!!!");
                    }
                }
                System.out.println("waitperson got " + restaurant.meal);
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        }
    }
}
