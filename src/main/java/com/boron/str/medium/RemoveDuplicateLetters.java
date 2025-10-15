package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 316. 去除重复字母 <a href="https://leetcode.cn/problems/remove-duplicate-letters/submissions/670960477/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/14
 * </pre>
 */
public class RemoveDuplicateLetters {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
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
                .s("bcabc")
                .build();
        Result result = Result.builder().result("abc").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .s("cbacdcbc")
                .build();
        Result result = Result.builder().result("acdb").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = RemoveDuplicateLettersSolution.removeDuplicateLetters(param.getS());
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

class RemoveDuplicateLettersSolution {
    public static String removeDuplicateLetters(String s) {
        // 记录每个字符是否访问过
        boolean[] visits = new boolean[26];
        // 记录字符的个数
        int[] letters = new int[26];
        // 遍历s，记录每个字符的个数
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            letters[c - 'a']++;
        }
        // 存放最后的结果
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 如果该字符未访问过
            if (!visits[c - 'a']) {
                char lastC;
                // 判断结果集中的最后一个字符是否大于当前字符，且之后是否还剩多余字符
                while (!res.isEmpty() && (lastC = res.charAt(res.length() - 1)) > c && letters[lastC - 'a'] > 0) {
                    // 如果有，则将该字符设置为未访问，并删除结果集中的该字符
                    visits[lastC - 'a'] = false;
                    res.deleteCharAt(res.length() - 1);
                }
                // 将当前字符添加进结果集，并设置为已访问
                visits[c - 'a'] = true;
                res.append(c);
            }
            // 当前字符的数量-1
            letters[c - 'a']--;
        }
        return res.toString();
    }
}
