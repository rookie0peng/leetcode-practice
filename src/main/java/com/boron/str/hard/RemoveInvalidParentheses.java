package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  @description: 282. 给表达式添加运算符 <a href="https://leetcode.cn/problems/remove-invalid-parentheses/submissions/670631063/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/14
 * </pre>
 */
public class RemoveInvalidParentheses {

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
        List<String> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .s("()())()")
                .build();
        Result result = Result.builder().results(List.of("(())()","()()()")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .s("(a)())()")
                .build();
        Result result = Result.builder().results(List.of("(a())()","(a)()()")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .s(")(")
                .build();
        Result result = Result.builder().results(List.of("")).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResult = RemoveInvalidParenthesesSolution.removeInvalidParentheses(param.getS());
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
//        test(generate0());
//        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}

class RemoveInvalidParenthesesSolution {

    private static List<String> RES;

    public static List<String> removeInvalidParentheses(String s) {
        RES = new ArrayList<>();
        int lRemove = 0, rRemove = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                lRemove++;
            } else {
                if (c == ')') {
                    if (lRemove > 0) {
                        lRemove--;
                    } else {
                        rRemove++;
                    }
                }
            }
        }
        dfs(s, 0, lRemove, rRemove);
        return RES;
    }

    private static void dfs(String s, int idx, int lRemove, int rRemove) {
        if (lRemove == 0 && rRemove == 0) {
            if (isValid(s)) {
                RES.add(s);
            }
            return;
        }

        for (int i = idx; i < s.length(); i++) {
            if (i != idx && s.charAt(i) == s.charAt(i - 1)) {
                continue;
            }
            if (lRemove + rRemove > s.length() - i) {
                return;
            }
            if (lRemove > 0 && s.charAt(i) == '(') {
                dfs(s.substring(0, i) + s.substring(i + 1), i, lRemove - 1, rRemove);
            }
            if (rRemove > 0 && s.charAt(i) == ')') {
                dfs(s.substring(0, i) + s.substring(i + 1), i, lRemove, rRemove - 1);
            }
        }
    }

    private static boolean isValid(String s) {
        int leftBracket = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftBracket++;
            } else {
                if (c == ')') {
                    if (leftBracket == 0) {
                        return false;
                    } else {
                        leftBracket--;
                    }
                }
            }
        }
        return leftBracket == 0;
    }
}
