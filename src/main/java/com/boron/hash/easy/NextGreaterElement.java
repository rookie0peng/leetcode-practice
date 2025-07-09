package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 496. 下一个更大元素 I <a href="https://leetcode.cn/problems/next-greater-element-i/submissions/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/10
 * </pre>
 */
public class NextGreaterElement {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums1;
        int[] nums2;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int[] results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums1(new int[] {4,1,2}).nums2(new int[] {1,3,4,2}).build();
        Result result = Result.builder().results(new int[] {-1,3,-1}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums1(new int[] {2,4}).nums2(new int[] {1,2,3,4}).build();
        Result result = Result.builder().results(new int[] {3,-1}).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int[] actualResults = NextGreaterElementSolution.nextGreaterElement(param.getNums1(), param.getNums2());
        int[] expectResults = result.getResults();
        boolean compareResult = Arrays.equals(actualResults, expectResults);
        System.out.println("actualResults vs expectResults");
//        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
        test(generate1());
//        test(generate2());
//        test(generate3());
    }
}

class NextGreaterElementSolution {
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        Map<Integer, Integer> num2CountMap = new HashMap<>();
        queue.add(nums2[0]);
        for (int i = 1; i < nums2.length; i++) {
            while (!queue.isEmpty() && queue.peek() < nums2[i]) {
                Integer beforeVal = queue.poll();
                num2CountMap.put(beforeVal, nums2[i]);
            }
            queue.add(nums2[i]);
        }
        int[] results = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            results[i] = num2CountMap.getOrDefault(nums1[i], -1);
        }
        return results;
    }
}