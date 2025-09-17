package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 *  @description: 186. 反转字符串中的单词 II <a href="https://leetcode.cn/problems/reverse-words-in-a-string-ii/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/16
 * </pre>
 */
public class ReverseWords {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        char[] s;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        char[] result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s(new char[] {'t','h','e',' ','s','k','y',' ','i','s',' ','b','l','u','e'}).build();
        Result result = Result.builder().result(new char[] {'b','l','u','e',' ','i','s',' ','s','k','y',' ','t','h','e'}).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        ReverseWordsSolution.reverseWords(param.getS());
        char[] expectResult = result.getResult();
        boolean compareResult = Arrays.equals(param.getS(), expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(param.getS()), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
    
}

class ReverseWordsSolution {
    public static void reverseWords(char[] s) {
        // 反转所有字符
        int left = 0, right = s.length - 1;
        char sl, sr;
        while (left < right) {
            sl = s[left];
            sr = s[right];
            s[left] = sr;
            s[right] = sl;
            left++;
            right--;
        }
        // 反转每个单词
        int left1 = 0, right1 = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == ' ' || i == s.length - 1) {
                right1 = i == s.length - 1 ? i : i - 1;
                char lc1, rc1;
                while (left1 < right1) {
                    lc1 = s[left1];
                    rc1 = s[right1];
                    s[left1] = rc1;
                    s[right1] = lc1;
                    left1++;
                    right1--;
                }
                left1 = i + 1;
            }
        }
    }
}
