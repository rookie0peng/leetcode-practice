package com.boron.str.easy;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 14. 最长公共前缀 <a href="https://leetcode.cn/problems/longest-common-prefix/submissions/654643738/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/8/19
 * </pre>
 */
public class LongestCommonPrefix {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String[] strs;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().strs(new String[] {"flower","flow","flight"}).build();
        Result result = Result.builder().result("fl").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().strs(new String[] {"dog","racecar","car"}).build();
        Result result = Result.builder().result("").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().strs(new String[] {"a"}).build();
        Result result = Result.builder().result("a").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().strs(new String[] {"ab", "a"}).build();
        Result result = Result.builder().result("a").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = LongestCommonPrefixSolution.longestCommonPrefix(param.getStrs());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
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

class LongestCommonPrefixSolution {
    
    public static String longestCommonPrefix(String[] strs) {
        // 获取木桶的最小短板
        int minSL = strs[0].length();
        for (String s : strs) {
            int length = s.length();
            minSL = Math.min(minSL, length);
        }
        // 如果最小短板为0，直接返回空字符串
        if (minSL == 0) {
            return "";
        }
        // 最右端点
        int maxRight = -1;
        // 遍历最右端点
        one: for (int i = 0; i < minSL; i++) {
            // 初始化第一个字符串的第i个下标
            char c = strs[0].charAt(i);
            for (String str : strs) {
                // 如果这个字符不等于第一个字符
                if (c != str.charAt(i)) {
                    break one;
                }
            }
            // 如果没有跳出循环，则最右端点向右移动一位
            maxRight++;
        }
        // 如果最右端点等于-1，则返回空字符串，否则，返回指定的前缀
        return maxRight == -1 ? "" : strs[0].substring(0, maxRight + 1);
    }
}
