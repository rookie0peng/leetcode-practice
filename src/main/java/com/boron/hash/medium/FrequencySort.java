package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * <pre>
 *  @description: 451. 根据字符出现频率排序 <a href="https://leetcode.cn/problems/sort-characters-by-frequency/submissions/642161273/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/8
 * </pre>
 */
public class FrequencySort {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("tree").build();
        Result result = Result.builder().result("eert").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = FrequencySortSolution.frequencySort(param.getS());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
    }
}

class FrequencySortSolution {

    public static String frequencySort(String s) {
        int[] charCountArr = new int[128];
        char[] sArr = s.toCharArray();
        for (char c : sArr) {
            charCountArr[c]++;
        }
        // int[] -> (count, char)
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o2[0], o1[0]);
            }
        });
        for (int i = 0; i < charCountArr.length; i++) {
            queue.add(new int[] {charCountArr[i], i});
        }
        StringBuilder sb = new StringBuilder(s.length());
        while (!queue.isEmpty()) {
            int[] element = queue.poll();
            for (int i = 0; i < element[0]; i++) {
                sb.append((char) element[1]);
            }
        }
        return sb.toString();
    }
}
