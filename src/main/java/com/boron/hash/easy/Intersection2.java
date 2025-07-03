package com.boron.hash.easy;

import java.util.Arrays;

/**
 * <pre>
 *  @description: 350. 两个数组的交集 II <a href="https://leetcode.cn/problems/intersection-of-two-arrays-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class Intersection2 {
}

class Intersection2Solution {

    /**
     * 哈希表
     * 进阶问题：
     * 如果给定的数组已经排好序呢？你将如何优化你的算法？使用双指针，同时遍历nums1和nums2，时间复杂度O(m+n)，空间复杂度O(1)
     * 如果 nums1 的大小比 nums2 小，哪种方法更优？如果nums1<<nums2，那么意味着排序nums2的开销很大，最好选择哈希表
     * 如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
     * 1.分块读取，先对nums1进行哈希，然后分块读取nums2，再进行比对。
     * 2.外部排序，再读取。
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        int[] dict = new int[1001];
        for (int num : nums1) {
            dict[num]++;
        }
        int[] res = new int[Math.min(nums1.length, nums2.length)];
        int idx = 0;
        for (int num : nums2) {
            if (dict[num] > 0) {
                res[idx++] = num;
                dict[num]--;
            }
        }
        return Arrays.copyOf(res, idx);
    }

}
