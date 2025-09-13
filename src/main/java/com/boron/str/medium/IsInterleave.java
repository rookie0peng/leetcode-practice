package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 97. 交错字符串 <a href="https://leetcode.cn/problems/decode-ways/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/13
 * </pre>
 */
public class IsInterleave {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String s1;
        String s2;
        String s3;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s1("aabcc").s2("dbbca").s3("aadbbcbcac").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s1("aabcc").s2("dbbca").s3("aadbbbaccc").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .s1("abababababababababababababababababababababababababababababababababababababababababababababababababbb")
                .s2("babababababababababababababababababababababababababababababababababababababababababababababababaaaba")
                .s3("abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababbb").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsInterleaveSolution.isInterleave(param.getS1(), param.getS2(), param.getS3());
        boolean expectResult = result.isResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
//        test(generate7());
//        test(generate8());
    }
}

/**
 * 动态规划
 */
class IsInterleaveSolution {

    public static boolean isInterleave(String s1, String s2, String s3) {
        int l1 = s1.length(), l2 = s2.length(), l3 = s3.length();
        if (l1 + l2 != l3) {
            return false;
        }
        boolean[][] dp = new boolean[l1 + 1][l2 + 1];
        dp[0][0] = true;
        // 下标0不算，实际元素从1开始
        for (int i = 0; i <= l1; i++) {
            for (int j = 0; j <= l2; j++) {
                int k = i + j - 1;
                if (i > 0) {
                    dp[i][j] = dp[i][j] || (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(k));
                }
                if (j > 0) {
                    dp[i][j] = dp[i][j] || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(k));
                }
            }
        }

        return dp[l1][l2];
    }

    public static boolean checkAlphabetNo(String s1, String s2, String s3) {
        int[] alphabets = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            alphabets[s1.charAt(i) - 'a'] += 1;
        }
        for (int i = 0; i < s2.length(); i++) {
            alphabets[s2.charAt(i) - 'a'] += 1;
        }
        for (int i = 0; i < s3.length(); i++) {
            alphabets[s3.charAt(i) - 'a'] -= 1;
        }
        for (int alphabet : alphabets) {
            if (alphabet != 0) {
                return false;
            }
        }
        return true;
    }

}

/**
 * 深度优先搜索
 * 解答成功，但是速度很慢
 */
class IsInterleaveSolution1 {

    static String S1;
    static String S2;
    static String S3;

    public static boolean isInterleave(String s1, String s2, String s3) {
        S1 = s1;
        S2 = s2;
        S3 = s3;
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        int[] alphabets = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            alphabets[s1.charAt(i) - 'a'] += 1;
        }
        for (int i = 0; i < s2.length(); i++) {
            alphabets[s2.charAt(i) - 'a'] += 1;
        }
        for (int i = 0; i < s3.length(); i++) {
            alphabets[s3.charAt(i) - 'a'] -= 1;
        }
        for (int alphabet : alphabets) {
            if (alphabet != 0) {
                return false;
            }
        }
        int idx1 = 0, idx2 = 0, idx3 = 0;
        int dfs = dfs(idx1, idx2, idx3);
        return dfs == 0;
    }

    public static int dfs(int idx1, int idx2, int idx3) {
        if (idx3 == S3.length()) {
            return 0;
        }
        if (idx1 >= S1.length() && idx2 >= S2.length()) {
            return 1;
        }
        char c1 = idx1 >= S1.length() ? '-' : S1.charAt(idx1);
        char c2 = idx2 >= S2.length() ? '-' : S2.charAt(idx2);
        char c3 = idx3 >= S3.length() ? '-' : S3.charAt(idx3);
        int dfs1 = -1;
        if (c1 == c3) {
            dfs1 = dfs(idx1 + 1, idx2, idx3 + 1);
        }
        if (dfs1 == -1) {
            if (c2 == c3) {
                int df2 = dfs(idx1, idx2 + 1, idx3 + 1);
                return df2;
            }
        }
        return dfs1;
    }

    public static char safeGetChar(int idx, String s, boolean is3) {
        if (is3) {
            return idx >= s.length() ? '=' : S1.charAt(idx);
        } else {
            return idx >= s.length() ? '-' : S1.charAt(idx);
        }
    }
}
