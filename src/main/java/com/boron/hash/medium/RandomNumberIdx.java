package com.boron.hash.medium;

import java.util.*;

/**
 * <pre>
 *  @description: 398. 随机数索引 <a href="https://leetcode.cn/problems/random-pick-index/submissions/641438218/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/5
 * </pre>
 */
public class RandomNumberIdx {


}


class RandomNumberIdxSolution {

    private final Map<Integer, List<Integer>> numIdxMap;
    private final Random random = new Random();

    public RandomNumberIdxSolution(int[] nums) {
        // 新建一个map(num -> indices)
        numIdxMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> indices = numIdxMap.computeIfAbsent(nums[i], k -> new ArrayList<>());
            indices.add(i);
        }
    }

    public int pick(int target) {
        // 从map中获取对应的索引
        List<Integer> indices = numIdxMap.get(target);
        // 获取随机数
        int nextInt = random.nextInt(indices.size());
        return indices.get(nextInt);
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
 */