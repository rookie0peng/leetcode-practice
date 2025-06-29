package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 299. 猜数字游戏 <a href="https://leetcode.cn/problems/bulls-and-cows/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/28
 * </pre>
 */
public class GuessNumber {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String secret;
        String guess;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().secret("1807").guess("7810").build();
        Result result = Result.builder().result("1A3B").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().secret("1123").guess("0111").build();
        Result result = Result.builder().result("1A1B").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().secret("1807").guess("8812").build();
        Result result = Result.builder().result("1A1B").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().secret("11").guess("10").build();
        Result result = Result.builder().result("1A0B").build();
        return Pair.of(param, result);
    }
    
    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = GuessNumberSolution.getHint(param.getSecret(), param.getGuess());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
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

class GuessNumberSolution {

    public static String getHint(String secret, String guess) {
        // 初始化两个map，字符->数量
        int[] s2CountArr = new int[128];
        int[] g2CountArr = new int[128];
        // 将两个字符串转为数组
        char[] secretArr = secret.toCharArray();
        char[] guessArr = guess.toCharArray();
        // 初始化公牛和奶牛值
        int countBull = 0;
        int countCow = 0;
        // 遍历s和g两个数组
        for (int i = 0; i < secretArr.length; i++) {
            // 如果相同下标的两个字符相等，则公牛数+1
            if (secretArr[i] == guessArr[i]) {
                countBull++;
            } else {
                // 如果s数组对应g字符的数量大于0
                if (s2CountArr[guessArr[i]] > 0) {
                    // 数量-1
                    s2CountArr[guessArr[i]]--;
                    // 奶牛数+1
                    countCow++;
                } else {
                    // g数组对应的g字符+1
                    g2CountArr[guessArr[i]]++;
                }
                // 如果g数组对应s字符的数量大于0
                if (g2CountArr[secretArr[i]] > 0) {
                    // 数量-1
                    g2CountArr[secretArr[i]]--;
                    // 奶牛数+1
                    countCow++;
                } else {
                    // s数组对应的s字符+1
                    s2CountArr[secretArr[i]]++;
                }
            }
        }
        return countBull + "A" + countCow + "B";
    }
}