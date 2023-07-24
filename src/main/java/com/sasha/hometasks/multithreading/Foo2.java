package com.sasha.hometasks.multithreading;

public class Foo2 {
    private boolean firstDone;
    private boolean secondDone;


    public synchronized void first(Runnable r) {
        r.run();
        firstDone = true;
        notifyAll();
    }

    public synchronized void second(Runnable r) {
        while (!firstDone) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        r.run();
        secondDone = true;
        notifyAll();
    }

    public synchronized void third(Runnable r) {
        while (!secondDone) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        r.run();

    }

    public static void main(String[] args) {
        Foo2 foo = new Foo2();

        Runnable printFirst = () -> System.out.print("first");
        Runnable printSecond = () -> System.out.print("second");
        Runnable printThird = () -> System.out.print("third");

        new Thread(() -> foo.first(printFirst)).start();
        new Thread(() -> foo.second(printSecond)).start();
        new Thread(() -> foo.third(printThird)).start();


    }
}

