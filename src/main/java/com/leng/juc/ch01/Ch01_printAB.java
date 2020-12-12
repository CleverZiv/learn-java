package com.leng.juc.ch01;

/**
 * @Classname Ch01_printAB
 * @Date 2020/12/12 10:16
 * @Autor lengxuezhang
 *
 * 要求线程依次打印A1B2C3...
 */
public class Ch01_printAB {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                synchronized (this) {
                    while(i < 26) {
                        System.out.println((char)('A'+i++));
                        notify();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            int i = 1;
            @Override
            public void run() {
                synchronized (this) {
                    while(i < 27) {
                        System.out.println(i++);
                        notify();

                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
