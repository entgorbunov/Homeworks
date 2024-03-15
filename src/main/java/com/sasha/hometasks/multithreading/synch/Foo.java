package com.sasha.hometasks.multithreading.synch;

public class Foo {
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
        Foo foo = new Foo();

        Runnable printFirst = () -> System.out.print("first");
        Runnable printSecond = () -> System.out.print("second");
        Runnable printThird = () -> System.out.print("third");

        new Thread(() -> foo.first(() -> System.out.print("first"))).start();
        new Thread(() -> foo.second(() -> System.out.print("second"))).start();
        new Thread(() -> foo.third(() -> System.out.print("third"))).start();


    }
}

