package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 65. 有效数字 <a href="https://leetcode.cn/problems/valid-number/submissions/660721672/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/3
 * </pre>
 */
public class IsNumber {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String s;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("0").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("e").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s(".").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("e9").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().s(".e9").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate5() {
        Param param = Param.builder().s("0.e9").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate6() {
        Param param = Param.builder().s("1e.").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate7() {
        Param param = Param.builder().s("6+1").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate8() {
        Param param = Param.builder().s("-E+3").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate9() {
        Param param = Param.builder().s("6e6.5").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate10() {
        Param param = Param.builder().s("3.").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate11() {
        Param param = Param.builder().s("32e8.616").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsNumberSolution.isNumber(param.getS());
        boolean expectResult = result.isResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

//        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
//        test(generate7());
//        test(generate8());
//        test(generate9());
//        test(generate10());
        test(generate11());

        // 1
    }
}

class IsNumberSolution {

    public static boolean isNo(char c) {
        return c >= 48 && c <= 57;
    }

    public static boolean isE(char c) {
        return c == 'e' || c == 'E';
    }

    public static boolean isSymbol(char c) {
        return c == '-' || c == '+';
    }

    public static boolean isNumber(String s) {
        int len = s.length();
        int numCount = 0;
        int eCount = 0;
        int eIdx = -1;
        int symbolCount = 0;
        Set<Integer> symbols = new HashSet<>();
        int pointCount = 0;
        int pointIdx = -1;
        int invalidCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isNo(c)) {
                numCount++;
            } else if (isE(c)) {
                eCount++;
                eIdx = i;
            } else if (isSymbol(c)) {
                symbolCount++;
                symbols.add(i);
            } else if (c == '.') {
                pointCount++;
                pointIdx = i;
            } else {
                invalidCount++;
            }
        }
        // 数字
        if (numCount == 0) {
            return false;
        }
        // 非法字符
        if (invalidCount > 0) {
            return false;
        }
        // 指数
        if (eCount >= 2) {
            return false;
        }
        if (eCount == 1) {
            if (eIdx == 0 || eIdx == len - 1) {
                return false;
            }
            if (s.charAt(eIdx - 1) == '.') {
                if (eIdx - 1 == 0) {
                    return false;
                }
                if (!isNo(s.charAt(eIdx - 2))) {
                    return false;
                }
            }
            if (isSymbol(s.charAt(eIdx + 1))) {
                if (eIdx + 1 == len - 1) {
                    return false;
                }
                if (!isNo(s.charAt(eIdx + 2))) {
                    return false;
                }
            }
        }
        // 小数点
        if (pointCount >= 2) {
            return false;
        }
        // 如果小数点在e的后面
        if (pointCount == 1 && eCount == 1) {
            if (eIdx < pointIdx) {
                return false;
            }
        }
        // 正负符号
        if (symbolCount > 0) {
            for (int idx : symbols) {
                if (idx != 0 && !isE(s.charAt(idx - 1))) {
                    return false;
                }
                if (idx == 0 && isE(s.charAt(idx + 1))) {
                    return false;
                }
            }
        }
        return true;
    }
}
