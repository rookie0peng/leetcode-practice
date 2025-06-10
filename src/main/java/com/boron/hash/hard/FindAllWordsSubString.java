package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 *  @description: 30. 串联所有单词的子串 <a href="https://leetcode.cn/problems/substring-with-concatenation-of-all-words/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/9
 * </pre>
 */
public class FindAllWordsSubString {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String s;

        String[] words;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        
        List<Integer> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("barfoothefoobarman").words(new String[] {"foo", "bar"}).build();
        Result result = Result.builder().results(List.of(0, 9)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("wordgoodgoodgoodbestword").words(new String[] {"word","good","best","word"}).build();
        Result result = Result.builder().results(List.of()).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("barfoofoobarthefoobarman").words(new String[] {"bar","foo","the"}).build();
        Result result = Result.builder().results(List.of(6, 9, 12)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("wordgoodgoodgoodbestword").words(new String[] {"word","good","best","good"}).build();
        Result result = Result.builder().results(List.of(8)).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<Integer> actualResults = FindAllWordsSubStringSolution.findSubstring(param.getS(), param.getWords());
        List<Integer> expectResults = result.getResults();
        boolean compareResult = Objects.equals(actualResults, expectResults);
        System.out.println("curStr vs expectStr");
        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
    }
}

class FindAllWordsSubStringSolution {
    public static List<Integer> findSubstring(String s, String[] words) {
        int wordLength = words[0].length();
        int windowLength = wordLength * words.length;
        if (windowLength > s.length()) {
            return List.of();
        }
        List<Integer> results = new ArrayList<>();
        // 将words存入map
        Map<String, Integer> targetMap = Stream.of(words).collect(Collectors.toMap(k -> k, v -> 1, Integer::sum));
        // 同时启动wordLength个滑动窗口
        for (int start = 0; start < wordLength; start++) {
            Map<String, Integer> currentMap = new HashMap<>();
            int overload = 0;
            for (int right = start + wordLength; right <= s.length(); right += wordLength) {
                String tmpWord = s.substring(right - wordLength, right);
                if (Objects.equals(currentMap.getOrDefault(tmpWord, 0), targetMap.getOrDefault(tmpWord, 0))) {
                    overload++;
                }
                currentMap.merge(tmpWord, 1, Integer::sum);

                // 该窗口的第一个单词的左端点
                int left = right - windowLength;
                // 小于0，说明窗口长度不足
                if (left < 0) {
                    continue;
                }
                // 没有过载，将下标添加进返回集
                if (overload == 0) {
                    results.add(left);
                }
                // 弹出最左边的单词
                String leftWord = s.substring(left, left + wordLength);
                // 将currentMap中的数量减一
                currentMap.merge(leftWord, -1, Integer::sum);
                // 如果currentMap减一之后，两者数量还相等，说明之前肯定过载了，需要对过载进行-1
                if (Objects.equals(currentMap.getOrDefault(leftWord, 0), targetMap.getOrDefault(leftWord, 0))) {
                    overload--;
                }
            }
        }
        return results;
    }

}
