package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 *  @description: 12. 整数转罗马数字 <a href="https://leetcode.cn/problems/integer-to-roman/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/6
 * </pre>
 */
public class IntToRoman {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int num;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String str;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().num(3749).build();
        Result result = Result.builder().str("MMMDCCXLIX").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate00() {
        Param param = Param.builder().num(9).build();
        Result result = Result.builder().str("IX").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().num(58).build();
        Result result = Result.builder().str("LVIII").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().num(1994).build();
        Result result = Result.builder().str("MCMXCIV").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String curStr = IntToRomanSolution.intToRoman(param.getNum());
        String expectStr = result.getStr();
        boolean compareResult = Objects.equals(curStr, expectStr);
        System.out.println("curStr vs expectStr");
        System.out.printf("%s vs %s\n", curStr, expectStr);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate00());
        test(generate1());
        test(generate2());
    }

}


class IntToRomanSolution {

    static String[][] bitToCharArr = new String[][] {
            new String[] {"I", "II", "III", "V"},
            new String[] {"X", "XX", "XXX", "L"},
            new String[] {"C", "CC", "CCC", "D"},
            new String[] {"M", "MM", "MMM", "M"},
    };

    // 先定义数字数组和字符串数组
    static final int[] values = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    static final String[] symbols = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String intToRoman(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        int curNum = num;
        // 同时便利两个数组，从大到小将数字对应的字符串放入缓冲区。
        for (int i = 0; i < values.length; i++) {
            int value = values[i];
            String symbol = symbols[i];
            while (curNum >= value) {
                stringBuilder.append(symbol);
                curNum -= value;
            }
        }
        return stringBuilder.toString();
    }

    public static String intToRoman0(int num) {
        int curNum = num;
        int curBit = 0;
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (curNum == 0) {
                break;
            }
            curBit = curNum % 10;
            String[] chars = bitToCharArr[i];
            if (curBit == 0) {
                // 置空
            } else if (curBit < 4) {
                results.add(0, chars[curBit - 1]);
            } else if (curBit == 4) {
                results.add(0, chars[3]);
                results.add(0, chars[0]);
            } else if (curBit < 9) {
                if (curBit > 5) {
                    results.add(0, chars[curBit - 1 - 5]);
                }
                results.add(0, chars[3]);
            } else {
                String[] nextChars = bitToCharArr[i + 1];
                results.add(0, nextChars[0]);
                results.add(0, chars[0]);
            }
            curNum = curNum / 10;
        }
        return String.join("", results);
    }
}