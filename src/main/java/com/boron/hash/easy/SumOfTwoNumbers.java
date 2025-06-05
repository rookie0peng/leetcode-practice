package com.boron.hash.easy;

import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <pre>
 *  @description: 1.两数之和 <a href="https://leetcode.cn/problems/two-sum/description/?envType=problem-list-v2&envId=hash-table"></>
 *  @author: BruceBoron
 *  @date: 2025/6/5
 * </pre>
 */
public class SumOfTwoNumbers {

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {2,7,11,15}).target(9).build();
        Result result = Result.builder().nums(new int[] {0, 1}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {3,2,4}).target(6).build();
        Result result = Result.builder().nums(new int[] {1, 2}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {3,3}).target(6).build();
        Result result = Result.builder().nums(new int[] {0, 1}).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int[] calResult = new Solution().twoSum(param.getNums(), param.getTarget());
        int[] calResult2 = Arrays.stream(calResult).sorted().toArray();
        int[] preResult = Arrays.stream(result.getNums()).sorted().toArray();
        boolean compareResult = Arrays.equals(calResult2, preResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
        test(generate1());
//        test(generate2());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Param {

    int[] nums;

    int target;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Result {
    int[] nums;
}

class Solution {

    public int[] twoSum(int[] nums, int target) {
        // 用空间换时间
        // 时间复杂度O(n)，空间复杂度O(n)
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int expect = target - nums[i];
            Integer index = map.get(expect);
            if (index != null) {
                return new int[] {index, i};
            }
            map.put(nums[i], i);
        }
        return new int[2];
    }

    public int[] twoSum1(int[] nums, int target) {
        // 时间复杂度O(n^2)，空间复杂度O(1)
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[2];
    }
}