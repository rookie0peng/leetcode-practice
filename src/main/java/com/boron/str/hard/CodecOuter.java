package com.boron.str.hard;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * <pre>
 *  @description: 297. 二叉树的序列化与反序列化 <a href="https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/14
 * </pre>
 */
public class CodecOuter {

    private static void test1() {
        // 1,2,3,null,null,4,5
        Codec.TreeNode node1 = new Codec.TreeNode(1);
        Codec.TreeNode node2 = new Codec.TreeNode(2);
        Codec.TreeNode node3 = new Codec.TreeNode(3);
        Codec.TreeNode node4 = new Codec.TreeNode(4);
        Codec.TreeNode node5 = new Codec.TreeNode(5);

        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;

        String serialize = new Codec().serialize(node1);
        Codec.TreeNode node11 = new Codec().deserialize(serialize);
        System.out.println("over");
    }


    private static void test2() {
        // 1,2,3,null,null,4,5
//        Codec.TreeNode node1 = new Codec.TreeNode(1);
//        Codec.TreeNode node2 = new Codec.TreeNode(2);
//        Codec.TreeNode node3 = new Codec.TreeNode(3);
//        Codec.TreeNode node4 = new Codec.TreeNode(4);
//        Codec.TreeNode node5 = new Codec.TreeNode(5);
//
//        node1.left = node2;
//        node1.right = node3;
//        node3.left = node4;
//        node3.right = node5;

        Codec.TreeNode node1 = null;

        String serialize = new Codec().serialize(null);
        Codec.TreeNode node11 = new Codec().deserialize(serialize);
        System.out.printf("node1==node11 ? %s\n", Objects.equals(node1, node11));
    }

    private static void test3() {
        // 1,2,3,null,null,4,5
        Codec.TreeNode node1 = new Codec.TreeNode(1);

        String serialize = new Codec().serialize(node1);
        Codec.TreeNode node11 = new Codec().deserialize(serialize);
        System.out.printf("node1==node11 ? %s\n", Objects.equals(node1, node11));
    }


    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Codec {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNode treeNode = (TreeNode) o;
            return val == treeNode.val && Objects.equals(left, treeNode.left) && Objects.equals(right, treeNode.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, left, right);
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        dfsSerialize(root, result);
        return result.toString();
    }

    private void dfsSerialize(TreeNode node, StringBuilder result) {
        if (node == null) {
            result.append(',');
            result.append('x');
            return;
        }
        if (!result.isEmpty()) {
            result.append(',');
        }
        result.append(node.val);
        dfsSerialize(node.left, result);
        dfsSerialize(node.right, result);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        int i = 0;
        while (i < data.length() && data.charAt(i) != ',') {
            i++;
        }
        int rootVal = Integer.parseInt(data.substring(0, i));
        TreeNode root = new TreeNode(rootVal);
        Deque<TreeNode> parentQueue = new LinkedList<>();
        parentQueue.push(root);
        dfsDeserialize(i + 1, data, parentQueue, root, true);
        return root;
    }

    private void dfsDeserialize(int idx, String data, Deque<TreeNode> parentQueue, TreeNode parent, boolean setLeft) {
        if (idx >= data.length()) {
            return;
        }
        int i = idx;
        while (i < data.length() && data.charAt(i) != ',') {
            i++;
        }
        String str = data.substring(idx, i);
        if (str.equals("x")) {
            if (setLeft) {
                parentQueue.poll();
                dfsDeserialize(i + 1, data, parentQueue, parent, false);
            } else {
                if (!parentQueue.isEmpty()) {
                    dfsDeserialize(i + 1, data, parentQueue, parentQueue.poll(), false);
                }
            }
        } else {
            TreeNode treeNode = new TreeNode(Integer.parseInt(str));
            parentQueue.push(treeNode);
            if (setLeft) {
                parent.left = treeNode;
            } else {
                parent.right = treeNode;
            }
            dfsDeserialize(i + 1, data, parentQueue, treeNode, true);
        }
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
