package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 325. 和等于 k 的最长子数组长度 <a href="https://leetcode.cn/problems/maximum-size-subarray-sum-equals-k/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/1
 * </pre>
 */
public class MaxSubArrayLen {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        int[] nums;
        int k;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[]{1,-1,5,-2,3}).k(3).build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[]{-2,-1,2,1}).k(1).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = MaxSubArrayLenSolution.maxSubArrayLen(param.getNums(), param.getK());
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
//        test(generate2());
    }
}

class MaxSubArrayLenSolution {

    public static int maxSubArrayLen(int[] nums, int k) {
        // sum -> index，只需要把最小的index放进去即可
        Map<Long, Integer> map = new HashMap<>();
        // 前缀和
        long prefixSum = 0;
        // 最大长度
        int maxLen = 0;
        // 遍历数组
        for (int i = 0; i < nums.length; i++) {
            // 计算前缀和
            prefixSum += nums[i];
            // 如果前缀和等于k，直接记录maxLen，因为前面就算能得到和为k的值，其长度肯定小于当前长度
            if (prefixSum == k) {
                // 记录最大长度等于i+1
                maxLen = i + 1;
            } else {
                // 如果前缀和不等于k，则需要从map中查找是否有 旧前缀和 等于 当前前缀和-k
                Integer preIndex;
                if ((preIndex = map.get(prefixSum - k)) != null) {
                    // 如果有，则记录最大长度
                    maxLen = Math.max(i - preIndex, maxLen);
                }
            }
            // 如果当前前缀和没放入过map
            if (!map.containsKey(prefixSum)) {
                // 放入，如果后面还有相同的前缀和，其下标肯定大于当前下标，计算的长度肯定小于当前
                map.put(prefixSum, i);
            }
        }
        return maxLen;
    }

    /**
     * 暴力遍历
     */
    public static int maxSubArrayLen1(int[] nums, int k) {
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            int tmpSum = 0;
            if (maxLen >= nums.length - i) {
                break;
            }
            for (int j = i; j < nums.length; j++) {
                tmpSum += nums[j];
                if (tmpSum == k) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }
}
