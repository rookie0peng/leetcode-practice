package com.boron.str.medium;

/**
 * <pre>
 *  @description: 245. 最短单词距离 III <a href="https://leetcode.cn/problems/shortest-word-distance-iii/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/6
 * </pre>
 */
public class ShortestWordDistance {


}

class ShortestWordDistanceSolution {
    public int shortestWordDistance(String[] wordsDict, String word1, String word2) {
        int len = wordsDict.length;
        int[] idx = new int[] {-1, -1};
        int minDis = Integer.MAX_VALUE;
        if (word1.equals(word2)) {
            for (int i = 0; i < len; i++) {
                String word = wordsDict[i];
                if (word.equals(word1)) {
                    idx[0] = idx[1];
                    idx[1] = i;
                    if (idx[0] != -1) {
                        minDis = Math.min(minDis, idx[1] - idx[0]);
                    }
                }
            }
        } else {
            for (int i = 0; i < len; i++) {
                String word = wordsDict[i];
                boolean change = false;
                if (word.equals(word1)) {
                    idx[0] = i;
                    change = true;
                }
                if (word.equals(word2)) {
                    idx[1] = i;
                    change = true;
                }
                if (change && idx[0] != -1 && idx[1] != -1) {
                    minDis = Math.min(minDis, Math.abs(idx[1] - idx[0]));
                }
            }
        }
        return minDis;
    }
}
