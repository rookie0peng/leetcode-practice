package com.boron.hash.medium;

/**
 * <pre>
 *  @description: 340. 至多包含 K 个不同字符的最长子串 <a href="https://leetcode.cn/problems/longest-substring-with-at-most-k-distinct-characters/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class LongestSubstringKDistinct {


}

class LongestSubstringKDistinctSolution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // 如果k等于0，直接返回0
        if (k == 0)
            return 0;
        // 左端点
        int left = 0;
        // 右端点
        int right = 0;
        // 统计左端点到右端点间的字符数量
        int count = 0;
        // 用数组作为map使用
        int[] charCount = new int[300];
        int maxLen = 0;
        // 左端点和右端点都要小于s的长度
        while (left < s.length() && right < s.length()) {
            // 取出右端点的字符
            char cr = s.charAt(right);
            // 如果右端点字符的数量为0，则count++
            if (charCount[cr] == 0) {
                count++;
            }
            // 字符对应数量也增加1
            charCount[cr] += 1;
            // 如果字符种类大于k，则需要向右移动左端点
            while (count > k) {
                // 取出左端点的字符
                char cl = s.charAt(left);
                // 字符数量-1
                charCount[cl]--;
                // 如果字符数量等于0
                if (charCount[cl] == 0) {
                    // 字符种类-1
                    count--;
                }
                // 向右移动左端点
                left++;
            }
            // 计算最大长度
            maxLen = Math.max(maxLen, right - left + 1);
            // 向右移动右端点
            right++;
        }
        return maxLen;
    }
}