package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 358. K 距离间隔重排字符串 <a href="https://leetcode.cn/problems/rearrange-string-k-distance-apart/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class RearrangeString {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
        int k;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("aabbcc").k(3).build();
        Result result = Result.builder().result("abcabc").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("aaabc").k(3).build();
        Result result = Result.builder().result("").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("aaadbbcc").k(2).build();
        Result result = Result.builder().result("abacabcd").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("a").k(0).build();
        Result result = Result.builder().result("a").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = RearrangeStringSolution.rearrangeString(param.getS(), param.getK());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
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
//        test(generate4());
    }
}

class RearrangeStringSolution {

    public static String rearrangeString(String s, int k) {
        // 统计每个字符对应的数量
        int[] char2CountArr = new int[26];
        for (char c : s.toCharArray()) {
            char2CountArr[c - 'a']++;
        }
        // 记录字符下一个可以放置的位置
        int[] nextPlace = new int[26];
        int n = s.length();
        // 结果集
        StringBuilder sb = new StringBuilder();
        // 遍历字符串长度
        for (int i = 0; i < n; i++) {
            // 记录剩余字符中的最大数量
            int max = -1;
            // 记录剩余字符中的最大数量的字符
            int maxC = -1;
            // 遍历26个字母
            for (int j = 0; j < 26; j++) {
                // 如果字符对应的数量大于0，且当前位置i大于等于下一个可以放置的位置
                if (char2CountArr[j] > 0 && i >= nextPlace[j]) {
                    // 记录最大数量字符、最大数量
                    if (char2CountArr[j] > max) {
                        max = char2CountArr[j];
                        maxC = j;
                    }
                }
            }
            // 如果最大数量字符是-1，则认为没有找到可放置字符，返回空字符串
            if (maxC == -1) {
                return "";
            }
            // 将当前字符加入结果集
            sb.append((char) (maxC + 'a'));
            // 该字符的数量扣减
            char2CountArr[maxC]--;
            // 记录下一个可以放置该字符的位置
            nextPlace[maxC] = i + k;
        }
        return sb.toString();
    }

    /**
     * 最大堆
     * 维护一个优先级列，每次弹出队列中数量最大的字母
     */
    public static String rearrangeString1(String s, int k) {
        // 如果k等于0，直接返回原字符串
        if (k == 0) {
            return s;
        }
        // 统计每个字符对应的数量
        Map<Character, Integer> char2CountMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char2CountMap.put(s.charAt(i), char2CountMap.getOrDefault(s.charAt(i), 0) + 1);
        }
        // int[] -> {char, count}，倒序排序，先弹出数量多的
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt((int[] o) -> o[1]).reversed());
        for (Map.Entry<Character, Integer> entry : char2CountMap.entrySet()) {
            queue.add(new int[]{entry.getKey(), entry.getValue()});
        }
        // 队列2，用于放置暂时不允许加入结果集的字符
        Queue<int[]> queue2 = new LinkedList<>();
        // 统计每个字符还需间隔多少次移动才能放入结果集
        int[] charSpaceArr = new int[26];
        // 结果集
        StringBuffer sb = new StringBuffer();
        while (!queue.isEmpty()) {
            // 扣减每个字符的间隔数量
            reduceCount(charSpaceArr);
            // 弹出元素
            int[] element = queue.poll();
            // 将queue的队首元素加入结果集
            sb.append((char) element[0]);
            // 数量扣减
            if (--element[1] > 0) {
                // 距离下一次放入还剩k的长度
                charSpaceArr[element[0] - 'a'] = k;
                // 加入queue2，方便后续使用
                queue2.offer(element);
            }
            // 如果队列2不为空
            if (!queue2.isEmpty()) {
                // 获取第一个元素，判断其是否到达可以弹出，当计数等于1时，可以弹出
                int[] peek = queue2.peek();
                if (charSpaceArr[peek[0] - 'a'] == 1) {
                    queue.offer(queue2.poll());
                }
            }
        }
        // 如果队列2清空，则认为可以做到；否则反之
        return queue2.isEmpty() ? sb.toString() : "";
    }

    private static void reduceCount(int[] charSpaceArr) {
        for (int i = 0; i < charSpaceArr.length; i++) {
            if (charSpaceArr[i] > 0) {
                charSpaceArr[i]--;
            }
        }
    }
}
