package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 424. 替换后的最长重复字符 <a href="https://leetcode.cn/problems/longest-repeating-character-replacement/submissions/641725787/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/6
 * </pre>
 */
public class CharacterReplacement {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
        int k;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("ABAB").k(2).build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("AABABBA").k(1).build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = CharacterReplacementSolution.characterReplacement(param.getS(), param.getK());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
    }
}


class CharacterReplacementSolution {

    /**
     * 滑动窗口二
     */
    public static int characterReplacement(String s, int k) {
        char[] sArr = s.toCharArray();
        int res = 0;
        int left = 0, right = 0;
        // 记录窗口内出现的字符以及频率
        int[] windowCharCount = new int[26];
        // 记录每个窗口内频率最高的字符的频率
        int maxWindowResult = 0;
        while (right < sArr.length) {
            windowCharCount[sArr[right] - 'A']++;
            maxWindowResult = Math.max(maxWindowResult, windowCharCount[sArr[right] - 'A']);
            right++;
            // 如果 窗口的长度 大于 窗口内最高频率 + k
            // 则左端点右移
            if (right - left > maxWindowResult + k) {
                windowCharCount[sArr[left] - 'A']--;
                left++;
            }
            res = Math.max(right - left, res);
        }
        return res;
    }

    /**
     * 滑动窗口一
     */
    public static int characterReplacement1(String s, int k) {
        int[] countCharArr = new int[128];
        for (int i = 0; i < s.length(); i++) {
            countCharArr[s.charAt(i)]++;
        }
        int maxLen = 0;
        for (int i = 'A'; i <= 'Z'; i++) {
            if (countCharArr[i] + k <= maxLen || countCharArr[i] == 0) {
                continue;
            }
            int usedK = 0;
            // 滑动窗口
            int left = 0, right = 0;
            int count = 0;
            while (right < s.length()) {
                if (s.charAt(right) != i) {
                    if (usedK == k) {
                        maxLen = Math.max(maxLen, count);
                        while (s.charAt(left) == i) {
                            count--;
                            left++;
                        }
                        left++;
                        usedK--;
                        count--;
                    }
                    usedK++;
                }
                count++;
                right++;
            }
            maxLen = Math.max(maxLen, count);
        }
        return maxLen;
    }
}
