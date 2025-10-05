package com.boron.str.easy;

import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  @description: 243. 最短单词距离 <a href="https://leetcode.cn/problems/shortest-word-distance/submissions/668233501/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/5
 * </pre>
 */
public class ShortestDistance {

}

class ShortestDistanceSolution {
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        int idx1 = -1, idx2 = -1;
        int minDistance = Integer.MAX_VALUE;
        boolean change = false;
        for (int i = 0; i < wordsDict.length; i++) {
            String word = wordsDict[i];
            change = false;
            if (word.equals(word1)) {
                idx1 = i;
                change = true;
            } else if (word.equals(word2)) {
                idx2 = i;
                change = true;
            }
            if (change && idx1 != -1 && idx2 != -1) {
                minDistance = Math.min(Math.abs(idx1 - idx2), minDistance);
            }
            if (minDistance == 1) {
                break;
            }
        }
        return minDistance;
    }
}
