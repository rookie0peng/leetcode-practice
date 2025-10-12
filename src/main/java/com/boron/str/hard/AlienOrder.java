package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 269. 火星词典 <a href="https://leetcode.cn/problems/alien-dictionary/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/8
 * </pre>
 */
public class AlienOrder {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String[] words;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .words(new String[] {"wrt","wrf","er","ett","rftt"})
                .build();
        Result result = Result.builder().result("wertf").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .words(new String[] {"z","x","z"})
                .build();
        Result result = Result.builder().result("").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = AlienOrderSolution.alienOrder(param.getWords());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        System.out.println(Math.pow(10, 15) > Integer.MAX_VALUE);;
//        test(generate0());
        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }

}

class AlienOrderSolution {

    private static Map<Character, Set<Character>> NEXT_MAP;
    private static boolean VALID;
    private static int INDEX;
    private static final int VISITING = 1;
    private static final int VISITED = -1;
    private static Map<Character, Integer> STATES;
    private static char[] ORDERS;

    public static String alienOrder(String[] words) {
        // 分层遍历整个数组
        // 如果当前为第一层，或者前一层的字符相等，再去对比当前层的每个字符
        // 将字符按顺序插入到链表中，a>->c->b, x->y|z->y, m->c->d
        // 记录每个字符的下一个字符，一对多
        Map<Character, Set<Character>> nextCharMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                nextCharMap.computeIfAbsent(words[i].charAt(j), k -> new HashSet<>());
            }
        }
        VALID = true;
        NEXT_MAP = nextCharMap;
        for (int i = 1; i < words.length && VALID; i++) {
            putNext(words[i - 1], words[i]);
        }
        if (!VALID) {
            return "";
        }
        INDEX = NEXT_MAP.size() - 1;
        Set<Character> allChars = NEXT_MAP.keySet();
        ORDERS = new char[allChars.size()];
        STATES = new HashMap<>();
        for (Character c : allChars) {
            if (VALID && !STATES.containsKey(c)) {
                dfs(c);
            }
        }
        if (!VALID) {
            return "";
        }
        return new String(ORDERS);
    }

    private static void putNext(String word0, String word1) {
        int len1 = word0.length(), len2 = word1.length();
        int len = Math.min(len1, len2);
        int i = 0;
        while (i < len) {
            if (word0.charAt(i) != word1.charAt(i)) {
                NEXT_MAP.get(word0.charAt(i)).add(word1.charAt(i));
                break;
            }
            i++;
        }
        if (i == len && len1 > len2) {
            VALID = false;
        }
    }

    private static void dfs(char c) {
        STATES.put(c, VISITING);
        Set<Character> nextChars = NEXT_MAP.get(c);
        for (char nextChar : nextChars) {
            if (!STATES.containsKey(nextChar)) {
                dfs(nextChar);
                if (!VALID) {
                    return;
                }
            } else {
                if (STATES.get(nextChar) != VISITED) {
                    VALID = false;
                    return;
                }
            }
        }
        STATES.put(c, VISITED);
        ORDERS[INDEX--] = c;
    }
}
