package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 229. 多数元素 II <a href="https://leetcode.cn/problems/majority-element-ii/submissions/639306980/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class MajorityElement2 {

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
        Param param = Param.builder().nums(new int[] {3,2,3}).build();
        Result result = Result.builder().results(List.of(3)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {1}).build();
        Result result = Result.builder().results(List.of(1)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {1,2}).build();
        Result result = Result.builder().results(List.of(1,2)).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<Integer> actualResults = MajorityElement2Solution.majorityElement(param.getNums());
        List<Integer> actualResults1 = actualResults.stream().sorted().toList();
//        int[][] actualResults = param.getMatrix();
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
        test(generate1());
        test(generate2());

//        double pow = Math.pow(10, 9);
//        System.out.println(pow);
//        System.out.println(pow < Integer.MAX_VALUE);
    }
}

class MajorityElement2Solution {

    /**
     * 摩尔投票法，每选举出3个不同的元素，将这3个元素抵消，直到最后，看剩下几个元素。
     * 如果是1或者2个，则进行第二次遍历，统计这两个元素出现的次数是否大于n/3，
     * 如果没有剩下，则返回false。
     */
    public static List<Integer> majorityElement(int[] nums) {
        int value1 = 0;
        int value2 = 0;
        int count1 = 0;
        int count2 = 0;
//        int value3 = 0;
        for (int num : nums) {
            if (count1 > 0 && num == value1) {
                count1++;
            } else if (count2 > 0 && num == value2) {
                count2++;
            } else if (count1 == 0) {
                value1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                value2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        int count11 = 0;
        int count22 = 0;
        for (int num : nums) {
            if (num == value1) {
                count11++;
            } else if (num == value2) {
                count22++;
            }
        }
        int target = nums.length / 3;
        List<Integer> results = new ArrayList<>();
        if (count11 > target) {
            results.add(value1);
        }
        if (count22 > target) {
            results.add(value2);
        }
        return results;
    }

    /**
     * 哈希
     */
    public static List<Integer> majorityElement1(int[] nums) {
        Map<Integer, Integer> numToCountMap = new HashMap<>();
        int target = nums.length / 3;
        for (int num : nums) {
            Integer count = numToCountMap.getOrDefault(num, 0);
            numToCountMap.put(num, count + 1);
        }
        List<Integer> results = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : numToCountMap.entrySet()) {
            if (entry.getValue() > target) {
                results.add(entry.getKey());
            }
        }
        return results;
    }
}