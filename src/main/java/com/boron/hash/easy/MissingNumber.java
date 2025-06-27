package com.boron.hash.easy;

/**
 * <pre>
 *  @description: 268. 丢失的数字 <a href="https://leetcode.cn/problems/missing-number/submissions/639678477/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/28
 * </pre>
 */
public class MissingNumber {


}

class MissingNumberSolution {

    /**
     * 异或运算
     * 因为异或满足交换律和结合律，我们只需要把 0~n-1 的数组元素 和 0~n 的数字 进行异或运算，最终的结果就是缺失的数字
     */
    public int missingNumber(int[] nums) {
        int xor = 0;
        // 异或 0~n-1 的数组元素
        for (int num : nums) {
            xor ^= num;
        }
        // 异或 0~n
        for (int i = 0; i <= nums.length; i++) {
            xor ^= i;
        }
        return xor;
    }

    /**
     * 原地哈希
     */
    public int missingNumber1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i && nums[i] < nums.length && nums[nums[i]] != nums[i] ) {
                int old = nums[i];
                nums[i] = nums[old];
                nums[old] = old;
            }
        }
        int result = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                result = i;
                break;
            }
        }
        return result;
    }
}
