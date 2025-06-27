package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 288. 单词的唯一缩写 <a href="https://leetcode.cn/problems/unique-word-abbreviation/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/28
 * </pre>
 */
public class ValidWordAbbrOuter {

    public static void test() {
        ValidWordAbbr validWordAbbr = new ValidWordAbbr(new String[]{"deer", "door", "cake", "card"});
        validWordAbbr.isUnique("dear"); // 返回 false，字典中的 "deer" 与输入 "dear" 的缩写都是 "d2r"，但这两个单词不相同
        validWordAbbr.isUnique("cart"); // 返回 true，字典中不存在缩写为 "c2t" 的单词
        validWordAbbr.isUnique("cane"); // 返回 false，字典中的 "cake" 与输入 "cane" 的缩写都是 "c2e"，但这两个单词不相同
        validWordAbbr.isUnique("make"); // 返回 true，字典中不存在缩写为 "m2e" 的单词
        validWordAbbr.isUnique("cake"); // 返回 true，因为 "cake" 已经存在于字典中，并且字典中没有其他缩写为 "c2e" 的单词
    }

    public static void test1() {
        ValidWordAbbr validWordAbbr = new ValidWordAbbr(new String[]{"deer","door","cake","card"});
        validWordAbbr.isUnique("dear"); // 返回 false，字典中的 "deer" 与输入 "dear" 的缩写都是 "d2r"，但这两个单词不相同
        validWordAbbr.isUnique("cart"); // 返回 true，字典中不存在缩写为 "c2t" 的单词
        validWordAbbr.isUnique("cane"); // 返回 false，字典中的 "cake" 与输入 "cane" 的缩写都是 "c2e"，但这两个单词不相同
        validWordAbbr.isUnique("make"); // 返回 true，字典中不存在缩写为 "m2e" 的单词
        validWordAbbr.isUnique("cake"); // 返回 true，因为 "cake" 已经存在于字典中，并且字典中没有其他缩写为 "c2e" 的单词
    }

    public static void main(String[] args) {
        test();
        test1();
    }
}

class ValidWordAbbr {

    private final Map<String, Set<String>> map = new HashMap<>();

    public ValidWordAbbr(String[] dictionary) {
        // 遍历字典数组
        for (String word : dictionary) {
            // 生成简写
            String abbr = generateAbbr(word);
            // 将简写字符串放入map
            Set<String> abbrs = map.computeIfAbsent(abbr, k -> new HashSet<>());
            abbrs.add(word);
        }
    }

    public boolean isUnique(String word) {
        // 获取简写
        String abbr = generateAbbr(word);
        // 如果map不包含该简写，返回 true
        if (!map.containsKey(abbr)) {
            return true;
        }
        // 如果map的value等于1且map的value包含word，则返回true
        return map.get(abbr).size() == 1 && map.get(abbr).contains(word);
    }

    private String generateAbbr(String word) {
        // 如果字符串长度大于2
        if (word.length() > 2) {
            // 分别添加首尾字符以及中间字符长度
            StringBuilder builder = new StringBuilder();
            builder.append(word.charAt(0));
            builder.append(word.length() - 2);
            builder.append(word.charAt(word.length() - 1));
            return builder.toString();
        } else {
            return word;
        }
    }
}

/**
 * Your ValidWordAbbr object will be instantiated and called as such:
 * ValidWordAbbr obj = new ValidWordAbbr(dictionary);
 * boolean param_1 = obj.isUnique(word);
 */