package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 211. 添加与搜索单词 - 数据结构设计 <a href="https://leetcode.cn/problems/design-add-and-search-words-data-structure/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/18
 * </pre>
 */
public class WordDictionaryOuter {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {10, 2}).build();
        Result result = Result.builder().result("210").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = LargestNumberSolution.largestNumber(param.getNums());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void test1() {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        boolean search1 = wordDictionary.search("pad"); // 返回 False
        boolean search2 = wordDictionary.search("bad"); // 返回 True
        boolean search3 = wordDictionary.search(".ad"); // 返回 True
        boolean search4 = wordDictionary.search("b.."); // 返回 True
        System.out.println("search1: " + search1);
        System.out.println("search2: " + search2);
        System.out.println("search3: " + search3);
        System.out.println("search4: " + search4);
    }

    public static void main(String[] args) {
        test1();

//        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}

class WordDictionary {

    boolean isWord = false;
    WordDictionary[] next = new WordDictionary[26];

    public WordDictionary() {

    }

    public void addWord(String word) {
        WordDictionary tmp = this;
        char[] charArray = word.toCharArray();
        int idx;
        for (char c : charArray) {
            idx = c - 'a';
            if (tmp.next[idx] == null) {
                tmp.next[idx] = new WordDictionary();
            }
            tmp = tmp.next[idx];
        }
        tmp.isWord = true;
    }

    public boolean search(String word) {
        char[] charArray = word.toCharArray();
        return dfsSearch(charArray, 0);
    }

    private boolean dfsSearch(char[] words, int idx) {
        if (idx == words.length) {
            return isWord;
        }
        WordDictionary nowDict = this;
        char c1 = words[idx];
        if (c1 == '.') {
            for (WordDictionary nextDict : nowDict.next) {
                if (nextDict != null) {
                    boolean dfsSearch = nextDict.dfsSearch(words, idx + 1);
                    if (dfsSearch) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            WordDictionary nextDict = nowDict.next[c1 - 'a'];
            if (nextDict == null) {
                return false;
            }
            boolean dfsSearch = nextDict.dfsSearch(words, idx + 1);
            return dfsSearch;
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */

