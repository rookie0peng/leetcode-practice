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
 *  @description: 125. 验证回文串 <a href="https://leetcode.cn/problems/valid-palindrome/submissions/662455695/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/13
 * </pre>
 */
public class IsPalindrome {

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
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("A man, a plan, a canal: Panama").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("race a car").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s(" ").build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("0P").build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsPalindromeSolution.isPalindrome(param.getS());
        boolean expectResult = result.isResult();
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
        test(generate3());
    }
    
}

class IsPalindromeSolution {

    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        char lc, rc;
        while (left <= right) {
            lc = s.charAt(left);
            rc = s.charAt(right);
            boolean a1 = isNumAlphabet(lc);
            boolean a2 = isNumAlphabet(rc);
            if (a1 && a2) {
                if (!aEquals(lc, rc)) {
                    return false;
                } else {
                    left++;
                    right--;
                }
            } else if (a1) {
                right--;
            } else if (a2) {
                left++;
            } else {
                left++;
                right--;
            }
        }
        return true;
    }
    
    public static boolean isNumAlphabet(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    public static boolean isAlphabet(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean aEquals(char c1, char c2) {
        return c1 == c2 || (isAlphabet(c1) && isAlphabet(c2) && Math.abs(c1 - c2) == Math.abs('a' - 'A'));
    }
}
