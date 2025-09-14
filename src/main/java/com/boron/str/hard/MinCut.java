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
 *  @description: 132. 分割回文串 II <a href="https://leetcode.cn/problems/palindrome-partitioning-ii/submissions/662695158/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/14
 * </pre>
 */
public class MinCut {

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
        Param param = Param.builder().s("aab").build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("a").build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("ab").build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("acaabaada").build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = MinCutSolution.minCut(param.getS());
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
//        test(generate2());
        test(generate3());
    }

}

class MinCutSolution {
    public static int minCut(String s) {
        int len = s.length();
        char[] sArr = s.toCharArray();
        // 定义一个回文串数组，判断s字符串中的i到j的子串是否为回文串
        boolean[][] palindromes = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            Arrays.fill(palindromes[i], true);
        }
        // (i, j)的值取决于(i+1,j-1)和s[i]==s[j]
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i + 1; j < len; j++) {
                palindromes[i][j] = sArr[i] == sArr[j] && palindromes[i + 1][j - 1];
            }
        }
        // 最小切割数组
        int[] f = new int[len];
        // 存入最大值，方便计算
        Arrays.fill(f, Integer.MAX_VALUE);
        for (int i = 0; i < len; i++) {
            // 如果从0到i为回文串，则切割次数为0
            if (palindromes[0][i]) {
                f[i] = 0;
            } else {
                // 如果不为回文串，则从0到i一步步计算最小切割数。
                for (int j = 0; j < i; j++) {
                    if (palindromes[j + 1][i]) {
                        f[i] = Math.min(f[i], f[j] + 1);
                    }
                }
            }
            
        }
        return f[len - 1];
    }
}
