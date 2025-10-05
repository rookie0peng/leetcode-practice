package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 241. 为运算表达式设计优先级 <a href="https://leetcode.cn/problems/different-ways-to-add-parentheses/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/4
 * </pre>
 */
public class DiffWaysToCompute {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String expression;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<Integer> result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .expression("2-1-1")
                .build();
        Result result = Result.builder().result(List.of(0, 2)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .expression("2*3-4*5")
                .build();
        Result result = Result.builder().result(List.of(-34,-14,-10,-10,10)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .expression("11")
                .build();
        Result result = Result.builder().result(List.of(11)).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<Integer> actualResults = DiffWaysToComputeSolution.diffWaysToCompute(param.getExpression());
        List<Integer> expectResults = result.getResult();
        List<Integer> actualResults2 = actualResults.stream().sorted().toList();
        List<Integer> expectResults2 = expectResults.stream().sorted().toList();
        boolean compareResult = Objects.equals(actualResults2, expectResults2);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResults2), JSON.toJSONString(expectResults2));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}

/**
 * 记忆+深度优先搜索
 */
class DiffWaysToComputeSolution {
    public static List<Integer> diffWaysToCompute(String expression) {
        // 获取表达式的长度
        int len = expression.length();
        // 将表达式转为数字和运算符
        List<Integer> numbers = new ArrayList<>();
        int i = 0;
        while (i < len) {
            if (expression.charAt(i) == '+') {
                numbers.add(-1);
                i++;
            } else if (expression.charAt(i) == '-') {
                numbers.add(-2);
                i++;
            } else if (expression.charAt(i) == '*') {
                numbers.add(-3);
                i++;
            } else {
                int tmp = 0;
                while (i < len && Character.isDigit(expression.charAt(i))) {
                    tmp = tmp * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                numbers.add(tmp);
            }
        }
        // 初始化记忆数组，记录numbers的子数组计算值
        List<Integer>[][] dp = new List[len][len];
        for (int x = 0; x < len; x++) {
            for (int y = 0; y < len; y++) {
                dp[x][y] = new ArrayList<>();
            }
        }
        // 深度优先搜索+记忆
        List<Integer> dfs = dfs(dp, 0, numbers.size() - 1, numbers);
        return dfs;
    }

    static Set<Integer> SYMBOLS = Set.of(-1, -2, -3);

    public static List<Integer> dfs(List<Integer>[][] dp, int l, int r, List<Integer> numbers) {
        // 如果numbers子数组l-r未曾记录
        if (dp[l][r].isEmpty()) {
            // 如果左端点等于右端点
            if (l == r) {
                // 直接添加当前值
                dp[l][r].add(numbers.get(l));
                return dp[l][r];
            } else {
                // 从左端点遍历至右端点
                for (int i = l; i <= Math.min(numbers.size() - 1, r); i++) {
                    // 如果遇到运算符
                    if (SYMBOLS.contains(numbers.get(i))) {
                        Integer symbol = numbers.get(i);
                        // 递归获取左子数组的值和右子数组的值
                        List<Integer> left = dfs(dp, l, i - 1, numbers);
                        List<Integer> right = dfs(dp, i + 1, r, numbers);
                        int tmp = 0;
                        // 将左右结果运算
                        for (Integer lv : left) {
                            for (Integer rv : right) {
                                tmp = 0;
                                if (symbol == -1) {
                                    tmp = lv + rv;
                                } else if (symbol == -2) {
                                    tmp = lv - rv;
                                } else if (symbol == -3) {
                                    tmp = lv * rv;
                                }
                                dp[l][r].add(tmp);
                            }
                        }
                    }
                }
            }
        }
        return dp[l][r];
    }
}

/**
 * 深度优先搜索
 */
class DiffWaysToComputeSolution1 {
    public static List<Integer> diffWaysToCompute(String expression) {
        int len = expression.length();
        List<Integer> numbers = new ArrayList<>();
        int i = 0;
        while (i < len) {
            if (expression.charAt(i) == '+') {
                numbers.add(-1);
                i++;
            } else if (expression.charAt(i) == '-') {
                numbers.add(-2);
                i++;
            } else if (expression.charAt(i) == '*') {
                numbers.add(-3);
                i++;
            } else {
                int tmp = 0;
                while (i < len && Character.isDigit(expression.charAt(i))) {
                    tmp = tmp * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                numbers.add(tmp);
            }
        }
        List<Integer> dfs = dfs(numbers);
        return dfs;
    }

    static Set<Integer> SYMBOLS = Set.of(-1, -2, -3);

    private static List<Integer> dfs(List<Integer> numbers) {
        List<Integer> results = new ArrayList<>();
        if (numbers.size() == 1) {
            results.add(numbers.get(0));
            return results;
        }
        for (int i = 0; i < numbers.size(); i++) {
            Integer symbol = numbers.get(i);
            if (SYMBOLS.contains(symbol)) {
                List<Integer> left = dfs(numbers.subList(0, i));
                List<Integer> right = dfs(numbers.subList(i+1, numbers.size()));
                int tmp = 0;
                for (Integer l : left) {
                    for (Integer r : right) {
                        tmp = 0;
                        if (symbol == -1) {
                            tmp = l + r;
                        } else if (symbol == -2) {
                            tmp = l - r;
                        } else if (symbol == -3) {
                            tmp = l * r;
                        }
                        results.add(tmp);
                    }
                }
            }
        }
        return results;
    }
}
