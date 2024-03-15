package com.sasha.hometasks.multithreading.semaphore;

import java.util.concurrent.Semaphore;

public class Foo {
    private final Semaphore semaphore1;
    private final Semaphore semaphore2;

    public Foo() {
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
        Foo foo = new Foo();

        new Thread(() -> foo.first(() -> System.out.print("first"))).start();
        new Thread(() -> foo.second(() -> System.out.print("second"))).start();
        new Thread(() -> foo.third(() -> System.out.print("third"))).start();
    }
}
