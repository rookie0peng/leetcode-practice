package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 347. 前 K 个高频元素 <a href="https://leetcode.cn/problems/top-k-frequent-elements/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class TopKFrequent {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        int[] nums;
        int k;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int[] results;
    }
    
    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {1,1,1,2,2,3}).k(2).build();
        Result result = Result.builder().results(new int[] {1, 2}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {1}).k(1).build();
        Result result = Result.builder().results(new int[] {1}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {4,1,-1,2,-1,2,3}).k(2).build();
        Result result = Result.builder().results(new int[] {2,-1}).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int[] actualResults = TopKFrequentSolution.topKFrequent(param.getNums(), param.getK());
        int[] expectResults = result.getResults();
        Arrays.sort(actualResults);
        Arrays.sort(expectResults);
        boolean compareResult = Arrays.equals(actualResults, expectResults);
        System.out.println("actualResults vs expectResults");
//        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

class TopKFrequentSolution {


    public static int[] topKFrequent(int[] nums, int k) {
        // 统计数值对应的数量
        Map<Integer, Integer> countNumMap = new HashMap<>();
        for (int num : nums) {
            countNumMap.put(num, countNumMap.getOrDefault(num, 0) + 1);
        }
        // 优先级队列
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] m, int[] n) {
                return m[1] - n[1];
            }
        });
        // 遍历统计数字的map
        for (Map.Entry<Integer, Integer> entry : countNumMap.entrySet()) {
            if (queue.size() < k) {
                // 如果队列小于k，那么直接放入元素
                queue.offer(new int[] {entry.getKey(), entry.getValue()});
            } else {
                // 否则比较最小元素和当前元素的数量
               int[] element = queue.peek();
                // 如果小于当前元素，则，将队列中的元素出队，当前元素入队
                if (element[1] < entry.getValue()) {
                    queue.poll();
                    queue.offer(new int[] {entry.getKey(), entry.getValue()});
                }
            }
        }
        // 将队列的元素转为int数组返回
        int[] results = new int[k];
        for (int i = 0; i < k; ++i) {
            results[i] = queue.poll()[0];
        }
        return results;
    }

    static class Pair<L, R> {
        L l;
        R r;
        public static <L, R> Pair<L, R> of(L l, R r) {
            Pair<L, R> pair = new Pair<>();
            pair.l = l;
            pair.r = r;
            return pair;
        }
    }
}