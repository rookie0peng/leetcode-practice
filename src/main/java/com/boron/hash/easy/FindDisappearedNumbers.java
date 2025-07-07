package com.boron.hash.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  @description: 448. 找到所有数组中消失的数字 <a href="https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/solutions/601946/zhao-dao-suo-you-shu-zu-zhong-xiao-shi-d-mabl/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/7
 * </pre>
 */
public class FindDisappearedNumbers {


}

class FindDisappearedNumbersSolution {

    /**
     * 原地哈希
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length;
        for (int num : nums) {
            int x = (num - 1) % n;
            // num 的范围是 1 ~ n，所以小于等于n的值都需要修改
            if (nums[x] <= n) {
                nums[x] += n;
            }
        }
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] <= n) {
                results.add(i + 1);
            }
        }
        return results;
    }

    /**
     * 原地交换
     */
    public List<Integer> findDisappearedNumbers1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] - 1 != i && nums[i] != nums[nums[i] - 1]) {
                int oldI = nums[i];
                nums[i] = nums[oldI - 1];
                nums[oldI - 1] = oldI;
            }
        }
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i + 1 != nums[i]) {
                results.add(i + 1);
            }
        }
        return results;
    }
}
