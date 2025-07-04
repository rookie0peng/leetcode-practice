package com.boron.hash.easy;

/**
 * <pre>
 *  @description: 389. 找不同 <a href="https://leetcode.cn/problems/find-the-difference/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class FindTheDifference {


}

/**
 * 以下两个方法都很快
 */
class FindTheDifferenceSolution {

    /**
     * 异或
     */
    public char findTheDifference(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        char res = tArr[tArr.length - 1];
        for (int i = 0; i < s.length(); i++) {
            res ^= sArr[i];
            res ^= tArr[i];
        }
        return res;
    }

    /**
     * 数组
     */
    public char findTheDifference1(String s, String t) {
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        int[] char2CountArr = new int[26];
        for (int i = 0; i < sArr.length; i++) {
            char2CountArr[tArr[i] - 'a']++;
            char2CountArr[sArr[i] - 'a']--;
        }
        char2CountArr[tArr[tArr.length - 1] - 'a']++;
        for (int i = 0; i < 26; i++) {
            if (char2CountArr[i] == 1) {
                return (char) (i + 'a');
            }
        }
        return '-';
    }
}
