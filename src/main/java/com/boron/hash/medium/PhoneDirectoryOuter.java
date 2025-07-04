package com.boron.hash.medium;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  @description: 379. 电话目录管理系统 <a href="https://leetcode.cn/problems/design-phone-directory/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class PhoneDirectoryOuter {

    private static void test() {
        PhoneDirectory phoneDirectory = new PhoneDirectory(3);
        int number1 = phoneDirectory.get();      // 它可以返回任意可用的数字。这里我们假设它返回 0。
        int number2 = phoneDirectory.get();      // 假设它返回 1。
        boolean result1 = phoneDirectory.check(2);   // 数字 2 可用，所以返回 true。
        int number3 = phoneDirectory.get();      // 返回剩下的唯一一个数字 2。
        boolean result2 = phoneDirectory.check(2);   // 数字 2 不再可用，所以返回 false。
        phoneDirectory.release(2); // 将数字 2 释放回号码池。
        boolean result3 = phoneDirectory.check(2);   // 数字 2 重新可用，返回 true。

        System.out.println(number1);
        System.out.println(number2);
        System.out.println(number3);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    public static void main(String[] args) {
        test();
    }
}

/**
 * 哈希map，慢
 */
class PhoneDirectory {

    private final int maxNumbers;
    private final Set<Integer> usedNumbers;

    public PhoneDirectory(int maxNumbers) {
        this.maxNumbers = maxNumbers;
        this.usedNumbers = new HashSet<>(8);
    }

    public int get() {
        int findIdx = -1;
        for (int i = 0; i < maxNumbers; i++) {
            if (!this.usedNumbers.contains(i)) {
                findIdx = i;
                this.usedNumbers.add(i);
                break;
            }
        }
        return findIdx;
    }

    public boolean check(int number) {
        return !this.usedNumbers.contains(number);
    }

    public void release(int number) {
        this.usedNumbers.remove(number);
    }
}

/**
 * 数组更快
 */
class PhoneDirectory1 {

    private int[] allNumbers;

    public PhoneDirectory1(int maxNumbers) {
        this.allNumbers = new int[maxNumbers];
    }

    public int get() {
        int findIdx = -1;
        for (int i = 0; i < allNumbers.length; i++) {
            if (allNumbers[i] == 0) {
                findIdx = i;
                allNumbers[i] = 1;
                break;
            }
        }
        return findIdx;
    }

    public boolean check(int number) {
        if (number < 0 || number >= allNumbers.length) {
            return false;
        }
        return allNumbers[number] == 0;
    }

    public void release(int number) {
        if (number < 0 || number >= allNumbers.length) {
            return;
        }
        allNumbers[number] = 0;
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */
