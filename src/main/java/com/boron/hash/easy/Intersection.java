package com.boron.hash.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *  @description: 349. 两个数组的交集 <a href="https://leetcode.cn/problems/design-tic-tac-toe/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class Intersection {


}

class IntersectionSolution {

    public int[] intersection(int[] nums1, int[] nums2) {
        // 初始化字典
        int[] dict = new int[1001];
        // 在字典中标记该值
        for (int num : nums1) {
            dict[num] = 1;
        }
        // 初始化结果集数组1
        int[] results = new int[Math.min(nums1.length, nums2.length)];
        // 存储结果集时使用的下标
        int index = 0;
        // 遍历数组2
        for (int num : nums2) {
            // 如果字典中该数值的值为1，则说明存在，
            if (dict[num] == 1) {
                // 将该值添加到结果集，并且下标+1
                results[index++] = num;
                // 将dict中的该数值标记为0，避免重复统计
                dict[num] = 0;
            }
        }
        // 返回指定数组
        return Arrays.copyOf(results, index);
    }
}
