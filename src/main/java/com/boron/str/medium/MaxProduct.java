package com.boron.str.medium;

import java.util.*;
import java.util.stream.Stream;

/**
 * <pre>
 *  @description: 318. 最大单词长度乘积 <a href="https://leetcode.cn/problems/maximum-product-of-word-lengths/submissions/670971940/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/16
 * </pre>
 */
public class MaxProduct {
}

/**
 * 位运算
 */
class MaxProductSolution {
    public int maxProduct(String[] words) {
        int[] marks = new int[words.length];
        // 26个字母对应26位
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int val = 0;
            for (int j = 0; j < word.length(); j++) {
                val |= 1 << (word.charAt(j) - 'a');
            }
            marks[i] = val;
        }
        int maxValue = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                // 如果两个单词的字母没有交集，则进行计算
                if ((marks[i] & marks[j]) == 0) {
                    maxValue = Math.max(maxValue, words[i].length() * words[j].length());
                }
            }
        }
        return maxValue;
    }
}
/**
 * 遍历法，很慢，但完成了
 */
class MaxProductSolution1 {
    public int maxProduct(String[] words) {
        Map<Character, Set<String>> char2Words = new HashMap<>();
        Map<String, Set<Character>> word2Chars = new HashMap<>();
        Set<Character> allChars = new HashSet<>();
        for (String word : words) {
            Set<Character> letters = new HashSet<>();
            for (int i = 0; i < word.length(); i++) {
                letters.add(word.charAt(i));
                allChars.add(word.charAt(i));
                Set<String> words1 = char2Words.computeIfAbsent(word.charAt(i), k -> new HashSet<>());
                words1.add(word);
            }
            word2Chars.put(word, letters);
        }
        int maxValue = 0;
        for (int i = 0; i < words.length; i++) {
            String word1 = words[i];
            Set<Character> chars1 = word2Chars.get(word1);
            for (int j = i + 1; j < words.length; j++) {
                String word2 = words[j];
                Set<Character> chars2 = word2Chars.get(word2);
                boolean contains = chars1.stream().anyMatch(chars2::contains);
                if (!contains) {
                    maxValue = Math.max(maxValue, word1.length() * word2.length());
                }
            }
        }
        return maxValue;

    }
}
