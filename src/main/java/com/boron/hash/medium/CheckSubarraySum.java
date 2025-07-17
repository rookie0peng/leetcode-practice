package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 523. 连续的子数组和 <a href="https://leetcode.cn/problems/continuous-subarray-sum/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/12
 * </pre>
 */
public class CheckSubarraySum {

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
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {23,2,4,6,7}).k(6).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {23,2,6,4,7}).k(6).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {23,2,6,4,7}).k(13).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().nums(new int[] {1,2,3}).k(5).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = CheckSubarraySumSolution.checkSubarraySum(param.getNums(), param.getK());
        boolean expectResult = result.isResult();
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
    }
}

class CheckSubarraySumSolution {
    /**
     * 取模前缀+哈希
     */
    public static boolean checkSubarraySum(int[] nums, int k) {
        // 如果数组的长度为1，直接返回false
        if (nums.length == 1) {
            return false;
        }
        // 初始化前缀模数组
        int[] prefixModArr = new int[nums.length];
        // 初始化下标0
        prefixModArr[0] = nums[0] % k;
        // 前缀模 -> 下标
        Map<Integer, Integer> prefixModMap = new HashMap<>();
        // 初始化第一个前缀模
        prefixModMap.put(prefixModArr[0], 0);
        // 遍历数组
        for (int i = 1; i < nums.length; i++) {
            // 获取每个元素的前缀模
            int prefixMod = (prefixModArr[i - 1] + nums[i]) % k;
            // 如果前缀模等于0，则直接返回true
            if (prefixMod == 0) {
                return true;
            }
            prefixModArr[i] = prefixMod;
            // 如果前缀模map包含这个key
            // 举例: prefixModArr = [1, 2, 4, 4, 3], k = 10
            // 当遍历到下标0时，前缀模为1，当遍历到下标3时，前缀模还为1，则说明从下标1~3的子数组满足条件
            // 还要注意，子数组长度必须大于等于2
            if (prefixModMap.containsKey(prefixMod)) {
                if (i - prefixModMap.get(prefixMod) > 1) {
                    return true;
                }
            } else {
                // 如果不包含该前缀模，则将该前缀模放入map
                prefixModMap.put(prefixModArr[i], i);
            }
        }
        return false;
    }
}
