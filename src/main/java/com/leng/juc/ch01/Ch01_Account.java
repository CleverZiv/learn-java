package com.leng.juc.ch01;

/**
 * @Classname Ch01_Account
 * @Date 2020/12/7 23:04
 * @Autor lengxuezhang
 */
public class Ch01_Account {
    /**
     * 面试题：模拟银行账户
     * 对业务写方法加锁
     * 对业务读方法不加锁
     * 这样行不行？
     *
     * 容易产生脏读问题（dirtyRead）
     */
    private String name;
    private double balance;

    public synchronized void setBalance(String name, double balance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public /*synchronized*/ double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        Ch01_Account account = new Ch01_Account();
        new Thread(() -> account.setBalance("张三", 100.00)).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.getBalance("张三"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(account.getBalance("张三"));

    }
}
