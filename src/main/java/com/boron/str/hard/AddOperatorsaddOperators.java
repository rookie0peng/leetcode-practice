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
 *  @description: 282. 给表达式添加运算符 <a href="https://leetcode.cn/problems/expression-add-operators/submissions/670344788/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/13
 * </pre>
 */
public class AddOperatorsaddOperators {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String num;
        int target;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<String> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .num("123")
                .target(6)
                .build();
        Result result = Result.builder().results(List.of("1+2+3", "1*2*3")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .num("232")
                .target(8)
                .build();
        Result result = Result.builder().results(List.of("2*3+2", "2+3*2")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .num("3456237490")
                .target(9191)
                .build();
        Result result = Result.builder().results(List.of()).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResult = AddOperatorsaddOperatorsSolution.addOperators(param.getNum(), param.getTarget());
        List<String> expectResult = result.getResults();
        List<String> actualResult1 = actualResult.stream().sorted().toList();
        List<String> expectResult1 = expectResult.stream().sorted().toList();
        boolean compareResult = Objects.equals(actualResult1, expectResult1);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult1), JSON.toJSONString(expectResult1));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        System.out.println(Math.pow(10, 15) > Integer.MAX_VALUE);;
        test(generate0());
        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}

class AddOperatorsaddOperatorsSolution {

    private static int TARGET;
    private static String NUM;
    private static List<String> RESULTS;

    public static List<String> addOperators(String num, int target) {
        TARGET = target;
        NUM = num;
        RESULTS = new ArrayList<>();
        StringBuilder expr = new StringBuilder();
        dfs(0, 0, expr, 0);
        return RESULTS;
    }

    private static void dfs(int idx, long result, StringBuilder expr, long lastValue) {
        if (idx == NUM.length()) {
            if (result == TARGET) {
                RESULTS.add(expr.toString());
            }
            return;
        }
        // 获取当前表达式长度，后面截取stringbuilder用
        int signIndex = expr.length();
        if (signIndex != 0) {
            // 占位符
            expr.append('?');
        }
        // 枚举数字
        long curValue = 0;
        // 要么第一个字符不为0，要么长度为1
        for (int i = idx; i < NUM.length() && (NUM.charAt(idx) != '0' || i == idx); i++) {
            curValue = curValue * 10 + (NUM.charAt(i) - '0');
            expr.append(NUM.charAt(i));
            if (idx == 0) {
                // 如果是第一个数字，则无需添加符号
                dfs(i + 1, curValue, expr, curValue);
            } else {
                // 枚举符号
                expr.setCharAt(signIndex, '+');
                dfs(i + 1, result + curValue, expr, curValue);
                expr.setCharAt(signIndex, '-');
                dfs(i + 1, result - curValue, expr, -curValue);
                expr.setCharAt(signIndex, '*');
                dfs(i + 1, result - lastValue + lastValue * curValue, expr, lastValue * curValue);
            }
        }
        expr.setLength(signIndex);
    }
}
