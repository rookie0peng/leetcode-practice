package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

/**
 * <pre>
 *  @description: 142. 环形链表 II <a href="https://leetcode.cn/problems/linked-list-cycle-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/19
 * </pre>
 */
public class DetectCycle {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        DetectCycleSolution.ListNode head;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        DetectCycleSolution.ListNode result;
    }

    public static Pair<Param, Result> generate0() {
        DetectCycleSolution.ListNode node1 = new DetectCycleSolution.ListNode(3);
        DetectCycleSolution.ListNode node2 = new DetectCycleSolution.ListNode(2);
        DetectCycleSolution.ListNode node3 = new DetectCycleSolution.ListNode(0);
        DetectCycleSolution.ListNode node4 = new DetectCycleSolution.ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        Param param = Param.builder().head(node1).build();
        Result result = Result.builder().result(node2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        DetectCycleSolution.ListNode node1 = new DetectCycleSolution.ListNode(1);
        DetectCycleSolution.ListNode node2 = new DetectCycleSolution.ListNode(2);
        node1.next = node2;
        node2.next = node1;
        Param param = Param.builder().head(node1).build();
        Result result = Result.builder().result(node1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        DetectCycleSolution.ListNode node1 = new DetectCycleSolution.ListNode(1);
        Param param = Param.builder().head(node1).build();
        Result result = Result.builder().result(null).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        DetectCycleSolution.ListNode node1 = new DetectCycleSolution.ListNode(-1);
        DetectCycleSolution.ListNode node2 = new DetectCycleSolution.ListNode(-7);
        DetectCycleSolution.ListNode node3 = new DetectCycleSolution.ListNode(7);
        DetectCycleSolution.ListNode node4 = new DetectCycleSolution.ListNode(-4);
        DetectCycleSolution.ListNode node5 = new DetectCycleSolution.ListNode(19);
        DetectCycleSolution.ListNode node6 = new DetectCycleSolution.ListNode(6);
        DetectCycleSolution.ListNode node7 = new DetectCycleSolution.ListNode(-9);
        DetectCycleSolution.ListNode node8 = new DetectCycleSolution.ListNode(-5);
        DetectCycleSolution.ListNode node9 = new DetectCycleSolution.ListNode(-2);
        DetectCycleSolution.ListNode node10 = new DetectCycleSolution.ListNode(-5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;
        node8.next = node9;
        node9.next = node10;
        node10.next = node7;
        Param param = Param.builder().head(node1).build();
        Result result = Result.builder().result(node7).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        DetectCycleSolution.ListNode actualResult = DetectCycleSolution.detectCycle(param.getHead());
        DetectCycleSolution.ListNode expectResult = result.getResult();
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
        test(generate3());
    }
}

class DetectCycleSolution {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static ListNode detectCycle(ListNode head) {
        // 定义3个指针，快指针、慢指针、2度慢指针
        // 快指针和慢指针用来找环，找到环后用2度慢指针和慢指针来找到对应的入环点
        ListNode fast = head;
        ListNode slow = head;
        ListNode slow2 = head;
        // 相遇次数
        int meetNo = 0;
        // 是否找到环
        boolean foundCycle = false;
        // 进入循环
        while (slow != null && fast != null) {
            // 节点相等，则相遇次数+1
            if (fast == slow) {
                meetNo++;
            }
            // 快慢指针第二次遇见，代表着有环，跳出循环
            if (meetNo == 2) {
                foundCycle = true;
                break;
            }
            // 如果快指针的next为null，则说明没环，跳出循环
            if (fast.next == null) {
                break;
            }
            // 快指针一次移动两格，慢指针一次移动一格
            fast = fast.next.next;
            slow = slow.next;
        }
        // 从快指针和慢指针的相遇点开始，同时移动慢指针和2度慢指针，他们两最后会在入口点相遇
        // 原理：参考图A，（/resources/hash/medium/SearchCycleEnter.png）
        // 设链表中环外部分的长度为 a。slow 指针进入环后，又走了 b 的距离与 fast 相遇。
        // 此时，fast 指针已经走完了环的 n 圈，因此它走过的总距离为 a+n(b+c)+b=a+(n+1)b+nc。
        // 根据题意，任意时刻，fast 指针走过的距离都为 slow 指针的 2 倍。因此，我们有
        // a+(n+1)b+nc=2(a+b)⟹a=c+(n−1)(b+c)
        // 有了 a=c+(n−1)(b+c) 的等量关系，我们会发现：从相遇点到入环点的距离加上 n−1 圈的环长，恰好等于从链表头部到入环点的距离。
        // 因此，当发现 slow 与 fast 相遇时，我们再额外使用一个指针 ptr。起始，它指向链表头部；随后，它和 slow 每次向后移动一个位置。最终，它们会在入环点相遇。
        ListNode enter = null;
        // 如果找到环，则进入寻找入口点
        if (foundCycle) {
            while (true) {
                if (slow == slow2) {
                    enter = slow2;
                    break;
                }
                slow = slow.next;
                slow2 = slow2.next;
            }
        } else {
            // 没找到，则返回空
            return null;
        }
        return enter;
    }
}
