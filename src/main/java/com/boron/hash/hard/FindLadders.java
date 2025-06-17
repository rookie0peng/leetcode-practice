package com.boron.hash.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 126. 单词接龙 II <a href="https://leetcode.cn/problems/word-ladder-ii/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/17
 * </pre>
 */
public class FindLadders {

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

        List<List<String>> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().beginWord("hit").endWord("cog").wordList(List.of("hot","dot","dog","lot","log","cog")).build();
        Result result = Result.builder().results(List.of(List.of("hit","hot","dot","dog","cog"), List.of("hit","hot","lot","log","cog"))).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<List<String>> actualResults = FindLaddersSolution.findLadders(param.getBeginWord(), param.getEndWord(), param.getWordList());
        // 然后对外部List排序
        List<List<String>> actualResults2 = actualResults.stream().sorted((list1, list2) -> {
            // 比较两个内部List的字符串顺序
            for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
                int cmp = list1.get(i).compareTo(list2.get(i));
                if (cmp != 0) {
                    return cmp;
                }
            }
            return Integer.compare(list1.size(), list2.size());
        }).toList();
        List<List<String>> expectResults = result.getResults();
        // 然后对外部List排序
        List<List<String>> expectResults2 = expectResults.stream().sorted((list1, list2) -> {
            // 比较两个内部List的字符串顺序
            for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
                int cmp = list1.get(i).compareTo(list2.get(i));
                if (cmp != 0) {
                    return cmp;
                }
            }
            return Integer.compare(list1.size(), list2.size());
        }).toList();
        boolean compareResult = Objects.equals(actualResults2, expectResults2);
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResults), JSON.toJSONString(expectResults));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
    }
}

class FindLaddersSolution {
    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        int len = beginWord.length();
        // 因为需要快速判断能扩展的单词是否在wordList里面，因此需要将wordList存入哈希表，这里命名为字典
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return List.of();
        }
        dict.remove(beginWord);
        // 广度优先搜索先建图
        // 记录扩展出的单词是在第几次扩展的时候得到的，key：单词，value：在广度优先搜索的第几层
        Map<String, Integer> steps = new HashMap<>();
        steps.put(beginWord, 0);
        // 记录了单词是从哪些单词扩展而来，key：当前单词，value：来源单词，这些单词可以变换到 key ，它们是一对多关系
        Map<String, List<String>> from = new HashMap<>();
        int step = 1;
        // 暂存所有需要扩展的词
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        boolean found = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curWord = queue.poll();
                char[] charArray = curWord.toCharArray();
                // 将每一位替换成任意一个英文小写字母
                for (int j = 0; j < len; j++) {
                    char origin = charArray[j];
                    for (char k = 'a'; k <= 'z'; k++) {
                        charArray[j] = k;
                        String nextWord = String.valueOf(charArray);
                        // 如果之前已经记录过该单词，则需要判断该单词当前的步骤是不是<=之前步骤
                        // 按理来说，之前记录过该单词，则当前步骤一定是 大于等于 之前步骤，因此只需要判断是否等于，然后记录
                        if (steps.containsKey(nextWord) && step == steps.get(nextWord)) {
                            from.get(nextWord).add(curWord);
                        }
                        if (!dict.contains(nextWord)) {
                            continue;
                        }
                        // 如果从一个单词扩展出来的单词以前遍历过，距离一定更远，为了避免搜索到已经遍历到、且距离更远的单词，需要将它从dict中删除
                        dict.remove(nextWord);
                        // 这一层扩展出的单词进入队列
                        queue.offer(nextWord);
                        //记录 nextWord 从 curWord 而来
                        from.putIfAbsent(nextWord, new ArrayList<>());
                        from.get(nextWord).add(curWord);
                        // 记录 nextWord 的 step
                        steps.put(nextWord, step);
                        if (nextWord.equals(endWord)) {
                            found = true;
                        }
                    }
                    charArray[j] = origin;
                }
            }
            step++;
            if (found) {
                break;
            }
        }

        // 第2步：回溯所有解，从 endWord 恢复到 beginWord，所以每次尝试操作path列表的头部
        List<List<String>> res = new ArrayList<>();
        if (found) {
            Deque<String> path = new ArrayDeque<>();
            path.add(endWord);
            backtrack(from, path, beginWord, endWord, res);
        }
        return res;
    }

    // 第 2 步：回溯找到所有解，从 endWord 恢复到 beginWord ，所以每次尝试操作 path 列表的头部
    public static void backtrack(Map<String, List<String>> from, Deque<String> path, String beginWord, String cur, List<List<String>> res) {
        if (cur.equals(beginWord)) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (String precursor : from.get(cur)) {
            path.addFirst(precursor);
            backtrack(from, path, beginWord, precursor, res);
            path.removeFirst();
        }

    }
}
