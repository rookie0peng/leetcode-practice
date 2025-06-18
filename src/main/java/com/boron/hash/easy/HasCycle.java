package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  @description: 141. 环形链表 <a href="https://leetcode.cn/problems/linked-list-cycle/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/18
 * </pre>
 */
public class HasCycle {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        HasCycleSolution.ListNode head;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(3);
        HasCycleSolution.ListNode node2 = new HasCycleSolution.ListNode(2);
        HasCycleSolution.ListNode node3 = new HasCycleSolution.ListNode(0);
        HasCycleSolution.ListNode node4 = new HasCycleSolution.ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        Param param = Param.builder().head(node1).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(1);
        HasCycleSolution.ListNode node2 = new HasCycleSolution.ListNode(2);
        node1.next = node2;
        node2.next = node1;
        Param param = Param.builder().head(node1).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(1);
        Param param = Param.builder().head(node1).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = HasCycleSolution.hasCycle(param.getHead());
        boolean expectResult = result.isResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
    }
    
    
}

class HasCycleSolution {

    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * 快慢引用
     */
    public static boolean hasCycle(ListNode head) {
        // 空间复杂度为O(1)
        // 时间复杂度为O(2n)
        // 创建一个集合，用来存放遍历过的节点
        boolean foundCycle = false;
        // 快、慢两个引用，慢引用步长1，快引用步长2
        ListNode fast = head;
        ListNode slow = head;
        int equalNo = 0;
        // 同时遍历快慢两个引用
        while (slow != null && fast != null) {
            // 如果慢引用等于快引用，相遇次数+1
            if (slow == fast) {
                equalNo++;
            }
            // 如果相遇次数等于2，则说明找到环
            if (equalNo == 2) {
                foundCycle = true;
                break;
            }
            // 将引用切换到下一个节点
            slow = slow.next;
            // 快引用的下一个节点为空，则跳出循环
            if (fast.next == null) {
                break;
            }
            // 将快引用切换到下2个节点
            fast = fast.next.next;
        }
        return foundCycle;
    }

    /**
     * 哈希法
     */
    public static boolean hasCycle2(ListNode head) {
        // 空间复杂度为O(n)
        // 时间复杂度为O(n)
        // 创建一个集合，用来存放遍历过的节点
        Set<ListNode> exists = new HashSet<>();
        ListNode curNode = head;
        boolean foundCycle = false;
        // 遍历节点
        while (curNode != null) {
            // 如果该节点已经生成过，则说明有环存在，跳出循环
            if (exists.contains(curNode)) {
                foundCycle = true;
                break;
            }
            // 将节点添加到集合
            exists.add(curNode);
            // 将引用切换到下一个节点
            curNode = curNode.next;
        }
        return foundCycle;
    }
}