package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  @description: 291. 单词规律 II <a href="https://leetcode.cn/problems/word-pattern-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/28
 * </pre>
 */
public class WordPatternMatch {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String pattern;
        String s;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().pattern("abab").s("redblueredblue").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().pattern("aaaa").s("asdasdasdasd").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().pattern("aabb").s("xyzabcxzyabc").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().pattern("abab").s("asdasdasdasd").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = WordPatternMatchSolution.wordPatternMatch(param.getPattern(), param.getS());
        boolean expectResult = result.isResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

//        test(generate0());
//        test(generate1());
//        test(generate2());
        test(generate3());
    }
}

class WordPatternMatchSolution {

    public static boolean wordPatternMatch(String pattern, String s) {
        Map<Character, String> p2sMap = new HashMap<>(pattern.length());
        Map<String, Character> s2pMap = new HashMap<>(pattern.length());
        return recursion(pattern, 0, s, 0, p2sMap, s2pMap);
    }

    private static boolean recursion(String pattern, int pIndex, String s, int sIndex, Map<Character, String> p2sMap, Map<String, Character> s2pMap) {
        // 如果两个下标同时到达出口，则认为成功找到目标
        if (pIndex == pattern.length() && sIndex == s.length()) {
            return true;
        }
        // 如果只有其中一个，则认为失败
        if (pIndex >= pattern.length() || sIndex >= s.length()) {
            return false;
        }
        // 匹配器的下标找到对应字符，再根据字符找到旧映射值
        char pChar = pattern.charAt(pIndex);
        String oldP2S = p2sMap.get(pChar);
        // 双射 意味着映射双方一一对应，不会存在两个字符映射到同一个字符串，也不会存在一个字符分别映射到两个不同的字符串。
        if (oldP2S != null) {
            // 如果旧映射值不为null
            // 计算下一个映射值的下标
            int nextSIndex = sIndex + oldP2S.length();
            // 如果下一个下标大于字符串长度，或者，就映射值不等于下一个子串
            if (nextSIndex > s.length() || !oldP2S.equals(s.substring(sIndex, nextSIndex))) {
                return false;
            } else {
                // 继续递归
                return recursion(pattern, pIndex + 1, s, nextSIndex, p2sMap, s2pMap);
            }
        } else {
            // 如果旧映射值为null
            // 从s当前下标开始，遍历
            for (int i = sIndex; i < s.length(); i++) {
                // 获取子串
                String tmpS = s.substring(sIndex, i + 1);
                // 如果字符串->匹配器 map，包含该key，则说明不符合双射原则，跳过
                if (s2pMap.containsKey(tmpS)) {
                    continue;
                }
                // 推入两对值到双map
                p2sMap.put(pChar, tmpS);
                s2pMap.put(tmpS, pChar);
                // 递归下一个元素
                boolean recursion = recursion(pattern, pIndex + 1, s, i + 1, p2sMap, s2pMap);
                // 成功则返回true
                if (recursion) {
                    return true;
                }
                // 不成功则将两对值移出map
                p2sMap.remove(pChar);
                s2pMap.remove(tmpS);
            }
        }
        // 默认返回false
        return false;
    }
}
