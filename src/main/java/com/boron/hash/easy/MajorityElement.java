package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  @description: 169. 多数元素 <a href="https://leetcode.cn/problems/majority-element/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/23
 * </pre>
 */
public class MajorityElement {

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
//        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(1);
        Param param = Param.builder().nums(new int[] {3,2,3}).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
//        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(1);
        Param param = Param.builder().nums(new int[] {2,2,1,1,1,2,2}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = MajorityElementSolution.majorityElement(param.getNums());
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

class MajorityElementSolution {

    /**
     * nums:      [7, 7, 5, 7, 5, 1 | 5, 7 | 5, 5, 7, 7 | 7, 7, 7, 7]
     * majority:  7  7  7  7  7  7   5  5   5  5  5  5   7  7  7  7
     * count:      1  2  1  2  1  0   1  0   1  2  1  0   1  2  3  4
     * 时间复杂度为 O(n)、空间复杂度为 O(1)
     */
    public static int majorityElement(int[] nums) {
        int majority = nums[0];
        int count = 0;
        for (int num : nums) {
            // 如果count等于0，则重新选择众数，最后一定会选中众数
            if (count == 0) {
                majority = num;
            }
            // 如果当前数字等于众数，则统计数量++
            if (majority == num) {
                count++;
            } else {
                count--;
            }
        }
        // 返回众数
        return majority;
    }

    /**
     * 哈希法解决
     */
    public static int majorityElement1(int[] nums) {
        // 建立map，用于统计不同数字对应的数量
        Map<Integer, Integer> dict = new HashMap<>();
        int n_2 = nums.length / 2;
        for (int num : nums) {
            Integer compute = dict.getOrDefault(num, 0);
            dict.put(num, compute + 1);
            // 如果数量大于一半，则返回该数值
            if (compute + 1 > n_2) {
                return num;
            }
        }
        return -1;
    }
}