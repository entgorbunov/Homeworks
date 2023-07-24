package com.sasha.hometasks.multithreading;

import java.util.concurrent.Semaphore;

public class Foo1 {
    private final Semaphore semaphore1;
    private final Semaphore semaphore2;

    public Foo1() {
        semaphore1 = new Semaphore(0);
        semaphore2 = new Semaphore(0);
    }

    public void first(Runnable r) {
        r.run();
        semaphore1.release();
    }

    public void second(Runnable r) {
        try {
            semaphore1.acquire();
            r.run();
            semaphore2.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void third(Runnable r) {
        try {
            semaphore2.acquire();
            r.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Foo1 foo1 = new Foo1();

        Runnable printFirst = () -> System.out.print("first");
        Runnable printSecond = () -> System.out.print("second");
        Runnable printThird = () -> System.out.print("third");

        new Thread(() -> foo1.first(printFirst)).start();
        new Thread(() -> foo1.second(printSecond)).start();
        new Thread(() -> foo1.third(printThird)).start();
    }
}
