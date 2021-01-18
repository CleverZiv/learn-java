package com.leng.algorithm.interview.chapter1;

/**
 * @Classname Algo1
 * @Date 2021/1/19 0:25
 * @Autor lengxuezhang
 *
 */
public class Algo1 {
    public static void main(String[] args) {
        String s = "(())(()()(()";
        System.out.println(validBrakets(s));
    }

    /**
     *
     *  已知一个字符串都是由左括号(和右括号)组成，判断该字符串是否是有效的括号组合。例子：
     *  有效的括号组合:()()(())(()())
     *  无效的括号组合:(())(()()(()
     * @param s
     * @return
     */
    private static boolean validBrakets(String s) {
        /**
         * 一、找规律：对于有效的括号的组合有以下几个特点：
         * 1. 字符串中 ( 和 ) 的数量一定是一样的
         * 2. 在未到达字符串最后一位前，( 的数量必定比 ) 数量多。
         * 第1点是显而易见的，而第2点相对来说，在没见过这题的情况下，不容易想到
         *
         * 二、代码实现：逐个遍历每个字符，用一个整型变量记录 ( 和 )的差值，差值需满足上面的两点
         */
        if(s == null || s.length() == 0) {
            return false;
        }
        char[] chars = s.toCharArray();
        int braketDiff = 0;
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] == '(') {
                braketDiff++;
            }else {
                braketDiff--;
            }
            if(braketDiff < 0) {
                return false;
            }
        }
        return braketDiff == 0;
    }
}
