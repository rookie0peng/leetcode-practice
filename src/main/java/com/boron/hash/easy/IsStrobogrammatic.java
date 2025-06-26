package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

/**
 * <pre>
 *  @description: 246. 中心对称数 <a href="https://leetcode.cn/problems/strobogrammatic-number/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class IsStrobogrammatic {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String num;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().num("69").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().num("88").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().num("962").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().num("1").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsStrobogrammaticSolution.isStrobogrammatic(param.getNum());
        boolean expectResult = result.isResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
    }
}

class IsStrobogrammaticSolution {

    /**
     * 哈希法
     * 用数组作为map使用
     */
    public static boolean isStrobogrammatic(String num) {
        if (num == null || num.length() == 0) {
            return false;
        }
        // 单个自成对称数字：1, 8, 0
        int[] singleMark = new int[] {1, 1, 0, 0, 0, 0, 0, 0, 1, 0};
        // 两个组成对称数字：1, 8, 0
        int[] doubleMark = new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0, 1};
        int left = 0;
        int right = num.length() - 1;
        char[] numArr = num.toCharArray();
        boolean result = true;
        while (left <= right) {
            int leftI = numArr[left] - '0';
            int rightI = numArr[right] - '0';
            if (left == right) {
                if (singleMark[leftI] != 1) {
                    result = false;
                    break;
                }
            } else {
                if (leftI == rightI) {
                    if (singleMark[leftI] != 1 || singleMark[rightI] != 1) {
                        result = false;
                        break;
                    }
                } else {
                    if (doubleMark[leftI] != 1 || doubleMark[rightI] != 1) {
                        result = false;
                        break;
                    }
                }
            }
            left++;
            right--;
        }
        return result;
    }
}