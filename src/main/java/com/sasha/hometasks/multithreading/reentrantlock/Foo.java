package com.sasha.hometasks.multithreading.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Foo {
    private final Lock lock;
    private final Condition firstDone;
    private final Condition secondDone;

    private int state;

    public Foo() {
        lock = new ReentrantLock();
        firstDone = lock.newCondition();
        secondDone = lock.newCondition();
        state = 1;
    }

    public void first(Runnable r) {
        lock.lock();
        try {
            // Проверяем, что порядок верный (сначала first(), затем second(), затем third())
            print("first");
            // Переходим к следующему состоянию
            state = 2;
            // Сигнализируем о завершении first()
            secondDone.signal();
        } finally {
            lock.unlock();
        }
    }

    public void second(Runnable r) {
        lock.lock();
        try {
            // Проверяем, что порядок верный (сначала first(), затем second(), затем third())
            while (state != 2) {
                secondDone.await();
            }
            print("second");
            // Переходим к следующему состоянию
            state = 3;
            // Сигнализируем о завершении second()
            firstDone.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void third(Runnable r) {
        lock.lock();
        try {
            // Проверяем, что порядок верный (сначала first(), затем second(), затем third())
            while (state != 3) {
                firstDone.await();
            }
            print("third");
            // Переходим к следующему состоянию (по желанию)
            state = 1;
            // Сигнализируем о завершении third()
            secondDone.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void print(String message) {
        System.out.print(message);
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        Runnable taskA = () -> foo.first(null);
        Runnable taskB = () -> foo.second(null);
        Runnable taskC = () -> foo.third(null);

        new Thread(taskC).start();
        new Thread(taskB).start();
        new Thread(taskA).start();
    }
}
