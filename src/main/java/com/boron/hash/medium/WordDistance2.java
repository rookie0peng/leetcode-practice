package com.boron.hash.medium;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  @description: 244. 最短单词距离 II <a href="https://leetcode.cn/problems/shortest-word-distance-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class WordDistance2 {

    public static void test() {
        WordDistance wordDistance = new WordDistance(new String[] {"practice", "makes", "perfect", "coding", "makes"});
        int shortest1 = wordDistance.shortest("coding", "practice");// 返回 3
        int shortest2 = wordDistance.shortest("makes", "coding");    // 返回 1
        System.out.println(shortest1);
        System.out.println(shortest2);
    }

    public static void main(String[] args) {
        test();
    }
}

class WordDistance {

    private final Map<String, List<Integer>> dict = new HashMap<>();

    public WordDistance(String[] wordsDict) {
        for (int i = 0; i < wordsDict.length; i++) {
            List<Integer> tmpIndices = dict.computeIfAbsent(wordsDict[i], k -> new ArrayList<>());
            tmpIndices.add(i);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> indices1 = dict.get(word1);
        List<Integer> indices2 = dict.get(word2);
        int minDistance = Integer.MAX_VALUE;
        for (Integer index1 : indices1) {
            for (Integer index2 : indices2) {
                minDistance = Math.min(minDistance, Math.abs(index2 - index1));
                if (index1 < index2) {
                    break;
                }
            }
        }
        return minDistance;
    }
}

/**
 * Your WordDistance object will be instantiated and called as such:
 * WordDistance obj = new WordDistance(wordsDict);
 * int param_1 = obj.shortest(word1,word2);
 */
