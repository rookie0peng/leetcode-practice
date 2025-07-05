package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *  @description: 423. 从英文中重建数字 <a href="https://leetcode.cn/problems/reconstruct-original-digits-from-english/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/5
 * </pre>
 */
public class OriginalDigits {

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
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("owoztneoer").build();
        Result result = Result.builder().result("012").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("fviefuro").build();
        Result result = Result.builder().result("45").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = OriginalDigitsSolution.originalDigits(param.getS());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
        test(generate1());
//        test(generate2());
    }
}

class OriginalDigitsSolution {

    /**
     * e	0 1 3 5 7 8 9
     * f	4 5
     * g	8
     * h	3 8
     * i	5 6 8 9
     * n	1 7 9
     * o	0 1 2 4
     * r	0 3 4
     * s	6 7
     * t	2 3 8
     * u	4
     * v	5 7
     * w	2
     * x	6
     * z	0
     * -----------------------------------------------------------
     * 根据字符 g u w x z 可以确定 0 2 4 6 8 的数量。
     * 因为 4 6 8 的数量确定，那可以根据 f h s 来确定 5 3 7 的数量。
     * 因为 0 2 4 的数量确定，可以根据 o 来确定 1 的数量。
     * 因为 5 6 8 的数量确定，可以根据 i 来确定 9 的数量。
     */

    private static final String[] NUMBERS = new String[] {
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine",
    };

    private static final Map<String, Integer> WORD_TO_NUM_MAP = Map.of(
            "zero", 0,
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,

            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
            );

    private static final Map<Character, Set<String>> CHAR_TO_WORD_MAP = initCharToWordMap();

    private static final Map<String, Map<Character, Integer>> WORD_TO_CHAR_MAP = initWordToCharMap();

    private static Map<Character, Set<String>> initCharToWordMap() {
        Map<Character, Set<String>> map = new HashMap<>();
        for (String str : NUMBERS) {
            for (char c : str.toCharArray()) {
                Set<String> strings = map.computeIfAbsent(c, k -> new HashSet<>());
                strings.add(str);
            }
        }
        return map;
    }

    private static Map<String, Map<Character, Integer>> initWordToCharMap() {
        Map<String, Map<Character, Integer>> map = new HashMap<>();
        for (String str : NUMBERS) {
            Map<Character, Integer> characterIntegerMap = map.computeIfAbsent(str, k -> new HashMap<>());
            for (char c : str.toCharArray()) {
                characterIntegerMap.put(c, characterIntegerMap.getOrDefault(c, 0) + 1);
            }
        }
        return map;
    }

    public static String originalDigits(String s) {
        int[] charCountArr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            charCountArr[s.charAt(i) - 'a']++;
        }
        int[] res = new int[10];
        res[0] = charCountArr['z' - 'a'];
        res[8] = charCountArr['g' - 'a'];
        res[4] = charCountArr['u' - 'a'];
        res[2] = charCountArr['w' - 'a'];
        res[6] = charCountArr['x' - 'a'];
        res[5] = charCountArr['f' - 'a'] - res[4];
        res[3] = charCountArr['h' - 'a'] - res[8];
        res[7] = charCountArr['s' - 'a'] - res[6];
        res[1] = charCountArr['o' - 'a'] - res[0] - res[2] - res[4];
        res[9] = charCountArr['i' - 'a'] - res[5] - res[6] - res[8];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < res[i]; j++) {
                sb.append((char) (i + '0'));
            }
        }
        return sb.toString();
    }

}