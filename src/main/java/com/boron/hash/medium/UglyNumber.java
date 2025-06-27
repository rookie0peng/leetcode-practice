package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 264. 丑数 II <a href="https://leetcode.cn/problems/ugly-number-ii/submissions/639628429/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/27
 * </pre>
 */
public class UglyNumber {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int n;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().n(10).build();
        Result result = Result.builder().result(12).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().n(1).build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = UglyNumberSolution.nthUglyNumber(param.getN());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
    }
}

class UglyNumberSolution {

    /**
     * 最小堆
     */
    public static int nthUglyNumber(int n) {
        // 初始化优先级队列
        PriorityQueue<Long> queue = new PriorityQueue<>();
        // 添加初始值1
        queue.add(1L);
        // 初始化去重集合
        Set<Long> distinct = new HashSet<>();
        // 添加初始值1
        distinct.add(1L);
        // 初始化最小值1，使用long，防止int数值溢出
        long min = 1;
        // 计数
        int count = 0;
        // 存放计算结果的3个变量
        long min2 = 0, min3 = 0, min5 = 0;
        while (count < n) {
            // 弹出最小值
            min = queue.poll();
            // 分别计算与2、3、5相乘的结果
            min2 = min * 2;
            min3 = min * 3;
            min5 = min * 5;
            // 先判断是否与之前放入队列的元素重复，不重复再加入队列
            if (distinct.add(min2)) {
                queue.add(min2);
            }
            if (distinct.add(min3)) {
                queue.add(min3);
            }
            if (distinct.add(min5)) {
                queue.add(min5);
            }
            // 计数+1
            count++;
        }
        // 返回最小值
        return (int) min;
    }

    /**
     * 动态规划
     */
    public static int nthUglyNumber1(int n) {
        // 初始化3个下标
        int p2 = 1, p3 = 1, p5 = 1;
        // 初始化动态规划数组
        int[] dp = new int[n + 1];
        dp[1] = 1;
        // 用于存放计算结果的变量
        int num2 = 1, num3 = 1, num5 = 1;
        // 循环，从2开始，n结束
        for (int i = 2; i <= n; i++) {
            // 计算3个下标对应的乘算结果
            num2 = dp[p2] * 2;
            num3 = dp[p3] * 3;
            num5 = dp[p5] * 5;
            // 取出最小值
            dp[i] = Math.min(Math.min(num2, num3), num5);
            // 最小值等于哪个，就对哪个下标进行+1
            if (dp[i] == num2) {
                p2++;
            }
            if (dp[i] == num3) {
                p3++;
            }
            if (dp[i] == num5) {
                p5++;
            }
        }
        // 返回第n个元素
        return dp[n];
    }
}
