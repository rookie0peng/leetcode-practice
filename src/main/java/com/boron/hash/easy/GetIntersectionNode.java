package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  @description: 160. 相交链表 <a href="https://leetcode.cn/problems/intersection-of-two-linked-lists/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/22
 * </pre>
 */
public class GetIntersectionNode {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        GetIntersectionNodeSolution.ListNode headA;
        GetIntersectionNodeSolution.ListNode headB;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        GetIntersectionNodeSolution.ListNode result;
    }

    public static Pair<Param, Result> generate0() {
        GetIntersectionNodeSolution.ListNode node1 = new GetIntersectionNodeSolution.ListNode(1);
        GetIntersectionNodeSolution.ListNode node2 = new GetIntersectionNodeSolution.ListNode(9);
        GetIntersectionNodeSolution.ListNode node3 = new GetIntersectionNodeSolution.ListNode(1);
        GetIntersectionNodeSolution.ListNode node4 = new GetIntersectionNodeSolution.ListNode(2);
        GetIntersectionNodeSolution.ListNode node5 = new GetIntersectionNodeSolution.ListNode(4);
        GetIntersectionNodeSolution.ListNode node6 = new GetIntersectionNodeSolution.ListNode(3);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node6.next = node4;
        Param param = Param.builder().headA(node1).headB(node6).build();
        Result result = Result.builder().result(node4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        GetIntersectionNodeSolution.ListNode node1 = new GetIntersectionNodeSolution.ListNode(1);
        GetIntersectionNodeSolution.ListNode node2 = new GetIntersectionNodeSolution.ListNode(9);
        GetIntersectionNodeSolution.ListNode node3 = new GetIntersectionNodeSolution.ListNode(1);
        GetIntersectionNodeSolution.ListNode node4 = new GetIntersectionNodeSolution.ListNode(2);
        GetIntersectionNodeSolution.ListNode node5 = new GetIntersectionNodeSolution.ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node4.next = node5;
        Param param = Param.builder().headA(node1).headB(node4).build();
        Result result = Result.builder().result(null).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        GetIntersectionNodeSolution.ListNode actualResult = GetIntersectionNodeSolution.getIntersectionNode(param.getHeadA(), param.getHeadB());
        GetIntersectionNodeSolution.ListNode expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
    }
}

class GetIntersectionNodeSolution {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * 计算两个链表长度差，然后长链表先移动指定距离，第一次遇到相等的节点，就是相交点。
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 同时遍历headA和headB，计算两个链表的长度差，判断两个链表是否相交
        ListNode tmpA = headA, tmpB = headB, preA = headA, preB = headB;
        int aLen = 0, bLen = 0;
        while (true) {
            preA = tmpA == null ? preA : tmpA;
            preB = tmpB == null ? preB : tmpB;
            if (tmpA != null && tmpB != null) {
                tmpA = tmpA.next;
                tmpB = tmpB.next;
                aLen++;
                bLen++;
            } else if (tmpA != null) {
                tmpA = tmpA.next;
                aLen++;
            } else if (tmpB != null) {
                tmpB = tmpB.next;
                bLen++;
            } else {
                break;
            }
        }
        // 如果尾结点A和尾结点B不相等，意味着两个链表不相交
        if (preA != preB) {
            return null;
        }
        // 先移动长链表的引用一段距离
        ListNode detectA = headA, detectB = headB;
        if (aLen > bLen) {
            for (int i = 0; i < aLen - bLen; i++) {
                detectA = detectA.next;
            }
        } else {
            for (int i = 0; i < bLen - aLen; i++) {
                detectB = detectB.next;
            }
        }
        // 同时移动A/B两个链表，遇到第一个相等的节点就是相交节点
        ListNode intersectionNode = null;
        while (detectA != null) {
            if (detectA == detectB) {
                intersectionNode = detectA;
                break;
            }
            detectA = detectA.next;
            detectB = detectB.next;
        }
        return intersectionNode;
    }

    /**
     * 链表反转，不行
     */
    public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        ListNode reverseA = reverse(headA);
        ListNode reverseB = reverse(headB);
        if (reverseA != reverseB) {
            return null;
        }
        ListNode tmpA = reverseA, tmpB = reverseB, pre = reverseA;

        while (tmpA == tmpB) {
            pre = tmpA;
            tmpA = tmpA.next;
            tmpB = tmpB.next;
        }
        return pre;
    }

    /**
     * 链表反转，不行
     */
    private static ListNode reverse(ListNode head) {
        ListNode freshA = null;
        ListNode tmpA, nextA = head;
        while (nextA != null) {
            tmpA = nextA.next;
            nextA.next = freshA;
            freshA = nextA;
            nextA = tmpA;
        }
        return freshA;
    }

    /**
     * 哈希
     */
    public static ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        Set<ListNode> sets = new HashSet<>();
        ListNode tmpA = headA;
        while (tmpA != null) {
            sets.add(tmpA);
            tmpA = tmpA.next;
        }
        ListNode tmpB = headB;
        ListNode intersectionNode = null;
        while (tmpB != null) {
            if (sets.contains(tmpB)) {
                intersectionNode = tmpB;
                break;
            }
            tmpB = tmpB.next;
        }
        return intersectionNode;
    }
}
