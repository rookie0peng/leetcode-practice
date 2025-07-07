package com.boron.hash.medium;

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
 *  @description: 438. 找到字符串中所有字母异位词 <a href="https://leetcode.cn/problems/find-all-anagrams-in-a-string/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/6
 * </pre>
 */
public class FindAnagrams {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
        String p;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<Integer> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("cbaebabacd").p("abc").build();
        Result result = Result.builder().results(List.of(0, 6)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("abab").p("ab").build();
        Result result = Result.builder().results(List.of(0,1,2)).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<Integer> actualResults = FindAnagramsSolution.findAnagrams(param.getS(), param.getP());
        List<Integer> expectResults = result.getResults();
        boolean compareResult = Objects.equals(actualResults, expectResults);
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
        test(generate1());
    }
}

class FindAnagramsSolution {
    public static List<Integer> findAnagrams(String s, String p) {
        // 将字符串s转为字符数组，方便使用
        char[] sArr = s.toCharArray();
        // 记录字符串p中的所有字符，以及各个字符的个数
        int[] pCountArr = new int[26];
        // 记录字符串p中的所有字符
        int[] pDict = new int[26];
        // 遍历字符串p，记录p的所有字符，以及个数
        for (char c : p.toCharArray()) {
            pCountArr[c - 'a']++;
            if (pDict[c - 'a'] == 0) {
                pDict[c - 'a']++;
            }
        }
        // 记录该窗口中满足条件的字符的个数
        int countP = 0;
        // 窗口左端点和右端点
        int left = 0, right = 0;
        // 记录满足条件的字符串的左端点
        List<Integer> indices = new ArrayList<>();
        // 判断是否需要将左端点移动到右端点，当遇到不存在于字符串p中的字符时，需要如此操作
        boolean moveToRight = false;
        // 遍历字符串s
        while (right < s.length()) {
            // 默认不需要移动
            moveToRight = false;
            // 遇到不存在于字符串p中的字符时，需要移动，并且先将右端点移至字符x，且字符x需存在于字符串p中。
            while (right < s.length() && pDict[sArr[right] - 'a'] == 0) {
                moveToRight = true;
                right++;
            }
            // 如果右端点超过字符串s的长度，终止循环
            if (right >= s.length()) {
                break;
            }
            // 是否需要移动到右端点
            if (moveToRight) {
                // 遍历窗口，将左端点移动至右端点
                while (left < right) {
                    // 如果该字符在字符串p中存在
                    if (pDict[sArr[left] - 'a'] > 0) {
                        // 字符在数组中的计数+1
                        pCountArr[sArr[left] - 'a']++;
                        // 已满足条件的字符-1
                        countP--;
                    }
                    // 左窗口+1
                    left++;
                }
            } else {
                // 如果右端点所需字符数不足
                while (left < right && pCountArr[sArr[right] - 'a'] == 0) {
                    // 左端点字符在数组中的计数+1
                    pCountArr[sArr[left] - 'a']++;
                    // 左端点+1
                    left++;
                    // 已满足条件的字符-1
                    countP--;
                }
            }
            // 右端点字符在数组中的计数-1
            pCountArr[sArr[right] - 'a']--;
            // 已满足条件的字符+1
            countP++;
            // 满足条件的字符数等于字符串p的长度
            if (countP == p.length()) {
                // 将左端点添加至结果集
                indices.add(left);
            }
            // 右端点+1
            right++;
        }
        return indices;
    }
}
