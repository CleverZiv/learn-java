package com.leng.juc.ch01;

/**
 * @Classname Ch01_testSync
 * @Date 2020/12/11 1:11
 * @Autor lengxuezhang
 */
public class Ch01_testSync {
    private int i = 0;

    public synchronized void operation() {
        while (true) {
            i++;
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+", i = "+i);
                if(i == 5) {
                    throw new RuntimeException();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Ch01_testSync sync = new Ch01_testSync();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sync.operation();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sync.operation();
            }
        }, "t2");
        t1.start();
        t2.start();
    }
}
