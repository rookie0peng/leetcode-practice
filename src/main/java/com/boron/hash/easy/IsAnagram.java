package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

/**
 * <pre>
 *  @description: 242. 有效的字母异位词 <a href="https://leetcode.cn/problems/valid-anagram/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class IsAnagram {

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

    public static Pair<HasCycle.Param, HasCycle.Result> generate0() {
        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(3);
        HasCycleSolution.ListNode node2 = new HasCycleSolution.ListNode(2);
        HasCycleSolution.ListNode node3 = new HasCycleSolution.ListNode(0);
        HasCycleSolution.ListNode node4 = new HasCycleSolution.ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        HasCycle.Param param = HasCycle.Param.builder().head(node1).build();
        HasCycle.Result result = HasCycle.Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<HasCycle.Param, HasCycle.Result> generate1() {
        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(1);
        HasCycleSolution.ListNode node2 = new HasCycleSolution.ListNode(2);
        node1.next = node2;
        node2.next = node1;
        HasCycle.Param param = HasCycle.Param.builder().head(node1).build();
        HasCycle.Result result = HasCycle.Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<HasCycle.Param, HasCycle.Result> generate2() {
        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(1);
        HasCycle.Param param = HasCycle.Param.builder().head(node1).build();
        HasCycle.Result result = HasCycle.Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<HasCycle.Param, HasCycle.Result> testParam) {
        HasCycle.Param param = testParam.getKey();
        HasCycle.Result result = testParam.getValue();
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

class Solution {

    public boolean isAnagram(String s, String t) {
        // 如果长度不相等，则说明该两个字符串肯定不是字母异位词
        if (s.length() != t.length()) {
            return false;
        }
        // 建立数组映射表
        int[] mapArr1 = new int[128];
        // 将两个字符串转为字符数组
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        // 遍历字符串，在第一个映射表+1，第二个映射表-1
        for (int i = 0; i < s.length(); i++) {
            mapArr1[sArr[i]]++;
            mapArr1[tArr[i]]--;
        }
        // 遍历映射表，遇到不为0的数字，则认为两个字符串不是字母异位字符串
        for (int i = 'a'; i < 'z'; i++) {
            if (mapArr1[i] != 0) {
                return false;
            }
        }
        return true;
    }

}