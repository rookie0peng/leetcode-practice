package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 *  @description: 3. 无重复字符的最长子串 <a href="https://leetcode.cn/problems/longest-substring-without-repeating-characters/submissions/634786072/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/5
 * </pre>
 */
public class LengthOfLongestSubStringWithNoRepeat {

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
        int length;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("pwwkew").build();
        Result result = Result.builder().length(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("bbbbb").build();
        Result result = Result.builder().length(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("abcabcbb").build();
        Result result = Result.builder().length(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s(" ").build();
        Result result = Result.builder().length(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().s("bpfbhmipx").build();
        Result result = Result.builder().length(7).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int calLength = new LengthOfLongestSubstringSolution().lengthOfLongestSubstring(param.getS());
        int expectLength = result.getLength();
        boolean compareResult = calLength == expectLength;
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



class LengthOfLongestSubstringSolution {
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int lastRemoveIndex = 0;
        // 滑动窗口，记录每个字符以及下标
        Map<Character, Integer> map = new HashMap<>();

        int sLength = s.length();
        for (int i = 0; i < sLength; i++) {
            // 如果剩下的长度+map的长度 <= 最大长度，直接跳出循环
            if (sLength - i + map.size() <= maxLength) {
                break;
            }
            char nowChar = s.charAt(i);
            Integer existIndex = map.get(nowChar);
            // 当前字符是否存在，存在就删除上一个删除点到重复字符坐标的所有字符
            if (existIndex != null) {
                for (int j = lastRemoveIndex; j <= existIndex; j++) {
                    map.remove(s.charAt(j));
                }
                lastRemoveIndex = existIndex + 1;
            }
            // 将当前字符放入map，统计长度
            map.put(nowChar, i);
            maxLength = Math.max(maxLength, map.keySet().size());
        }
        return maxLength;
    }
}
