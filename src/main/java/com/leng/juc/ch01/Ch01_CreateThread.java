package com.leng.juc.ch01;

import java.util.concurrent.*;

/**
 * @Classname Ch01_CreateThread
 * @Date 2020/12/7 21:47
 * @Autor lengxuezhang
 */
public class Ch01_CreateThread {
    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("Hello, MyThread!");
        }

    }

    static class MyRun implements Runnable {

        @Override
        public void run() {
            System.out.println("Hello, MyRun!");
        }
    }

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "Hello, MyCallable!";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 方式一
        new MyThread().start();
        // 方式二
        new Thread(new MyRun()).start();
        // 方式三
        new Thread(() -> {
            System.out.println("Hello Lambda!");
        }).start();
        // 方式四
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyCallable myCallable = new MyCallable();
        Future<String> submit = executorService.submit(myCallable);
        System.out.println(submit.get());
    }
}
