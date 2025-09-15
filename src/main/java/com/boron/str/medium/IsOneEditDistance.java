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
 *  @description: 161. 相隔为 1 的编辑距离 <a href="https://leetcode.cn/problems/one-edit-distance/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/15
 * </pre>
 */
public class IsOneEditDistance {

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
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("ab").t("acb").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("cab").t("ad").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("cad").t("ad").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("").t("").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().s("ab").t("ab").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate5() {
        Param param = Param.builder().s("abc").t("ab").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate6() {
        Param param = Param.builder().s("aa").t("aaa").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsOneEditDistanceSolution.isOneEditDistance(param.getS(), param.getT());
        boolean expectResult = result.isResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
        test(generate4());
        test(generate5());
        test(generate6());
    }
}

/**
 * 双指针
 */
class IsOneEditDistanceSolution {
    public static boolean isOneEditDistance(String s, String t) {
        // 获取两个字符串的长度
        int sl = s.length(), tl = t.length();
        // 如果第一个字符串大于第二个字符串，则交换位置
        if (sl > tl) {
            return isOneEditDistance(t, s);
        }
        // 如果两个字符串的长度差大于1，则两个字符串必不可能相距1
        if (tl - sl > 1) {
            return false;
        }
        // 从0遍历至sl
        for (int i = 0; i < sl; i++) {
            // 如果s在i位置的字符不等于t在i位置的字符
            if (s.charAt(i) != t.charAt(i)) {
                // 长度是否相等
                if (sl == tl) {
                    // 判断后面的子串是否相等
                    return Objects.equals(s.substring(i + 1), t.substring(i + 1));
                } else {
                    // 判断s的i之后的子串（包括i）与t的i+1之后的子串是否相等
                    return Objects.equals(s.substring(i), t.substring(i + 1));
                }
            }
        }
        // 如果所有的字符都相等，那就判断sl与tl是否只相差1
        return sl + 1 == tl;
    }
}

/**
 * 动态规划，非常慢
 */
class IsOneEditDistanceSolution1 {
    public static boolean isOneEditDistance(String s, String t) {
        int sl = s.length(), tl = t.length();
        if (sl == 0) {
            return tl == 1;
        }
        if (tl == 0) {
            return sl == 1;
        }
        // 表示从s的[0,i]，转为t的[0,j]需要多少步
        int[][] dp = new int[sl][tl];
        dp[0][0] = s.charAt(0) == t.charAt(0) ? 0 : 1;
        char s0 = s.charAt(0);
        for (int j = 1; j < tl; j++) {
            if (dp[0][j - 1] == j - 1) {
                dp[0][j] = dp[0][j - 1] + 1;
            } else {
                dp[0][j] = dp[0][j - 1] + (s0 == t.charAt(j) ? 0 : 1);
            }
        }
        char t0 = t.charAt(0);
        for (int i = 1; i < sl; i++) {
            if (dp[i - 1][0] == i - 1) {
                dp[i][0] = dp[i - 1][0] + 1;
            } else {
                dp[i][0] = dp[i - 1][0] + (t0 == s.charAt(i) ? 0 : 1);
            }
        }
        for (int i = 1; i < sl; i++) {
            char c1 = s.charAt(i);
            for (int j = 1; j < tl; j++) {
                char c2 = t.charAt(j);
                int min1 = Math.min(dp[i][j - 1], dp[i - 1][j]);
                int min2 = Math.min(dp[i - 1][j - 1], min1);
                if (min2 == 0) {
                    if (dp[i - 1][j - 1] == min2) {
                        dp[i][j] = min2 + (c1 == c2 ? 0 : 1);
                    } else {
                        dp[i][j] = 1;
                    }
                } else {
                    dp[i][j] = min2 + (c1 == c2 ? 0 : 1);
                }
            }
        }
        return dp[sl - 1][tl - 1] == 1;
    }
}
