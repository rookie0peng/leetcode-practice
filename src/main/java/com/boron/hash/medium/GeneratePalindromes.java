package com.boron.hash.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  @description: 267. 回文排列 II <a href="https://leetcode.cn/problems/palindrome-permutation-ii/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/27
 * </pre>
 */
public class GeneratePalindromes {

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
        List<String> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("aabb").build();
        Result result = Result.builder().results(List.of("abba", "baab")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("abc").build();
        Result result = Result.builder().results(List.of()).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResults = GeneratePalindromesSolution.generatePalindromes(param.getS());
        List<String> actualResults1 = actualResults.stream().sorted().toList();
        List<String> expectResults = result.getResults();
        List<String> expectResults1 = expectResults.stream().sorted().toList();
        boolean compareResult = Objects.equals(actualResults1, expectResults1);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResults1), JSON.toJSONString(expectResults1));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }


    public static void main(String[] args) {

        test(generate0());
        test(generate1());

//        List<Character> prefixChars = new ArrayList<>();
////        Map<Character, Integer> leftCharMap = Map.of('a', 3, 'b', 2, 'c', 1);
//        Map<Character, Integer> leftCharMap = Map.of('a', 2, 'b', 1);
//        leftCharMap = leftCharMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//        int length = 3;
//        Set<String> results = new HashSet<>();
//        GeneratePalindromesSolution.backtrack("", leftCharMap, length, results);
//        System.out.println(results);
    }
}

class GeneratePalindromesSolution {
    public static List<String> generatePalindromes(String s) {
        // 初始化字符映射表
        Map<Character, Integer> charMap = new HashMap<>();
        // 统计奇数字符个数
        int countOdd = 0;
        // 遍历字符串的各个字符，将字符对应的数量放入映射表，并且更新字符个数
        for (char c : s.toCharArray()) {
            Integer count = charMap.getOrDefault(c, 0);
            charMap.put(c, count + 1);
            if ((count + 1) % 2 == 0) {
                countOdd--;
            } else {
                countOdd++;
            }
        }
        // 如果字符串的长度是偶数，那么奇数字符只能出现0次；如果字符串的长度是奇数，那么奇数字符只能出现1次
        if ((s.length() % 2 == 0 && countOdd > 0) || (s.length() % 2 == 1 && countOdd != 1)) {
            return List.of();
        }
        // 取出中间字符，如果是偶数长度的话，中间字符就是空字符串，如果是奇数长度，那中间字符是指定奇数字符
        String midChar = "";
        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                midChar = String.valueOf(entry.getKey());
                break;
            }
        }
        // 将字符映射表转为左半部分映射表
        Map<Character, Integer> leftCharMap = charMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() / 2));
        // 生成左半边字符串
        Set<String> leftResults = new HashSet<>();
        backtrack("", leftCharMap, s.length() / 2, leftResults);
        // 生成右半边字符串，并加入结果集
        List<String> results = new ArrayList<>();
        for (String leftStr : leftResults) {
            results.add(leftStr + midChar + reverse(leftStr));
        }
        // 返回结果集
        return results;
    }
    
    private static String reverse(String str) {
        // 反转字符串
        StringBuilder stringBuilder = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(str.charAt(length - 1 - i));
        }
        return stringBuilder.toString();
    }

    public static void backtrack(String prefix, Map<Character, Integer> leftCharMap, int length, Set<String> results) {
        // 如果长度等于目标长度，则终止回溯，将前缀加入结果集
        if (prefix.length() == length) {
            results.add(prefix);
            return;
        }
        // 回溯法生成左半部分结果集
        for (Character key : leftCharMap.keySet()) {
            Integer count = leftCharMap.get(key);
            if (count > 0) {
                leftCharMap.put(key, count - 1);
                backtrack(prefix + key, leftCharMap, length, results);
                leftCharMap.put(key, count);
            }
        }

    }
}