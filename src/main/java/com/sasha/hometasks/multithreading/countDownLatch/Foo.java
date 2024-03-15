package com.sasha.hometasks.multithreading.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class Foo {
    private final CountDownLatch latch1;
    private final CountDownLatch latch2;

    public Foo() {
        latch1 = new CountDownLatch(1);
        latch2 = new CountDownLatch(1);
    }

    public void first(Runnable r) {
        print("first");
        // Сигнализируем о выполнении метода first()
        latch1.countDown();
    }

    public void second(Runnable r) {
        try {
            // Ожидаем завершения метода first()
            latch1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("second");
        // Сигнализируем о выполнении метода second()
        latch2.countDown();
    }

    public void third(Runnable r) {
        try {
            // Ожидаем завершения метода second()
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("third");
    }

    private void print(String message) {
        System.out.print(message);
    }

    public static void main(String[] args) {
        Foo foo = new Foo();

        new Thread(() -> foo.third(null)).start();
        new Thread(() -> foo.second(null)).start();
        new Thread(() -> foo.first(null)).start();
    }
}

