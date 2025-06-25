package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;

/**
 * <pre>
 *  @description: 208. 实现 Trie (前缀树) <a href="https://leetcode.cn/problems/implement-trie-prefix-tree/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class PrefixTrie {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        int[][] matrix;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int[][] results;
    }

    public static void test0() {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // 返回 True
        System.out.println(trie.search("app"));     // 返回 False
        System.out.println(trie.startsWith("app")); // 返回 True
        trie.insert("app");
        System.out.println(trie.search("app"));     // 返回 True
    }

    public static void main(String[] args) {
        test0();
//        test(generate1());
//        test(generate2());
    }
}

/**
 * 前缀搜索，
 * 插入：将单词的所有字符按顺序一个一个的放入前一个字符的子节点，到最后一个节点时，将当前节点标记为isEnd。
 * 搜索：按照顺序匹配需要搜索单词的各个字符，如果匹配结束，该节点的不为null，且isEnd为True则说明搜索成功；否则，反之
 * 前缀搜索：和搜索的步骤一样，不过，最后只需要判断该节点不为null，无需判断isEnd为True。
 */
class Trie {

    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        // 因为只需要处理英文26个字母，所以长度设置成26即可
        children = new Trie[26];

        isEnd = false;
    }

    public void insert(String word) {
        // 获取当前节点
        Trie node = this;
        // 将word转为字符数组
        char[] wordArr = word.toCharArray();
        int index;
        // 遍历字符数组
        for (char wordChar : wordArr) {
            // 获取每个字符对应的数组下标
            index = wordChar - 'a';
            // 如果字符对应的子节点为空
            if (node.children[index] == null) {
                // 在该位置创建新的节点
                node.children[index] = new Trie();
            }
            // 将node指向子节点，继续遍历
            node = node.children[index];
        }
        // 遍历结束，将最后一个子节点的isEnd标记为True
        node.isEnd = true;
    }

    public boolean search(String word) {
        // 使用前缀搜索找到该节点
        Trie trie = searchPrefix(word);
        // 校验该节点不为null，且isEnd为true
        return trie != null && trie.isEnd;
    }

    public boolean startsWith(String prefix) {
        // 使用前缀搜索找到该节点
        Trie trie = searchPrefix(prefix);
        // 校验该节点不为null
        return trie != null;
    }

    private Trie searchPrefix(String prefix) {
        // 如果搜索的前缀为null，直接返回null
        if (prefix == null)
            return null;
        // 将前缀转为字符数组
        char[] prefixArr = prefix.toCharArray();
        // 将node指向当前节点
        Trie node = this;
        int index;
        // 遍历前缀字符数组
        for (char c : prefixArr) {
            // 获取字符对应的下标
            index = c - 'a';
            // 如果该下标对应的子节点为空，则直接返回空
            if (node.children[index] == null) {
                return null;
            }
            // 将node指向子节点
            node = node.children[index];
        }
        // 返回寻找到的节点
        return node;
    }
}

class Trie1 {
    private Trie1[] children;
    private boolean isEnd;

    public Trie1() {
        children = new Trie1[26];
        isEnd = false;
    }

    public void insert(String word) {
        Trie1 node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Trie1();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie1 node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    private Trie1 searchPrefix(String prefix) {
        Trie1 node = this;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            int index = ch - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }
}
