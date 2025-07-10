package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *  @description: 508. 出现次数最多的子树元素和 <a href="https://leetcode.cn/problems/most-frequent-subtree-sum/submissions/642788582/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/10
 * </pre>
 */
public class FindFrequentTreeSum {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        FindFrequentTreeSumSolution.TreeNode root;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int[] results;
    }

    public static Pair<Param, Result> generate0() {
        FindFrequentTreeSumSolution.TreeNode node1 = new FindFrequentTreeSumSolution.TreeNode(5);
        FindFrequentTreeSumSolution.TreeNode node2 = new FindFrequentTreeSumSolution.TreeNode(2);
        FindFrequentTreeSumSolution.TreeNode node3 = new FindFrequentTreeSumSolution.TreeNode(-3);
        node1.left = node2;
        node1.right = node3;
        Param param = Param.builder().root(node1).build();
        Result result = Result.builder().results(new int[] {2,-3,4}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        FindFrequentTreeSumSolution.TreeNode node1 = new FindFrequentTreeSumSolution.TreeNode(5);
        FindFrequentTreeSumSolution.TreeNode node2 = new FindFrequentTreeSumSolution.TreeNode(2);
        FindFrequentTreeSumSolution.TreeNode node3 = new FindFrequentTreeSumSolution.TreeNode(-5);
        node1.left = node2;
        node1.right = node3;
        Param param = Param.builder().root(node1).build();
        Result result = Result.builder().results(new int[] {2}).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int[] actualResults = FindFrequentTreeSumSolution.findFrequentTreeSum(param.getRoot());
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

class FindFrequentTreeSumSolution {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static int[] findFrequentTreeSum(TreeNode root) {
        // map<num -> count>
        Map<Integer, Integer> num2CountMap = new HashMap<>();
        // max: num -> count
        AtomicReference<int[]> maxCountNum = new AtomicReference<>(new int[2]);
        // 存储结果集
        Set<Integer> results = new HashSet<>();
        // 深度优先遍历
        dfs(root, num2CountMap, maxCountNum, results);
        // 将结果集转为数组返回
        return results.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int dfs(TreeNode node, Map<Integer, Integer> num2CountMap, AtomicReference<int[]> maxCountNum, Set<Integer> results) {
        // 如果节点为null，返回0
        if (node == null) {
            return 0;
        }
        // 获取当前结果
        int val = node.val;
        // 深度优先遍历左子树+右子树
        int sum = val + dfs(node.left, num2CountMap, maxCountNum, results) + dfs(node.right, num2CountMap, maxCountNum, results);
        // 获取该结果在map中的数量
        Integer preCount = num2CountMap.getOrDefault(sum, 0);
        // 数量+1
        num2CountMap.put(sum, preCount + 1);
        // 如果当前数量等于最大数量，将当前sum添加进集合
        if (maxCountNum.get()[1] == preCount + 1) {
            results.add(sum);
        } else if (maxCountNum.get()[1] < preCount + 1) {
            // 如果当前数量大于最大数量，则重新赋值最大数量和num
            maxCountNum.get()[0] = sum;
            maxCountNum.get()[1] = preCount + 1;
            // 清空旧结果集
            // 添加sum
            results.clear();
            results.add(sum);
        }
        return sum;
    }
}
