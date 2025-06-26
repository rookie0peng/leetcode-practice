package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  @description: 219. 存在重复元素 II <a href="https://leetcode.cn/problems/contains-duplicate-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class ContainsDuplicate2 {

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
        Param param = Param.builder().nums(new int[] {1,2,3,1}).k(3).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {1,0,1,1}).k(1).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {1,2,3,1,2,3}).k(2).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = ContainsDuplicate2Solution.containsNearbyDuplicate(param.getNums(), param.getK());
        boolean expectResult = result.isResult();
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
    }
}

class ContainsDuplicate2Solution {

    /**
     * 滑动窗口，以及哈希+滑动窗口
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            int right = Math.min(i + k + 1, nums.length);
            for (int j = i + 1; j < right; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 哈希
     */
    public static boolean containsNearbyDuplicate1(int[] nums, int k) {
        Map<Integer, Integer> numToIndexMap = new HashMap<>();
        int lastIndex;
        for (int i = 0; i < nums.length; i++) {
            if (numToIndexMap.containsKey(nums[i])) {
                lastIndex = numToIndexMap.get(nums[i]);
                if (i - lastIndex <= k) {
                    return true;
                }
            }
            numToIndexMap.put(nums[i], i);
        }
        return false;
    }
}
