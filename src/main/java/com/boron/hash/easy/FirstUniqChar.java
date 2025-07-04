package com.boron.hash.easy;

/**
 * <pre>
 *  @description: 387. 字符串中的第一个唯一字符 <a href="https://leetcode.cn/problems/first-unique-character-in-a-string/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class FirstUniqChar {

}

class FirstUniqCharSolution {
    public int firstUniqChar(String s) {
        // 统计每个字符的数量
        int[] charCountArr = new int[26];
        // 记录每个字符第一次出现的位置
        int[] charFirstIdxArr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (charCountArr[c - 'a'] == 0) {
                charFirstIdxArr[c - 'a'] = i;
            }
            charCountArr[c - 'a']++;
        }
        // 遍历26个字母，如果该字符的数量为1，则记录一次最小值
        int minIdx = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            if (charCountArr[i] == 1) {
                minIdx = Math.min(charFirstIdxArr[i], minIdx);
            }
        }
        return minIdx == Integer.MAX_VALUE ? -1 : minIdx;
    }
}
