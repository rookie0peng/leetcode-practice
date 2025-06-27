package com.boron.hash.easy;

/**
 * <pre>
 *  @description: 266. 回文排列 <a href="https://leetcode.cn/problems/palindrome-permutation/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/27
 * </pre>
 */
public class CanPermutePalindrome {
}

class CanPermutePalindromeSolution {
    /**
     * 使用数组作为映射表
     */
    public boolean canPermutePalindrome(String s) {
        // 初始化映射表
        int[] mapArr = new int[128];
        // 将字符串转为字符数组
        char[] sArr = s.toCharArray();
        // 遍历字符数组
        for (char c : sArr) {
            // 如果字符对应的元素为0，则+1，否则-1
            if (mapArr[c] == 0) {
                mapArr[c]++;
            } else {
                mapArr[c]--;
            }
        }
        // 遍历映射表，遇到1则计数+1
        int count1 = 0;
        for (int i = 'a'; i <= 'z'; i++) {
            if (mapArr[i] == 1) {
                count1++;
            }
        }
        // 如果字符串长度为偶数，则判断1的出现次数是否为0，
        // 否则，判断1的出现次数是否为1
        if (s.length() % 2 == 0) {
            return count1 == 0;
        } else {
            return count1 == 1;
        }
    }
}