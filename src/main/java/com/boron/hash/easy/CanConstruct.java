package com.boron.hash.easy;

/**
 * <pre>
 *  @description: 383. 赎金信 <a href="https://leetcode.cn/problems/ransom-note/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class CanConstruct {


}


class CanConstructSolution {
    // 看似只遍历一次，实则更慢
    public boolean canConstruct(String ransomNote, String magazine) {
        int rLen = ransomNote.length();
        int mLen = magazine.length();
        if (mLen < rLen) {
            return false;
        }
        int[] mCount = new int[128];
        int[] rCount = new int[128];
        char[] rArr = ransomNote.toCharArray();
        char[] mArr = magazine.toCharArray();
        int count = 0;
        for (int i = 0; i < mLen; i++) {
            if (i < rLen) {
                if (mCount[rArr[i]] > 0) {
                    mCount[rArr[i]]--;
                } else {
                    rCount[rArr[i]]++;
                    count++;
                }
            }
            if (rCount[mArr[i]] > 0) {
                rCount[mArr[i]]--;
                count--;
            } else {
                mCount[mArr[i]]++;
            }
        }
        return count <= 0;
    }


    public boolean canConstruct1(String ransomNote, String magazine) {
        int[] mArr = new int[128];
        for (char c : magazine.toCharArray()) {
            mArr[c]++;
        }
        for (char c : ransomNote.toCharArray()) {
            if (mArr[c] == 0) {
                return false;
            }
            mArr[c]--;
        }
        return true;
    }
}
