package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 17. 电话号码的字母组合 <a href="https://leetcode.cn/problems/letter-combinations-of-a-phone-number/submissions/635433799/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/8
 * </pre>
 */
public class MobileLetterCombinations {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String digits;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<String> combines;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().digits("23").build();
        Result result = Result.builder().combines(List.of("ad","ae","af","bd","be","bf","cd","ce","cf")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().digits("").build();
        Result result = Result.builder().combines(List.of()).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().digits("2").build();
        Result result = Result.builder().combines(List.of("a", "b", "c")).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualCombines = MobileLetterCombinationsSolution.letterCombinations(param.getDigits());
        List<String> actualCombines2 = actualCombines.stream().sorted().toList();
        List<String> expectCombines = result.getCombines().stream().sorted().toList();
        boolean compareResult = actualCombines2.equals(expectCombines);
        System.out.println("actualCombines2 vs expectCombines");
        System.out.printf("%s vs %s\n", actualCombines2, expectCombines);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
    }
}

class MobileLetterCombinationsSolution {

    static final Map<Character, List<String>> LETTER_MAP = letterMap();
    public static List<String> letterCombinations(String digits) {
        // 广度优先遍历
        List<String> results = List.of();
        int length = digits.length();
        if (length == 0) {
            return results;
        }
        results = LETTER_MAP.get(digits.charAt(0));
        if (length == 1) {
            return results;
        }
        for (int i = 1; i < length; i++) {
            char number = digits.charAt(i);
            List<String> letters = LETTER_MAP.get(number);
            List<String> combines = new ArrayList<>();
            for (String prefix : results) {
                for (String letter : letters) {
                    combines.add(prefix + letter);
                }
            }
            results = combines;
        }
        return results;
    }

    // 深度优先遍历，递归思路
    private static List<String> combine(List<String> prefixList, List<String> letters) {
        List<String> combines = new ArrayList<>();
        if (prefixList.isEmpty()) {
            List<String> tmpCombines = recursionCombine("", letters);
            combines.addAll(tmpCombines);
        } else {
            for (String prefix : prefixList) {
                List<String> tmpCombines = recursionCombine(prefix, letters);
                combines.addAll(tmpCombines);
            }
        }
        return combines;
    }

    private static List<String> recursionCombine(String prefix, List<String> letters) {
        List<String> results = new ArrayList<>();
        for (String letter : letters) {
            results.add(prefix + letter);
        }
        return results;
    }



    private static Map<Character, List<String>> letterMap() {
        Map<Character, List<String>> map = new HashMap<>(8);
        map.put('2', List.of("a", "b", "c"));
        map.put('3', List.of("d", "e", "f"));
        map.put('4', List.of("g", "h", "i"));
        map.put('5', List.of("j", "k", "l"));
        map.put('6', List.of("m", "n", "o"));
        map.put('7', List.of("p", "q", "r", "s"));
        map.put('8', List.of("t", "u", "v"));
        map.put('9', List.of("w", "x", "y", "z"));
        return map;
    }
}

