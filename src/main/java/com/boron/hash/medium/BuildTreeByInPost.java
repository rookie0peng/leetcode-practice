package com.boron.hash.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  @description: 106. 从中序与后序遍历序列构造二叉树 <a href="https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/17
 * </pre>
 */
public class BuildTreeByInPost {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] inorder;
        int[] postorder;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        BuildTreeByInPostSolution.TreeNode result;
    }

    public static Pair<BuildTreeByInPost.Param, BuildTreeByInPost.Result> generate0() {
        BuildTreeByInPost.Param param = BuildTreeByInPost.Param.builder().inorder(new int[] {9,3,15,20,7}).postorder(new int[] {9,15,7,20,3}).build();
        BuildTreeByInPostSolution.TreeNode node1 = new BuildTreeByInPostSolution.TreeNode(9);
        BuildTreeByInPostSolution.TreeNode node2 = new BuildTreeByInPostSolution.TreeNode(3);
        BuildTreeByInPostSolution.TreeNode node3 = new BuildTreeByInPostSolution.TreeNode(15);
        BuildTreeByInPostSolution.TreeNode node4 = new BuildTreeByInPostSolution.TreeNode(20);
        BuildTreeByInPostSolution.TreeNode node5 = new BuildTreeByInPostSolution.TreeNode(7);
        node2.left = node1;
        node2.right = node4;
        node4.left = node3;
        node4.right = node5;
        BuildTreeByInPost.Result result = BuildTreeByInPost.Result.builder().result(node2).build();
        return Pair.of(param, result);
    }

    public static Pair<BuildTreeByInPost.Param, BuildTreeByInPost.Result> generate1() {
        BuildTreeByInPost.Param param = BuildTreeByInPost.Param.builder().inorder(new int[] {-1}).postorder(new int[] {-1}).build();
        BuildTreeByInPostSolution.TreeNode node1 = new BuildTreeByInPostSolution.TreeNode(-1);
        BuildTreeByInPost.Result result = BuildTreeByInPost.Result.builder().result(node1).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<BuildTreeByInPost.Param, BuildTreeByInPost.Result> testParam) {
        BuildTreeByInPost.Param param = testParam.getKey();
        BuildTreeByInPost.Result result = testParam.getValue();
        BuildTreeByInPostSolution.TreeNode actualResult = BuildTreeByInPostSolution.buildTree(param.getInorder(), param.getPostorder());
        BuildTreeByInPostSolution.TreeNode expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
//        BuildTreeByInPostSolution.TreeNode root = new BuildTreeByInPostSolution.TreeNode(3);
//        BuildTreeByInPostSolution.TreeNode node1 = new BuildTreeByInPostSolution.TreeNode(9);
//        BuildTreeByInPostSolution.TreeNode node2 = new BuildTreeByInPostSolution.TreeNode(20);
//        root.left = node1;
//        root.right = node2;
//
//        BuildTreeByInPostSolution.TreeNode root1 = new BuildTreeByInPostSolution.TreeNode(3);
//        BuildTreeByInPostSolution.TreeNode node11 = new BuildTreeByInPostSolution.TreeNode(9);
//        BuildTreeByInPostSolution.TreeNode node21 = new BuildTreeByInPostSolution.TreeNode(20);
//        root1.left = node11;
//        root1.right = node21;
//        System.out.println(root1.equals(root));
    }
    
}

class BuildTreeByInPostSolution {

    @Data
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

    static Map<Integer, Integer> DICT = new HashMap<>();

    public static TreeNode buildTree(int[] inorder, int[] postorder) {

        int len = inorder.length;
        // 根据中序遍历数组生成映射表，(val -> index)
        for (int i = 0; i < inorder.length; i++) {
            DICT.put(inorder[i], i);
        }
        // 递归生成树
        return recursion(inorder, postorder, 0, len - 1, 0, len - 1);
    }

    private static TreeNode recursion(int[] inorder, int[] postorder, int iLeft, int iRight, int pLeft, int pRight) {
        // 如果后续遍历的左边界大于右边界，则返回
        if (pLeft > pRight) {
            return null;
        }
        // 根据后序遍历设定，右边界元素就是根节点
        int rootVal = postorder[pRight];
        // 构建根节点
        TreeNode node = new TreeNode(rootVal);
        // 获取根节点在中序遍历数组中的下标
        int iRoot = DICT.get(rootVal);
        // 根据右边界生成右子树的长度
        int rightTreeLen = iRight - iRoot;
        // 递归生成左子树，通过右子树的长度来生成左子树的左右边界
        node.left = recursion(inorder, postorder, iLeft, iRoot - 1, pLeft, pRight - rightTreeLen - 1);
        // 递归生成右子树
        node.right = recursion(inorder, postorder, iRoot + 1, iRight, pRight - rightTreeLen, pRight - 1);
        return node;
    }
}