package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

/**
 * <pre>
 *  @description: 457. 环形数组是否存在循环 <a href="https://leetcode.cn/problems/circular-array-loop/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/8
 * </pre>
 */
public class CircularArrayLoop {

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
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {2,-1,1,2,2}).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {-1,-2,-3,-4,-5,6}).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {1,-1,5,1,4}).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().nums(new int[] {-1,-2,-3,-4,-5}).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = CircularArrayLoopSolution.circularArrayLoop(param.getNums());
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
        test(generate3());
    }
}

class CircularArrayLoopSolution {

    public static boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // 如果当前元素为 0 直接跳过
            if (nums[i] == 0) {
                continue;
            }
            // 初始化slow=i，fast = next(i)，相当于slow=0，fast=1
            // 为什么fast不从0开始，因为fast0等于slow0，没必要
            int slow = i, fast = next(nums, i);
            // 判断slow0是否与fast1符号相同，slow0是否与fast2符号相同
            while (nums[slow] * nums[fast] > 0 && nums[slow] * nums[next(nums, fast)] > 0) {
                if (slow == fast) {
                    // 如果当前慢指针不等于下一个慢指针，则返回true，排除单元素自循环
                    if (slow != next(nums, slow)) {
                        return true;
                    }
                    // 否则，属于单元素自循环
                    break;
                }
                // slow -> 1, fast -> 2
                slow = next(nums, slow);
                fast = next(nums, next(nums, fast));
            }
            // 将上次经过的元素全都设置为0
            slow = i;
            int tmpSlow;
            while (nums[slow] > 0) {
                tmpSlow = slow;
                slow = next(nums, slow);
                nums[tmpSlow] = 0;
            }
        }
        return false;
    }

    private static int next(int[] nums, int idx) {
        int n = nums.length;
        return (((idx + nums[idx]) % n) + n) % n;
    }

    /**
     * 快慢指针
     */
    public static boolean circularArrayLoop1(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int direction = toSign(nums[i]);
            int step1 = i, step2 = i;
            int preStep1, preStep2_1, preStep2_2;
            boolean start = false;
            while (true) {
                preStep1 = step1;
                preStep2_1 = step2;
                // step 1
                if (toSign(nums[preStep1]) * direction < 0) {
                    break;
                }
                step1 = nextStep(nums, n, preStep1);
                if (step1 == preStep1) {
                    break;
                }
                // step 2
                if (toSign(nums[preStep2_1]) * direction < 0) {
                    break;
                }
                preStep2_2 = nextStep(nums, n, preStep2_1);
                if (toSign(nums[step2]) * direction < 0) {
                    break;
                }
                step2 = nextStep(nums, n, preStep2_2);
                if (step1 == step2) {
                    if (start) {
                        return true;
                    }
                    start = true;
                }
            }
        }
        return false;
    }

    private static int toSign(int x) {
        return x > 0 ? 1 : -1;
    }

    private static int nextStep(int[] nums, int n, int step) {
        int nextMove = nums[step] % n;
        nextMove = nextMove < 0 ? nextMove + n : nextMove;
        int nextStep = step + nextMove;
        return nextStep >= n ? nextStep - n : nextStep;
    }

}
