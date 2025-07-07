package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  @description: 442. 数组中重复的数据 <a href="https://leetcode.cn/problems/find-all-duplicates-in-an-array/solutions/1473718/shu-zu-zhong-zhong-fu-de-shu-ju-by-leetc-782l/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/7
 * </pre>
 */
public class FindDuplicates {

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
        List<Integer> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {4,3,2,7,8,2,3,1}).build();
        Result result = Result.builder().results(List.of(2,3)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {1,1,2}).build();
        Result result = Result.builder().results(List.of(1)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {1}).build();
        Result result = Result.builder().results(List.of()).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<Integer> actualResults = FindDuplicatesSolution.findDuplicates(param.getNums());
        List<Integer> actualResults1 = actualResults.stream().sorted().toList();
        List<Integer> expectResults = result.getResults();
        List<Integer> expectResults1 = expectResults.stream().sorted().toList();
        boolean compareResult = Objects.equals(actualResults1, expectResults1);
        System.out.println("actualResults1 vs expectResults1");
        System.out.printf("%s vs %s\n", actualResults1, expectResults1);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
    }
}

class FindDuplicatesSolution {

    /**
     * 原地哈希
     */
    public static List<Integer> findDuplicates(int[] nums) {
        // 结果集
        List<Integer> results = new ArrayList<>();
        // 遍历数组
        for (int i = 0; i < nums.length; i++) {
            // 如果当前数字-1不等于当前下标，且当前数字不为0，则进行下一步交换
            while (nums[i] - 1 != i && nums[i] != 0) {
                // 暂存当前数字
                int oldI = nums[i];
                // 如果当前数字与待交换下标的数字相等，则将该数字加入结果集，然后将当前数字重置为0，进入下一轮循环
                if (nums[i] == nums[oldI - 1]) {
                    results.add(nums[oldI - 1]);
                    nums[i] = 0;
                    break;
                }
                // 交换两个位置的数字
                nums[i] = nums[oldI - 1];
                nums[oldI - 1] = oldI;
            }
        }
        // 返回结果集
        return results;
    }


}
