package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * <pre>
 *  @description: 87. 扰乱字符串 <a href="https://leetcode.cn/problems/scramble-string/solutions/724718/rao-luan-zi-fu-chuan-by-leetcode-solutio-8r9t/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/13
 * </pre>
 */
public class IsScramble {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String s1;
        String s2;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s1("great").s2("rgeat").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s1("abcde").s2("caebd").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s1("a").s2("a").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsScrambleSolution.isScramble(param.getS1(), param.getS2());
        boolean expectResult = result.isResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

class IsScrambleSolution {

    // 静态存储第一个字符串
    static String S1;
    // 静态存储第二个字符串
    static String S2;
    // 记录两个字符串分别从i1,i2开始，长度length的子串是否相等为和谐子串
    static int[][][] MEMORY;
    // 用于判断两个子串的字母和对应数量是否一致
    static int[] alphabets = new int[26];

    public static boolean isScramble(String s1, String s2) {
        // 初始化各个参数
        int len = s1.length();
        MEMORY = new int[len][len][len + 1];
        S1 = s1;
        S2 = s2;
        // 广度优先查找
        return dfs(0, 0, len);
    }

    public static boolean dfs(int i1, int i2, int length) {
        // 如果该下标对应的值不为0，则说明已经被计算过，直接获取计算值
        if (MEMORY[i1][i2][length] != 0) {
            return MEMORY[i1][i2][length] == 1;
        }
        // 判断两个子串是否相等
        if (S1.substring(i1, i1 + length).equals(S2.substring(i2, i2 + length))) {
            MEMORY[i1][i2][length] = 1;
            return true;
        }
        // 判断两个子串的字母和字母的数量是否一致
        if (!alphabetEquals(i1, i2, length)) {
            MEMORY[i1][i2][length] = -1;
            return false;
        }
        // 从长度1开始遍历，直到length-1
        for (int i = 1; i < length; i++) {
            // 不交换
            if (dfs(i1, i2, i) && dfs(i1 + i, i2 + i, length - i)) {
                MEMORY[i1][i2][length] = 1;
                return true;
            }
            // 交换
            if (dfs(i1, i2 + length - i, i) && dfs(i1 + i, i2, length - i)) {
                MEMORY[i1][i2][length] = 1;
                return true;
            }
        }
        // 如果未从前面任意点返回，则说明两个子串不“和谐”
        MEMORY[i1][i2][length] = -1;
        return false;
    }

    public static boolean alphabetEquals(int i1, int i2, int length) {
//        Arrays.fill(alphabets, 0);
        // 根据第一个子串标记字母表++
        for (int i = i1; i < i1 + length; i++) {
            alphabets[S1.charAt(i) - 'a']++;
        }
        // 根据第二个子串标记字母表--
        for (int i = i2; i < i2 + length; i++) {
            alphabets[S2.charAt(i) - 'a']--;
        }
        boolean equals = true;
        // 遍历字母表，如果相同字母的数量不一致，则说明不相等
        for (int i = 0; i < 26; i++) {
            if (alphabets[i] != 0) {
                equals = false;
            }
            // 重置字母表
            alphabets[i] = 0;
        }
        return equals;
    }
}
