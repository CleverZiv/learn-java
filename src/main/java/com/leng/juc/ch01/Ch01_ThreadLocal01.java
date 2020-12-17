package com.leng.juc.ch01;

import java.util.concurrent.TimeUnit;

/**
 * @Classname Ch01_ThreadLocal01
 * @Date 2020/12/18 0:25
 * @Autor lengxuezhang
 */
public class Ch01_ThreadLocal01 {
    static ThreadLocal<Person> tl = new ThreadLocal<>();
    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person("张三"));
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(tl.get());
            }
        }).start();

        tl.remove();
    }

}
