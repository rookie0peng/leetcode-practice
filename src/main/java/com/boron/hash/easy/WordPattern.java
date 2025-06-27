package com.boron.hash.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  @description: 290. 单词规律 <a href="https://leetcode.cn/problems/word-pattern/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/28
 * </pre>
 */
public class WordPattern {
}

class Solution {
    public boolean wordPattern(String pattern, String s) {
        // 将字符串分割成字符串数组
        String[] sArr = s.split(" ");
        // 如果字符串和匹配器的长度不一致，返回false
        if (sArr.length != pattern.length()) {
            return false;
        }
        // 将匹配器转为字符数组
        char[] patternArr = pattern.toCharArray();
        // 初始化两个映射表，匹配器 -> 字符串，字符串 -> 匹配器
        Map<Character, String> p2sMap = new HashMap<>();
        Map<String, Character> s2pMap = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char c = patternArr[i];
            // 如果匹配器map包含该字符，且旧字符串不等于新字符串，则返回false
            if (p2sMap.containsKey(c) && !p2sMap.get(c).equals(sArr[i])) {
                return false;
            }
            // 如果字符串map包含该字符串，且旧字符不等于新字符，则返回false
            if (s2pMap.containsKey(sArr[i]) && !s2pMap.get(sArr[i]).equals(c)) {
                return false;
            }
            // 分别将两对元素放入对应的map
            p2sMap.put(c, sArr[i]);
            s2pMap.put(sArr[i], c);
        }
        // 未找到不一致，则返回true
        return true;
    }
}