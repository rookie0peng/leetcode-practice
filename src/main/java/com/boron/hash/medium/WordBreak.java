package com.boron.hash.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 139. 单词拆分 <a href="https://leetcode.cn/problems/word-break/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/18
 * </pre>
 */
public class WordBreak {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
        List<String> wordDict;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("leetcode").wordDict(List.of("leet", "code")).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("applepenapple").wordDict(List.of("apple", "pen")).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("catsandog").wordDict(List.of("cats", "dog", "sand", "and", "cat")).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("aaaaaaa").wordDict(List.of("aaaa", "aa")).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().s("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab").wordDict(List.of("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa")).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = WordBreakSolution.wordBreak(param.getS(), param.getWordDict());
        boolean expectResult = result.isResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
        test(generate4());
    }
}

class WordBreakSolution {
    
    public static boolean wordBreak(String s, List<String> wordDict) {
        // 将单词字典放入set，方便查询
        Set<String> dict = new HashSet<>(wordDict);
        // 初始化动态规划的数组
        boolean[] dp = new boolean[s.length() + 1];
        // 初始化第一个元素，为空默认true
        dp[0] = true;
        // 遍历更新动态规划数组
        for (int i = 1; i <= s.length(); i++) {
            // 从0到i，去查找是否能够组成dp[i]
            for (int j = 0; j < i; j++) {
                // 如果dp[j]为true，子字符串在字典中，则dp[i]也设置为true
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        // 返回字符串s的动态规划解
        return dp[s.length()];
    }


    private static boolean detect(String s, int left, Map<Character, Set<String>> map) {
        if (left >= s.length()) {
            return true;
        }
        char c = s.charAt(left);
        Set<String> words = map.get(c);
        if (words == null) {
            return false;
        }
        boolean found = false;
        int right;
        for (String word : words) {
            right = left + word.length();
            if (right > s.length()) {
                continue;
            }
            String tmpWord = s.substring(left, right);
            if (tmpWord.equals(word)) {
                found = detect(s, left + word.length(), map);
            }
            if (found) {
                return true;
            }
        }
        return false;
    }



}
