package ru.javawebinar.basejavaold.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static volatile int summ;
    private static AtomicInteger ATOMIC_SUM = new AtomicInteger();
    private static int syncSum;

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            new Thread() {
                @Override
                public void run() {
                    summ++;
                    ATOMIC_SUM.incrementAndGet();
                    synchronized(this) {
                        syncSum++;
                    }
                }
            }.start();
        }
        System.out.println(summ);
        System.out.println(ATOMIC_SUM.get());
        System.out.println(syncSum);

    }
}
