package com.boron.str.easy;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 28. 找出字符串中第一个匹配项的下标 <a href="https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/submissions/654692077/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/8/20
 * </pre>
 */
public class StrCompare {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String haystack;
        String needle;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().haystack("sadbutsad").needle("sad").build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().haystack("leetcode").needle("leeto").build();
        Result result = Result.builder().result(-1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().haystack("mississippi").needle("issi").build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = StrCompareSolution.strStr(param.getHaystack(), param.getNeedle());
        int expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());

    }

}

class StrCompareSolution {
    public static int strStr(String haystack, String needle) {
        // 如果needle的长度大于haystack的长度，则返回-1
        if (needle.length() > haystack.length()) {
            return -1;
        }
        // 两个字符串的长度
        int len1 = haystack.length(), len2 = needle.length();
        // 判断needle是否有重复前缀
        char firstChar = needle.charAt(0);
        // 遍历时的跳跃距离
        int jumpDistance = 1;
        for (int i = 1; i < len2; i++) {
            char c = needle.charAt(i);
            if (c == firstChar) {
                jumpDistance = i;
                break;
            }
        }
        // 遍历第一个字符串的下标
        int x = 0;
        boolean same = true;
        // 如果
        while (x <= len1 - len2) {
            same = true;
            // 遍历第二个字符串的下标
            int y = 0;
            // 判断两个字符串是否相等
            for (; y < len2; y++) {
                char c1 = haystack.charAt(y + x);
                char c2 = needle.charAt(y);
                // 如果两个字符不相等，则赋值变量，中断循环
                if (c1 != c2) {
                    same = false;
                    break;
                }
            }
            // 如果相等，则中断循环
            if (same) {
                break;
            } else {
                // 如果不相等，则获取跳跃距离
                // 如果当前下标大于跳跃距离，则直接移动到跳跃距离的下标；否则跳跃距离1
                int tmpJumpDistance = y > jumpDistance ? jumpDistance : 1;
                x += tmpJumpDistance;
            }
        }
        return same ? x : -1;
    }


}
