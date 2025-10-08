package com.boron.str.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  @description: 257. 二叉树的所有路径 <a href="https://leetcode.cn/problems/binary-tree-paths/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/8
 * </pre>
 */
public class BinaryTreePaths {
}

class BinaryTreePathsSolution {

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

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> results = new ArrayList<>();
        dfs(root, "", results);
        return results;
    }

    private static void dfs(TreeNode node, String prefix, List<String> results) {
        if (node == null) {
            return;
        }
        StringBuilder pathSB = new StringBuilder(prefix);
        pathSB.append(node.val);
        if (node.left == null && node.right == null) {
            results.add(pathSB.toString());
        } else {
            pathSB.append("->");
            dfs(node.left, pathSB.toString(), results);
            dfs(node.right, pathSB.toString(), results);
        }
    }
}



/**
 * Definition for a binary tree node.
 *
 */
