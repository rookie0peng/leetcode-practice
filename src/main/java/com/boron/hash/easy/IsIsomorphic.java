package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * <pre>
 *  @description: 205. 同构字符串 <a href="https://leetcode.cn/problems/isomorphic-strings/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/25
 * </pre>
 */
public class IsIsomorphic {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
        String t;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("egg").t("add").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("foo").t("bar").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("paper").t("title").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("badc").t("baba").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsIsomorphicSolution.isIsomorphic(param.getS(), param.getT());
        boolean expectResult = result.isResult();
        boolean compareResult = actualResult == expectResult;
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

class IsIsomorphicSolution {
    public static boolean isIsomorphic(String s, String t) {
        int len = s.length();
        int[] mapArr = new int[128];
        int[] reverseMapArr = new int[128];
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        boolean result = true;
        for (int i = 0; i < len; i++) {
            // 预期 sArr[i] -> tArr[i]
            // 先取出旧的 sArr[i] 映射值
            int mapValue = mapArr[sArr[i]];
            // 如果映射值为0
            if (mapValue == 0) {
                // 取出旧的反向映射值，即 tArr[i] 的映射值
                int reverseMapValue = reverseMapArr[tArr[i]];
                // 如果 tArr[i] 的映射值不为0，且该值不等于 sArr[i]
                if (reverseMapValue != 0 && reverseMapValue != sArr[i]) {
                    // 则认为不合法
                    result = false;
                    break;
                }
            } else {
                // 如果映射值不为0
                // 且旧的映射值不等于 tArr[i]
                if (tArr[i] != mapValue) {
                    // 则认为不合法
                    result = false;
                    break;
                }
            }
            mapArr[sArr[i]] = tArr[i];
            reverseMapArr[tArr[i]] = sArr[i];
        }
        return result;
    }
}