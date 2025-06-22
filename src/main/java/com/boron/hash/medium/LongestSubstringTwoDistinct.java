package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

/**
 * <pre>
 *  @description: 159. 至多包含两个不同字符的最长子串 <a href="https://leetcode.cn/problems/longest-substring-with-at-most-two-distinct-characters/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/22
 * </pre>
 */
public class LongestSubstringTwoDistinct {

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
        Param param = Param.builder().s("eceba").build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("ccaabbb").build();
        Result result = Result.builder().result(5).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = LongestSubstringTwoDistinctSolution.lengthOfLongestSubstringTwoDistinct(param.getS());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
    }
}


class LongestSubstringTwoDistinctSolution {

    /**
     * 暴力遍历，时间复杂度O(N^2)
     */
    public static int lengthOfLongestSubstringTwoDistinct(String s) {
        // 将字符串转为字符数组
        char[] sArr = s.toCharArray();
        // 最大长度
        int maxLen = -1;
        // 字符数量统计
        int charCount = 0;
        // map，用来存放字符，以及对应的数量
        int[] arr = new int[128];
        // 开始下标，也是左端点
        int start = 0;
        // 遍历字符数组
        for (int i = 0; i < sArr.length; i++) {
            // 如果当前字符对应的数量等于0，并且字符种类+1
            // 对当前字符的数量+1
            if (arr[sArr[i]]++ == 0) {
                charCount++;
            }
            // 如果字符种类大于2，且左端点的字符数量-1后等于0，
            // 右端点右移一位
            while (charCount > 2 && --arr[sArr[start++]] == 0) {
                // 字符种类-1
                charCount--;
            }
            // 计算最大长度
            maxLen = Math.max(i - start + 1, maxLen);
        }
        return maxLen;
    }

    /**
     * 暴力遍历，时间复杂度O(N^2)
     */
    public static int lengthOfLongestSubstringTwoDistinct1(String s) {
        char[] sArr = s.toCharArray();
        char char1 = '-';

        char char2 = '-';
        int maxLen = -1;

        for (int i = 0; i < sArr.length; i++) {
            int tmpMaxLen = 1;
            char2 = char1 = sArr[i];
            for (int j = i + 1; j < sArr.length; j++) {
                if (char2 == char1) {
                    if (sArr[j] != char1) {
                        char2 = sArr[j];
                    }
                    tmpMaxLen++;
                } else {
                    if (sArr[j] == char1 || sArr[j] == char2) {
                        tmpMaxLen++;
                    } else {
                        break;
                    }
                }
            }
            maxLen = Math.max(tmpMaxLen, maxLen);
        }
        return maxLen;
    }
}
