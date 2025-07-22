package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  @description: 525. 连续数组 <a href="https://leetcode.cn/problems/contiguous-array/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/21
 * </pre>
 */
public class FindMaxLength {

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
        Param param = Param.builder().nums(new int[] {0,1}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {0,1,0}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {0,1,1,1,1,1,0,0,0}).build();
        Result result = Result.builder().result(6).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().nums(new int[] {0,1,0,1,1,1,0,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,1,0,0,0,1,0,1,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,1,1,1,0,1,0,0,1,1,1,1,1,0,0,1,1,1,1,0,0,1,0,1,1,0,0,0,0,0,0,1,0,1,0,1,1,0,0,1,1,0,1,1,1,1,0,1,1,0,0,0,1,1}).build();
        Result result = Result.builder().result(68).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().nums(new int[] {0,1,1,1,1,1,1,0,1,0,1,0,0,1,1,1}).build();
        Result result = Result.builder().result(8).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = FindMaxLengthSolution.findMaxLength(param.getNums());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResults vs expectResults");
//        System.out.printf("%s vs %s\n", actualResults, expectResults);
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

class FindMaxLengthSolution {
    public static int findMaxLength(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int[] prefixSumArr = new int[nums.length];
        int prefixSum = 0;
        int maxLen = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                prefixSum += 1;
            } else {
                prefixSum -= 1;
            }
            if (prefixSum == 0) {
                maxLen = i + 1;
            }
            prefixSumArr[i] = prefixSum;
            map.put(prefixSum, i);
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums.length - i < maxLen) {
                break;
            }
            int right;
            if (map.containsKey(prefixSumArr[i]) && (right = map.get(prefixSumArr[i])) > i) {
                maxLen = Math.max(maxLen, right - i);
            }
        }
        return maxLen;
    }
}

