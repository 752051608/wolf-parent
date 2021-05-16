package com.wolf.common.test.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: hon.g
 * @create: 2021-05-14 18:15
 **/

public class jiaotidayin {

    public static void main(String[] args){
        AtomicInteger at = new AtomicInteger(0);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(at.get() < 100){
                    if(at.get() % 3 == 0){
                        System.out.println("A");
                        at.getAndIncrement();

                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(at.get() < 100){
                    if(at.get() % 3 == 1){
                        System.out.println("B");
                        at.getAndIncrement();
                    }
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(at.get() < 100){
                    if(at.get() % 3 == 2){
                        System.out.println("C");
                        at.getAndIncrement();
                    }
                }
            }
        });

        t1.setName("线程1");
        t2.setName("线程2");
        t2.setName("线程3");
        t1.start();
        t2.start();
        t3.start();
    }

}
