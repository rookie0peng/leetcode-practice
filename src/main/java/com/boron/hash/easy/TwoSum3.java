package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 170. 两数之和 III - 数据结构设计 <a href="https://leetcode.cn/problems/two-sum-iii-data-structure-design/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/24
 * </pre>
 */
public class TwoSum3 {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
//        HasCycleSolution.ListNode node1 = new HasCycleSolution.ListNode(1);
        Param param = Param.builder().nums(new int[] {3,2,3}).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = MajorityElementSolution.majorityElement(param.getNums());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void test1() {
        TwoSumSolution twoSumSolution = new TwoSumSolution();
        twoSumSolution.add(5);
        twoSumSolution.add(3);
        twoSumSolution.add(1);
        System.out.println(twoSumSolution.find(4));
        System.out.println(twoSumSolution.find(7));
    }

    public static void test2() {
        TwoSumSolution twoSumSolution = new TwoSumSolution();
        twoSumSolution.add(3);
        twoSumSolution.add(2);
        twoSumSolution.add(1);
        System.out.println(twoSumSolution.find(2));
        System.out.println(twoSumSolution.find(3));
        System.out.println(twoSumSolution.find(4));
        System.out.println(twoSumSolution.find(5));
        System.out.println(twoSumSolution.find(6));
    }

    public static void main(String[] args) {
//        test(generate0());
//        test1();
        test2();
//        test(generate2());
    }
}

/**
 * 排序+双指针，双指针从两端向中间靠拢
 */
class TwoSumSolution {

    private ArrayList<Integer> nums;
    private boolean is_sorted;

    /** 在这里初始化你的数据结构。 */
    public TwoSumSolution() {
        this.nums = new ArrayList<Integer>();
        this.is_sorted = false;
    }

    /** 将数字添加到内部数据结构中。 */
    public void add(int number) {
        this.nums.add(number);
        this.is_sorted = false;
    }

    /** 查看是否存在任何一对数字，其总和等于该值。 */
    public boolean find(int value) {
        if (!this.is_sorted) {
            Collections.sort(this.nums);
            this.is_sorted = true;
        }
        int low = 0, high = this.nums.size() - 1;
        while (low < high) {
            int twosum = this.nums.get(low) + this.nums.get(high);
            if (twosum < value)
                low += 1;
            else if (twosum > value)
                high -= 1;
            else
                return true;
        }
        return false;
    }


}

/**
 * 哈希
 */
class TwoSumSolution1 {

    private final List<Integer> nums = new ArrayList<>();

    private final Map<Integer, Integer> map = new HashMap<>();

    public TwoSumSolution1() {
    }

    public void add(int number) {
        Integer count = map.getOrDefault(number, 0);
        map.put(number, count + 1);
    }

    public boolean find(int value) {
        int nextValue;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            nextValue = value - key;
            if (map.containsKey(nextValue) && (nextValue != key || entry.getValue() >= 2)) {
                return true;
            }
        }
        return false;
    }
}