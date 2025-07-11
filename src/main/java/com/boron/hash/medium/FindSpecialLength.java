package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 522. 最长特殊序列 II <a href="https://leetcode.cn/problems/longest-uncommon-subsequence-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/11
 * </pre>
 */
public class FindSpecialLength {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String[] strs;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().strs(new String[] {"aba","cdc","eae"}).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().strs(new String[] {"aaa","aaa","aa"}).build();
        Result result = Result.builder().result(-1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().strs(new String[] {"aabbcc","aabbcc","cb"}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = FindSpecialLengthSolution.findLUSlength(param.getStrs());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResults vs expectResults");
//        System.out.printf("%s vs %s\n", actualResults, expectResults);
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

class FindSpecialLengthSolution {
    public static int findLUSlength(String[] strs) {
        // 初始化最大长度为-1
        int maxLen = -1;
        // 第一层遍历
        one: for (int i = 0; i < strs.length; i++) {
            // 第二层遍历
            for (int j = 0; j < strs.length; j++) {
                // 如果i和j的索引相同，说明是同一字符串，直接跳过
                if (j == i) {
                    continue;
                }
                // 判断s1是否s2的子串
                boolean isSubStr = isSubStr(strs[i], strs[j]);
                // 是，直接跳过循环1
                if (isSubStr) {
                    continue one;
                }
            }
            // 如果字符串1不是任意其他字符串2的子串，则再获取一次最大值
            maxLen = Math.max(maxLen, strs[i].length());
        }
        return maxLen;
    }

    private static boolean isSubStr(String str1, String str2) {
        // 判断字符串1 是否为字符串2 的子串
        int idx1 = 0, idx2 = 0;
        while (idx1 < str1.length() && idx2 < str2.length()) {
            // 如果字符串1的字符等于字符串2的字符
            if (str1.charAt(idx1) == str2.charAt(idx2)) {
                // 字符串1的索引后移
                idx1++;
            }
            // 字符串2的索引后移
            idx2++;
        }
        // 如果字符串1的索引移动到末尾，则认为是子串
        return idx1 == str1.length();
    }

}