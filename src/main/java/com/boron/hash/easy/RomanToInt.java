package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;

/**
 * <pre>
 *  @description: 13.罗马数字转整数 <a href="https://leetcode.cn/problems/two-sum/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/8
 * </pre>
 */
public class RomanToInt {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String str;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int num;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().str("III").build();
        Result result = Result.builder().num(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().str("IV").build();
        Result result = Result.builder().num(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().str("IX").build();
        Result result = Result.builder().num(9).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().str("LVIII").build();
        Result result = Result.builder().num(58).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().str("MCMXCIV").build();
        Result result = Result.builder().num(1994).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int calResult = RomanToIntSolution.romanToInt(param.getStr());
        int preResult = result.getNum();
        boolean compareResult = calResult == preResult;
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
        test(generate4());
    }
}

class RomanToIntSolution {

    int[] values = new int[] {1, 5, 10, 50, 100, 500, 1000};
    char[] symbols = new char[] {'I', 'V', 'X', 'L', 'C', 'D', 'M'};

    public static int romanToInt(String s) {
        int length = s.length();
        int result = 0;
        char preChar = '0';
        // 遍历字符串，遇到指定的字符增加对应的数值，如果该字符前一个字符是特定字符，还需要减去对应的值
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            switch (c) {
                case 'I' -> result += 1;
                case 'V' -> {
                    result += 5;
                    if (preChar == 'I') {
                        result -= 2;
                    }
                }
                case 'X' -> {
                    result += 10;
                    if (preChar == 'I') {
                        result -= 2;
                    }
                }
                case 'L' -> {
                    result += 50;
                    if (preChar == 'X') {
                        result -= 20;
                    }
                }
                case 'C' -> {
                    result += 100;
                    if (preChar == 'X') {
                        result -= 20;
                    }
                }
                case 'D' -> {
                    result += 500;
                    if (preChar == 'C') {
                        result -= 200;
                    }
                }
                case 'M' -> {
                    result += 1000;
                    if (preChar == 'C') {
                        result -= 200;
                    }
                }
                default -> {
                }
            }
            preChar = c;
        }
        return result;
    }
}
