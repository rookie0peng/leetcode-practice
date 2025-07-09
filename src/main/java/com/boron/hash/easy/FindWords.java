package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;

/**
 * <pre>
 *  @description: 500. 键盘行 <a href="https://leetcode.cn/problems/keyboard-row/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/10
 * </pre>
 */
public class FindWords {

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
        String[] results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().words(new String[] {"Hello","Alaska","Dad","Peace"}).build();
        Result result = Result.builder().results(new String[] {"Alaska","Dad"}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().words(new String[] {"omk"}).build();
        Result result = Result.builder().results(new String[] {}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().words(new String[] {"adsdf","sfd"}).build();
        Result result = Result.builder().results(new String[] {"adsdf","sfd"}).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String[] actualResults = FindWordsSolution.findWords(param.getWords());
        String[] expectResults = result.getResults();
        boolean compareResult = Arrays.equals(actualResults, expectResults);
        System.out.println("actualResults vs expectResults");
//        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
        test(generate2());
//        test(generate3());
    }
}

class FindWordsSolution {
    private static final String CHAR1 = "zxcvbnm";
    private static final String CHAR2 = "asdfghjkl";
    private static final String CHAR3 = "qwertyuiop";
    private static final int[] DICT = initDict();

    private static int[] initDict() {
        int[] dict = new int[26];
        // 将键盘的每个字符映射行数，存入数组
//        for (int i = 0; i < CHAR1.length(); i++) {
//            dict[CHAR1.charAt(i) - 'a'] = 0;
//        }
        for (int i = 0; i < CHAR2.length(); i++) {
            dict[CHAR2.charAt(i) - 'a'] = 1;
        }
        for (int i = 0; i < CHAR3.length(); i++) {
            dict[CHAR3.charAt(i) - 'a'] = 2;
        }
        return dict;
    }

    public static String[] findWords(String[] words) {
        // 初始化结果集
        String[] results = new String[words.length];
        int resIdx = 0;
        // 遍历单词数组
        for (String word : words) {
            // 获取第一个字符所在行数
            int tmpIdx = word.charAt(0) >= 'a' ? word.charAt(0) - 'a' : word.charAt(0) - 'A';
            int line = DICT[tmpIdx];
            boolean inLine = true;
            // 遍历后续字符，但凡有字符所在行数不一致，直接中断
            for (int i = 1; i < word.length(); i++) {
                tmpIdx = word.charAt(i) >= 'a' ? word.charAt(i) - 'a' : word.charAt(i) - 'A';
                if (DICT[tmpIdx] != line) {
                    inLine = false;
                    break;
                }
            }
            // 如果在同一行，则将元素加入结果集，索引+1
            if (inLine) {
                results[resIdx] = word;
                resIdx++;
            }
        }
        // 返回截取的数组
        return Arrays.copyOfRange(results, 0, resIdx);
    }
}
