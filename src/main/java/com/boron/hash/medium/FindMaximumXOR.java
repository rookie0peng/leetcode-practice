package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  @description: 421. 数组中两个数的最大异或值 <a href="https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/5
 * </pre>
 */
public class FindMaximumXOR {

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

    public static int maxPowerOfTwo(int n) {
        if (n <= 0) return -1; // 处理非正数情况
        return Integer.numberOfTrailingZeros(Integer.highestOneBit(n));
    }

    public static int maxPowerOfTwo2(int n) {
        if (n <= 0) return -1; // 处理非正数情况

        int power = 0;
        while (n > 1) {
            n >>= 1;
            power++;
        }
        return power;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {3,10,5,25,2,8}).build();
        Result result = Result.builder().result(28).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = FindMaximumXORSolution.findMaximumXOR(param.getNums());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
    }

}

class FindMaximumXORSolution {

    public static int findMaximumXOR(int[] nums) {
        // 获取最大值
        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }
        // 获取最大值对应的是2的多少次幂
        int highBit = 31 - Integer.numberOfLeadingZeros(max);
        // 结果、掩码
        int ans = 0, mask = 0;
        // 存储每次 异或 结果
        Set<Integer> seen = new HashSet<>();
        // 从最高位开始枚举
        for (int i = highBit; i >= 0; i--) {
            // 重置 异或 结果集
            seen.clear();
            // 1000 -> 1100 -> 1110 -> 1111
            mask |= 1 << i;
            // 判断是否能到达下一个结果，例如当前 1000，则下一个结果1100
            int newAns = ans | (1 << i);
            // 遍历所有数字
            for (int x : nums) {
                // 低于 i 的比特位置为 0，排除影响
                x &= mask;
                // 如果seen包含 新结果和x的异或 值，则说明数组中存在两个数字，可以做到这两个数字的异或值等于新结果
                // 原本应该先把所有x添加进seen，再去判断seen中是否包含x^newAns
                // 但是考虑到异或满足交换律，所以我们可以一边添加，一边判断，如果有满足条件的值，一定不会漏
                if (seen.contains(newAns ^ x)) {
                    // 如果满足条件，即存在两个数字的异或值等于新结果，将下一个结果赋值给ans，进入下一轮循环（判断下一个比特位是否能达到）
                    ans = newAns;
                    break;
                }
                // 将x添加进seen集合
                seen.add(x);
            }
        }
        return ans;
    }


}
