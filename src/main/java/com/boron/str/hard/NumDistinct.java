package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 *  @description: 115. 不同的子序列 <a href="https://leetcode.cn/problems/distinct-subsequences/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/14
 * </pre>
 */
public class NumDistinct {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String s;
        String t;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("rabbbit").t("rabbit").build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("rraab").t("ra").build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("babgbag").t("bag").build();
        Result result = Result.builder().result(5).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = NumDistinctSolution.numDistinct(param.getS(), param.getT());
        int expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
        test(generate2());
//        test(generate3());
    }
}

class NumDistinctSolution {

    public static int numDistinct(String s, String t) {
        // 获取两个字符串的长度
        int sl = s.length();
        int tl = t.length();
        // 将两个字符串转为字符数组，取值更快
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        // 动态规划数组
        int[][] dp = new int[tl + 1][sl + 1];
        // 从1开始遍历t，判断i长度的t在j长度的s中出现了几次
        for (int i = 1; i <= tl; i++) {
            // 从2开始遍历s
            for (int j = i; j <= sl; j++) {
                // t的第i-1个字符和s的第j-1个字符，是否相等
                boolean lastCharEquals = tArr[i - 1] == sArr[j - 1];
                // 如果相等，则加数为1
                int add = lastCharEquals ? 1 : 0;
                // 初始化基础数组
                if (i == 1) {
                    // dp[i][j]等于同一行前一个dp值与加数的和
                    dp[i][j] = dp[i][j - 1] + add;
                    continue;
                }
                // 如果i==j，则dp[i][j]的值需要根据斜线前一个的值来推断
                if (i == j) {
                    dp[i][j] = dp[i- 1][j - 1] == 1 ? add : 0;
                    continue;
                }
                // 最后一个字符是否相等
                if (lastCharEquals) {
                    // 如果相等，则结果等于斜线前一个值+当前行的前一个值
                    dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1];
                } else {
                    // 如果不相等，则直接等于斜线的前一个值
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }

        return dp[tl][sl];
    }
}
