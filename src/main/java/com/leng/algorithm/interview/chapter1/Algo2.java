package com.leng.algorithm.interview.chapter1;

/**
 * @Classname Algo2
 * @Date 2021/1/19 0:41
 * @Autor lengxuezhang
 */
public class Algo2 {
    public static void main(String[] args) {
        String s = "()()(())";
        System.out.println(longestValidBrakets(s));
    }

    /**
     * 已知一个字符串都是由左括号(和右括号)组成，请返回最长有效括号子串的长度。如()()(())的最长有效子串长度为8
     *
     * 一、取套路：对于“最长子串/子数组”、“最短子串/子数组”的问题，在分析时，可以从“以当前位置结尾时的最长子串/子数组”
     * 二、找规律：如()()(())，假设dp[i]表示以i位置结尾时的最长子串长度，则dp[0]~dp[7]的值依次为：0，2,0,4,0,0,2,8。
     * 我们可以观察到，dp[i]是与dp[i-1]的值，与dp[i-dp[i-1]-1]处的括号属性，及dp[i-dp[i-1]-2]的值有关，关系为：
     * if(dp[i-dp[i-1]-1] == '(')
     * dp[i] = dp[i-1] + 2 + dp[i-dp[i-1]-2]
     *
     * @param s
     * @return
     */
    private static int longestValidBrakets(String s) {
        if(null == s || s.equals("")) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int res = 0;
        int pre = 0;
        int[] dp = new int[chars.length];
        for(int i = 1; i < chars.length; i++) {
            if(chars[i] == ')') {
               pre = i - dp[i-1]  - 1;
               if(pre >= 0 && chars[pre] == '(') {
                   dp[i] = dp[i-1] + 2 + (pre > 0 ? dp[pre-1] : 0);
               }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
