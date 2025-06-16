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

    public static TreeNode buildTree1(int[] preorder, int[] inorder) {
        // 中序遍历数组中元素对应的下标
        int len = preorder.length;
        // 先将中序遍历的下标和元素放入map，方便查找元素对应的下标(val -> index)
        for (int i = 0; i < len; i++) {
            DICT.put(inorder[i], i);
        }
        // 递归生成二叉树
        return recursion(preorder, inorder, 0, len - 1, 0, len - 1);
    }

    /**
     * 递归生成二叉树
     * @param preorder 先序遍历数组
     * @param inorder 中序遍历数组
     * @param pLeft 先序数组左边界
     * @param pRight 先序数组右边界
     * @param iLeft 中序数组左边界
     * @param iRight 中序数组右边界
     * @return 二叉树
     */
    private static TreeNode recursion(int[] preorder, int[] inorder, int pLeft, int pRight, int iLeft, int iRight) {
        // 如果左边界大于右边界，返回null
        if (pLeft > pRight) {
            return null;
        }
        // 先序遍历左边界即是根节点
        int pRoot = pLeft;
        // 获取根节点的值
        int rootVal = preorder[pRoot];
        // 获取根节点对应的中序遍历数组下标
        int iRoot = DICT.get(rootVal);
        // 构建根节点
        TreeNode treeNode = new TreeNode(rootVal);
        // 计算左子树的长度
        int leftTreeLen = iRoot - iLeft;
        // 递归构建左子树
        // 先序遍历数组没有明确的左右子树分割线，只能通过中序遍历数组计算得到的子树长度来计算。
        // 中序遍历数组的root坐标即是左右子树的分割线。
        treeNode.left = recursion(preorder, inorder, pLeft + 1, pLeft + leftTreeLen, iLeft, iRoot - 1);
        // 递归构建右子树
        treeNode.right = recursion(preorder, inorder, pLeft + leftTreeLen + 1, pRight, iRoot + 1, iRight);
        return treeNode;
    }

    /**
     * 迭代方式生成二叉树，很抽象
     * @param preorder 先序遍历数组
     * @param inorder 中序遍历数组
     * @return 节点
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
//        if (preorder == null || preorder.length == 0) {
//            return null;
//        }
        // 构建根节点
        TreeNode root = new TreeNode(preorder[0]);
        // 初始化暂存根节点的堆栈
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        // 中序遍历数组的下标
        int inorderIndex = 0;
        for (int i = 1; i < preorder.length; i++) {
            int preorderVal = preorder[i];
            TreeNode node = stack.peek();
            // 如果当前节点的值不等于中序遍历数组的元素，则将该元素作为左节点，并推入堆栈
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);
            } else {
                // 如果相等
                // 堆栈不为空，且栈顶的值与中序遍历的元素相等
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    // 弹出栈顶元素，中序遍历下标++
                    node = stack.pop();
                    inorderIndex++;
                }
                // 将当前元素作为作为最后一个节点的右子节点
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }
        return root;
    }
};
