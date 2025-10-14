package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  @description: 306. 累加数 <a href="https://leetcode.cn/problems/additive-number/submissions/670643664/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/14
 * </pre>
 */
public class IsAdditiveNumber {

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
        Boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .num("112358")
                .build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .num("199100199")
                .build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .num("0")
                .build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder()
                .num("10")
                .build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder()
                .num("121474836472147483648")
                .build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        Boolean actualResult = IsAdditiveNumberSolution.isAdditiveNumber(param.getNum());
        Boolean expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        System.out.println(Math.pow(10, 15) > Integer.MAX_VALUE);;
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}

class IsAdditiveNumberSolution {

    static boolean VALID;

    public static boolean isAdditiveNumber(String num) {
        VALID = false;
        return dfs(num, 0, -1, -1);
    }

    private static boolean dfs(String num, int idx, long number1, long number2) {
        if (idx == num.length()) {
            return VALID && number1 != -1 && number2 != -1;
        }
        long tmp = 0;
        for (int i = idx; i < num.length(); i++) {
            char c = num.charAt(i);
            if (i > idx && num.charAt(idx) == '0') {
                break;
            }
            tmp = tmp * 10 + c - '0';
            boolean dfs = false;
            if (number1 == -1) {
                dfs = dfs(num, i + 1, tmp, number2);
            } else if (number2 == -1) {
                dfs = dfs(num, i + 1, number1, tmp);
            } else {
                if (tmp == number1 + number2) {
                    VALID = true;
                    dfs = dfs(num, i + 1, number2, tmp);
                    VALID = false;
                } else if (tmp > number1 + number2) {
                    return false;
                }
            }
            if (dfs) {
                return true;
            }
        }
        return false;
    }
}
