package com.boron.hash.easy;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  @description: 217. 存在重复元素 <a href="https://leetcode.cn/problems/contains-duplicate/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class ContainsDuplicate {


}


class ContainsDuplicateSolution {

    /**
     * 哈希
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> dict = new HashSet<>(nums.length);
        for (int num : nums) {
            if (dict.contains(num)) {
                return true;
            }
            dict.add(num);
        }
        return false;
    }
}