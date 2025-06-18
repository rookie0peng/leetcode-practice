package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 140. 单词拆分 II <a href="https://leetcode.cn/problems/word-break-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/18
 * </pre>
 */
public class WordBreak2 {

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
        List<String> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("catsanddog").wordDict(List.of("cat","cats","and","sand","dog")).build();
        Result result = Result.builder().results(List.of("cats and dog","cat sand dog")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("pineapplepenapple").wordDict(List.of("apple","pen","applepen","pine","pineapple")).build();
        Result result = Result.builder().results(List.of("pine apple pen apple","pineapple pen apple","pine applepen apple")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("catsandog").wordDict(List.of("cats","dog","sand","and","cat")).build();
        Result result = Result.builder().results(List.of()).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("aaaaaaa").wordDict(List.of("aaa","aaaa","a")).build();
        Result result = Result.builder().results(List.of("a a a a a a a","aaa a a a a","a aaa a a a","aaaa a a a","a a aaa a a","a aaaa a a","a a a aaa a","aaa aaa a","a a aaaa a","a a a a aaa","aaa a aaa","a aaa aaa","aaaa aaa","a a a aaaa","aaa aaaa")).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResults = WordBreak2Solution.wordBreak(param.getS(), param.getWordDict());
        List<String> actualResults2 = actualResults.stream().sorted().toList();
        List<String> expectResults = result.getResults();
        List<String> expectResults2 = expectResults.stream().sorted().toList();
        boolean compareResult = Objects.equals(actualResults2, expectResults2);
        System.out.println("actualResults2 vs expectResults2");
        System.out.printf("%s vs %s\n", actualResults2, expectResults2);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
    }
}

class WordBreak2Solution {
    public static List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        List<String> preWords = new ArrayList<>(s.length());
        List<String> results = new ArrayList<>(s.length());
        recursion(s, 0, dict, preWords, results);
        return results;
    }

    private static void recursion(String s, int left, Set<String> dict, List<String> preWords, List<String> results) {
        for (int i = left; i <= s.length(); i++) {
            String tmpStr = s.substring(left, i);

            if (dict.contains(tmpStr)) {
                preWords.add(tmpStr);
                if (i == s.length()) {
                    String tmpResult = String.join(" ", preWords);
                    results.add(tmpResult);
                    preWords.remove(preWords.size() - 1);
                    break;
                }
                recursion(s, i, dict, preWords, results);
                preWords.remove(preWords.size() - 1);
            }
        }
    }
}