package com.boron.hash.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 105. 从前序和中序遍历序列构造二叉树 <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/5
 * </pre>
 */
public class BuildTreeByPreIn {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] preorder;
        int[] inorder;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        BuildTreeByPreInSolution.TreeNode result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().preorder(new int[] {3, 9, 20, 15, 7}).inorder(new int[] {9,3,15,20,7}).build();
        BuildTreeByPreInSolution.TreeNode root = new BuildTreeByPreInSolution.TreeNode(3);
        BuildTreeByPreInSolution.TreeNode node1 = new BuildTreeByPreInSolution.TreeNode(9);
        BuildTreeByPreInSolution.TreeNode node2 = new BuildTreeByPreInSolution.TreeNode(20);
        BuildTreeByPreInSolution.TreeNode node3 = new BuildTreeByPreInSolution.TreeNode(15);
        BuildTreeByPreInSolution.TreeNode node4 = new BuildTreeByPreInSolution.TreeNode(7);
        root.left = node1;
        root.right = node2;
        node2.left = node3;
        node2.right = node4;
        Result result = Result.builder().result(root).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().preorder(new int[] {1, 2, 3}).inorder(new int[] {2, 3, 1}).build();
        BuildTreeByPreInSolution.TreeNode root = new BuildTreeByPreInSolution.TreeNode(1);
        BuildTreeByPreInSolution.TreeNode node1 = new BuildTreeByPreInSolution.TreeNode(2);
        BuildTreeByPreInSolution.TreeNode node2 = new BuildTreeByPreInSolution.TreeNode(3);
        root.left = node1;
        node1.right = node2;
        Result result = Result.builder().result(root).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().preorder(new int[] {3, 1, 2, 4}).inorder(new int[] {1, 2, 3, 4}).build();
        BuildTreeByPreInSolution.TreeNode root = new BuildTreeByPreInSolution.TreeNode(3);
        BuildTreeByPreInSolution.TreeNode node1 = new BuildTreeByPreInSolution.TreeNode(1);
        BuildTreeByPreInSolution.TreeNode node2 = new BuildTreeByPreInSolution.TreeNode(2);
        BuildTreeByPreInSolution.TreeNode node3 = new BuildTreeByPreInSolution.TreeNode(4);
        root.left = node1;
        node1.right = node2;
        root.right = node3;
        Result result = Result.builder().result(root).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        BuildTreeByPreInSolution.TreeNode actualResult = BuildTreeByPreInSolution.buildTree(param.getPreorder(), param.getInorder());
        BuildTreeByPreInSolution.TreeNode expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());
//        BuildTreeByPreInSolution.TreeNode root = new BuildTreeByPreInSolution.TreeNode(3);
//        BuildTreeByPreInSolution.TreeNode node1 = new BuildTreeByPreInSolution.TreeNode(9);
//        BuildTreeByPreInSolution.TreeNode node2 = new BuildTreeByPreInSolution.TreeNode(20);
//        root.left = node1;
//        root.right = node2;
//
//        BuildTreeByPreInSolution.TreeNode root1 = new BuildTreeByPreInSolution.TreeNode(3);
//        BuildTreeByPreInSolution.TreeNode node11 = new BuildTreeByPreInSolution.TreeNode(9);
//        BuildTreeByPreInSolution.TreeNode node21 = new BuildTreeByPreInSolution.TreeNode(20);
//        root1.left = node11;
//        root1.right = node21;
//        System.out.println(root1.equals(root));
    }


}

/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class BuildTreeByPreInSolution {

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

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        // 中序遍历数组中元素对应的下标
        int len = preorder.length;
//        PREORDER = preorder;
        for (int i = 0; i < len; i++) {
            DICT.put(inorder[i], i);
        }
        return recursion(preorder, inorder, 0, len - 1, 0, len - 1);
    }

    private static TreeNode recursion(int[] preorder, int[] inorder, int pLeft, int pRight, int iLeft, int iRight) {
        if (pLeft > pRight) {
            return null;
        }
        int pRoot = pLeft;
        int rootVal = preorder[pRoot];
        int iRoot = DICT.get(rootVal);
        TreeNode treeNode = new TreeNode(rootVal);
        int leftTreeLen = iRoot - iLeft;
        treeNode.left = recursion(preorder, inorder, pLeft + 1, pLeft + leftTreeLen, iLeft, iRoot - 1);
        treeNode.right = recursion(preorder, inorder, pLeft + leftTreeLen + 1, pRight, iRoot + 1, iRight);
        return treeNode;
    }

    public static TreeNode buildTree1(int[] preorder, int[] inorder) {
        // 先序遍历：根左右
        // 中序遍历：左根右
        int len = preorder.length;
        // 中序遍历数组中元素对应的下标
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            inorderMap.put(inorder[i], i);
        }

        TreeNode root = new TreeNode(preorder[0]);
        // 根结点也视为右节点
        Stack<TreeNode> parents = new Stack<>();
        parents.push(root);
        one: for (int i = 1; i < len; i++) {
            Integer nowIndex = inorderMap.get(preorder[i]);
            TreeNode nowNode = new TreeNode(preorder[i]);

            two: while (!parents.isEmpty()) {
                // 取出当前父节点
                TreeNode nowParent = parents.peek();
                // 判断该节点是否可以挂载到左子节点
                if (nowIndex < inorderMap.get(parents.peek().val)) {
                    nowParent.left = nowNode;
                    parents.push(nowNode);
                    break;
                }
                // 查找第一个作为左子树的父节点
                TreeNode ppNode;
                parents.pop();
                TreeNode parentNode = parents.isEmpty() ? null : parents.peek();
                while (parentNode != null) {
                    ppNode = parents.size() > 0 ? parents.peek() : null;
                    // 如果当前父节点是祖先节点的右子节点，继续向上查找
                    if (ppNode != null && ppNode.right == parentNode) {
                        parentNode = parents.pop();
                    } else {
                        // 父父节点等于null也认为是左子节点，如果是左子节点，则暂停
                        if (inorderMap.get(parentNode.val) > inorderMap.get(nowNode.val)) {
                            nowParent.right = nowNode;
                            parents.push(nowParent);
                            parents.push(nowNode);
                            break two;
                        } else {
                            nowParent = parentNode;
                            parentNode = parents.size() > 0 ? parents.pop() : null;
                        }
                    }
                }
                // 如果前面没有找到 作为左子树的父节点
                // 直接将当前节点挂载到当前父节点的右节点
                nowParent.right = nowNode;
                parents.push(nowNode);
                break ;
            }
        }
        return root;
    }
};
