package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 127. 单词接龙 <a href="https://leetcode.cn/problems/word-ladder/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/17
 * </pre>
 */
public class LadderLength {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String beginWord;

        String endWord;

        List<String> wordList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {

        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().beginWord("hit").endWord("cog").wordList(List.of("hot","dot","dog","lot","log","cog")).build();
        Result result = Result.builder().result(5).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = LadderLengthSolution.ladderLength(param.getBeginWord(), param.getEndWord(), param.getWordList());
        int expectResult = result.getResult();
        // 然后对外部List排序
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
    }
}

class LadderLengthSolution {
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int len = beginWord.length();
        // 构建wordList的字典，方便后续快速查找
        Set<String> dict = new HashSet<>(wordList);
        // wordList不需要包含起始单词，直接移除
        dict.remove(beginWord);
        // 字典不包含终点单词，返回0
        if (!dict.contains(endWord)) {
            return 0;
        }
        // 构建广度优先遍历步骤map
        Map<String, Integer> steps = new HashMap<>();
        // 先将beginWord存入
        steps.put(beginWord, 1);
        // 存放每一层的元素
        Deque<String> queue = new LinkedList<>();
        // 先将beginWord放入
        queue.offer(beginWord);
        // 找到终点word标志位
        boolean found = false;
        // 队列是否不为空
        one: while (!queue.isEmpty()) {
            // 从队列弹出单词，作为当前单词
            String curWord = queue.pop();
            // 将单词转为字符数组，方便操作
            char[] charArray = curWord.toCharArray();
            // 遍历单词长度
            for (int i = 0; i < len; i++) {
                // 替换每一个位置的字符
                char origin = charArray[i];
                // 从 a 到 z
                for (char j = 'a'; j <= 'z'; j++) {
                    // 替换位置i的字符为j
                    charArray[i] = j;
                    // 将字符数组转为字符串
                    String nextWord = String.valueOf(charArray);
                    // 如果字典不包含 nextWord ，再来一次
                    if (!dict.contains(nextWord)) {
                        continue;
                    }
                    // 如果字典包含字符串
                    // 将 nextWord 移除，因为下次再遇到这个单词，它的步骤肯定比这次遇到的多，所以，直接丢弃
                    dict.remove(nextWord);
                    // 将 nextWord 放入 steps，记录对应的 step = step(curWord) + 1
                    steps.put(nextWord, steps.get(curWord) + 1);
                    // queue 入队 nextWord，下一轮广度优先遍历使用
                    queue.offer(nextWord);
                    // 如果nextWord等于endWord，说明到终点了
                    if (nextWord.equals(endWord)) {
                        // found赋值为true，并且终止所有循环
                        found = true;
                        break one;
                    }
                }
                // 恢复该位置的字符
                charArray[i] = origin;
            }
        }
        // 如果找到终点，则从steps中获取终点的步骤；否则，返回0
        return found ? steps.get(endWord) : 0;
    }
}
