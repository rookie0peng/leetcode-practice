package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

/**
 * <pre>
 *  @description: 224. 基本计算器 <a href="https://leetcode.cn/problems/basic-calculator/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/1
 * </pre>
 */
public class Calculate {

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
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .s("1 + 1")
                .build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    //  2-1 + 2
    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .s("  2-1 + 2 ")
                .build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .s("(1+(4+5+2)-3)+(6+8)")
                .build();
        Result result = Result.builder().result(23).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = CalculateSolution.calculate(param.getS());
        int expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

//        test(generate0());
        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}

class CalculateSolution {
    public static int calculate(String s) {
        int len = s.length();
        Deque<Integer> ops = new LinkedList<>();
        // 1代表+，-1代表-
        ops.push(1);
        int i = 0;
        int sign = 1;
        long res = 0;
        while (i < len) {
            char c = s.charAt(i);
            if (c == ' ') {
                i++;
            } else if (c == '+') {
                sign = ops.peek();
                i++;
            } else if (c == '-') {
                sign = -ops.peek();
                i++;
            } else if (c == '(') {
                ops.push(sign);
                i++;
            } else if (c == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < len) {
                    c = s.charAt(i);
                    if (c >= '0' && c <= '9') {
                        num = num * 10 + (c - '0');
                        i++;
                    } else {
                        break;
                    }
                }
                res += sign * num;
            }
        }
        return (int) res;
    }
}
