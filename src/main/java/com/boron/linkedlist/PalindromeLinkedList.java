package com.boron.linkedlist;

/**
 * <pre>
 *  @description: 234. 回文链表 <a href="https://leetcode.cn/problems/palindrome-linked-list/?envType=study-plan-v2&envId=top-100-liked"></a>
 *  @author: BruceBoron
 *  @date: 2025/6/5
 * </pre>
 */
public class PalindromeLinkedList {

    public static ListNode generate0() {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(1);

        one.next = two;
        two.next = three;
        return one;
    }

    public static ListNode generate1() {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(2);
        ListNode node4 = new ListNode(1);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        return node1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        boolean test = solution.isPalindrome(generate0());
        System.out.println("result: " + test);
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        // 遍历n/2长度，找到尾节点与中间节点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 反转后半部分链表
        ListNode prev = slow;
        ListNode next = null;
        ListNode current = slow != null ? slow.next : null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        // 对比链表
        ListNode first = head;
        ListNode second = prev;
        while (first.val == second.val) {
            // 奇数链表 || 偶数链表
            if (first == second || first.next == slow) {
                return true;
            }
            first = first.next;
            second = second.next;
        }
        return false;
    }
}

