package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 *  @description: 128. 最长连续序列 <a href="https://leetcode.cn/problems/longest-consecutive-sequence/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/17
 * </pre>
 */
public class LongestContinuousSequence {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {100,4,200,1,3,2}).build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {0,3,7,2,5,8,4,6,0,1}).build();
        Result result = Result.builder().result(9).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {1,0,1,2}).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().nums(new int[] {9,1,4,7,3,-1,0,5,8,-1,6}).build();
        Result result = Result.builder().result(7).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = LongestContinuousSequenceSolution.longestConsecutive(param.getNums());
        int expectResult = result.getResult();
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
    }
}

class LongestContinuousSequenceSolution {
    public static int longestConsecutive(int[] nums) {
        // 如果数组长度为0，直接返回
        if (nums.length == 0) {
            return 0;
        }
        // 将数组转为hashSet，方便查找
        Set<Integer> dict = new HashSet<>();
        for (int num : nums) {
            dict.add(num);
        }
        // 遍历字典
        int maxLen = 1;
        for (Integer curNum : dict) {
            // 如果字典包含减一的数字，则直接继续下一轮循环
            // 如果字典不包含减一的数字，则进入判断
            if (!dict.contains(curNum - 1)) {
                int curLen = 1;
                // 向上查找
                int up = curNum + 1;
                // 如果当前字典包含+1的数字，进入循环
                while (dict.contains(up)) {
                    // 当前的长度+1，数字也+1
                    curLen += 1;
                    up++;
                }
                // 计算最大长度
                maxLen = Math.max(maxLen, curLen);
            }
        }
        return maxLen;
    }
}