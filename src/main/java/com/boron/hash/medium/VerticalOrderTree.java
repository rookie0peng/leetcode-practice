package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 314. 二叉树的垂直遍历 <a href="https://leetcode.cn/problems/binary-tree-vertical-order-traversal/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/1
 * </pre>
 */
public class VerticalOrderTree {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        VerticalOrderTreeSolution.TreeNode root;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<List<Integer>> results;
    }

    public static Pair<Param, Result> generate0() {
        VerticalOrderTreeSolution.TreeNode node1 = new VerticalOrderTreeSolution.TreeNode(3);
        VerticalOrderTreeSolution.TreeNode node2 = new VerticalOrderTreeSolution.TreeNode(9);
        VerticalOrderTreeSolution.TreeNode node3 = new VerticalOrderTreeSolution.TreeNode(20);
        VerticalOrderTreeSolution.TreeNode node4 = new VerticalOrderTreeSolution.TreeNode(15);
        VerticalOrderTreeSolution.TreeNode node5 = new VerticalOrderTreeSolution.TreeNode(7);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
        Param param = Param.builder().root(node1).build();
        Result result = Result.builder().results(List.of(List.of(9), List.of(3, 15), List.of(20), List.of(7))).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        VerticalOrderTreeSolution.TreeNode node1 = new VerticalOrderTreeSolution.TreeNode(3);
        VerticalOrderTreeSolution.TreeNode node2 = new VerticalOrderTreeSolution.TreeNode(9);
        VerticalOrderTreeSolution.TreeNode node3 = new VerticalOrderTreeSolution.TreeNode(8);
        VerticalOrderTreeSolution.TreeNode node4 = new VerticalOrderTreeSolution.TreeNode(4);
        VerticalOrderTreeSolution.TreeNode node5 = new VerticalOrderTreeSolution.TreeNode(0);
        VerticalOrderTreeSolution.TreeNode node6 = new VerticalOrderTreeSolution.TreeNode(1);
        VerticalOrderTreeSolution.TreeNode node7 = new VerticalOrderTreeSolution.TreeNode(7);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        Param param = Param.builder().root(node1).build();
        Result result = Result.builder().results(List.of(List.of(4), List.of(9), List.of(3, 0, 1), List.of(8), List.of(7))).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<List<Integer>> actualResults = VerticalOrderTreeSolution.verticalOrder(param.getRoot());
        List<List<Integer>> expectResults = result.getResults();
        boolean compareResult = actualResults.equals(expectResults);
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

//        test(generate0());
        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}


class VerticalOrderTreeSolution {

    public static class TreeNode {
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

    static class Pair<L, R> {
        L l;
        R r;
        public static <L, R> Pair<L, R> of(L l, R r) {
            Pair<L, R> pair = new Pair<>();
            pair.l = l;
            pair.r = r;
            return pair;
        }
    }

    /**
     * 广度优先遍历
     */
    public static List<List<Integer>> verticalOrder(TreeNode root) {
        Queue<List<Pair<Integer, TreeNode>>> queue = new LinkedList<>();
        queue.offer(List.of(Pair.of(0, root)));
        Map<Integer, List<Integer>> index2ValueMap = new HashMap<>();
        while (!queue.isEmpty()) {
            List<Pair<Integer, TreeNode>> pairs = queue.poll();
            List<Pair<Integer, TreeNode>> nextPairs = new ArrayList<>();
            for (Pair<Integer, TreeNode> pair : pairs) {
                Integer col = pair.l;
                TreeNode node = pair.r;
                if (node == null) {
                    continue;
                }
                List<Integer> values = index2ValueMap.computeIfAbsent(col, k -> new ArrayList<>());
                values.add(node.val);
                nextPairs.add(Pair.of(col - 1, node.left));
                nextPairs.add(Pair.of(col + 1, node.right));
            }
//            row++;
            if (!nextPairs.isEmpty()) {
                queue.offer(nextPairs);
            }

        }
        List<List<Integer>> results = index2ValueMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue).toList();
        return results;
    }

}
