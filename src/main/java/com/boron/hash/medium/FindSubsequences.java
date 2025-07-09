package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 491. 非递减子序列 <a href="https://leetcode.cn/problems/non-decreasing-subsequences/solutions/387656/di-zeng-zi-xu-lie-by-leetcode-solution/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/9
 * </pre>
 */
public class FindSubsequences {

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
        List<List<Integer>> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {4,6,7,7}).build();
        Result result = Result.builder().results(List.of(List.of(4,6), List.of(4,6,7), List.of(4,6,7,7), List.of(4,7), List.of(4,7,7), List.of(6,7), List.of(6,7,7), List.of(7,7))).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {4,4,3,2,1}).build();
        Result result = Result.builder().results(List.of(List.of(4,4))).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<List<Integer>> actualResults = FindSubsequencesSolution.findSubsequences(param.getNums());
        List<List<Integer>> expectResults = result.getResults();
        boolean compareResult = Objects.equals(actualResults, expectResults);
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
//        test(generate2());
//        test(generate3());
    }
}

class FindSubsequencesSolution {

    /**
     * 使用字符串进行哈希
     * @param nums
     * @return
     */
    public static List<List<Integer>> findSubsequences(int[] nums) {
        List<Integer> prefix = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        Set<String> usedKeys = new HashSet<>();
        dfs(nums, 0, prefix, results, "", usedKeys);
        return results;
    }

    public static void dfs(int[] nums, int idx, List<Integer> prefix, List<List<Integer>> results, String prefixKey, Set<String> usedKeys) {
        if (prefix.size() >= 2) {
            results.add(new ArrayList<>(prefix));
        }
        for (int i = idx; i < nums.length; i++) {
            String freshKey = "".equals(prefixKey) ? prefixKey + nums[i] : prefixKey + "-" + nums[i];
            if (usedKeys.contains(freshKey)) {
                continue;
            }
            if (prefix.isEmpty() || prefix.get(prefix.size() - 1) <= nums[i]) {
                prefix.add(nums[i]);
                usedKeys.add(freshKey);
                dfs(nums, i + 1, prefix, results, freshKey, usedKeys);
                prefix.remove(prefix.size() - 1);
            }
        }
    }

    static class Node {
        private final Node[] children = new Node[201];
        public Node() {

        }

    }

    /**
     * 将子数组作为哈希使用
     * @param nums
     * @return
     */
    public static List<List<Integer>> findSubsequences1(int[] nums) {
        Node node = new Node();
        List<Integer> prefix = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        dfs1(nums, 0, prefix, results, node);
        return results;
    }

    public static void dfs1(int[] nums, int idx, List<Integer> prefix, List<List<Integer>> results, Node node) {
        if (prefix.size() >= 2) {
            results.add(new ArrayList<>(prefix));
        }
        for (int i = idx; i < nums.length; i++) {
            if (node.children[nums[i] + 100] != null) {
                continue;
            }
            if (prefix.isEmpty() || prefix.get(prefix.size() - 1) <= nums[i]) {
                prefix.add(nums[i]);
                Node tmpNode = new Node();
                node.children[nums[i] + 100] = tmpNode;
                dfs1(nums, i + 1, prefix, results, tmpNode);
                prefix.remove(prefix.size() - 1);
            }
        }
    }
}