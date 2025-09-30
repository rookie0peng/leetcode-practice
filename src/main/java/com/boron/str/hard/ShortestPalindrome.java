package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  @description: 214. 最短回文串 II <a href="https://leetcode.cn/problems/shortest-palindrome/submissions/667299645/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/30
 * </pre>
 */
public class ShortestPalindrome {

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
                .s("aacecaaa")
                .build();
        Result result = Result.builder().result("aaacecaaa").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .s("abcd")
                .build();
        Result result = Result.builder().result("dcbabcd").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .s("ababbbabbaba")
                .build();
        Result result = Result.builder().result("ababbabbbababbbabbaba").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder()
                .s("a")
                .build();
        Result result = Result.builder().result("a").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder()
                .s("aba")
                .build();
        Result result = Result.builder().result("aba").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = ShortestPalindromeSolution.shortestPalindrome(param.getS());
        String expectResult = result.getResult();
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
        test(generate4());
//        test(generate5());
//        test(generate6());
    }
    
}

/**
 * KMP算法
 */
class ShortestPalindromeSolution {

    public static String shortestPalindrome(String s) {
        int len = s.length();
        char[] sArr = s.toCharArray();
        // 初始化next数组，作为匹配失败时跳跃使用
        int[] next = buildNext(sArr);
        // kmp是匹配前缀，但是从后缀开始匹配则相当于回文匹配
        int j = 0;
        for (int i = len - 1; i >= 0; i--) {
            while (j > 0 && sArr[i] != sArr[j]) {
                // 不相等，则回退j
                j = next[j - 1];
            }
            if (sArr[i] == sArr[j]) {
                j++;
            }
        }
        String add = (j == len ? "" : s.substring(j));
        StringBuffer ans = new StringBuffer(add).reverse();
        ans.append(s);
        return ans.toString();
    }

    public static int[] buildNext(char[] sArr) {
        int len = sArr.length;
        int[] next = new int[len];
        int j = 0;
        // buildnext从1开始，匹配则从0开始
        for (int i = 1; i < len; i++) {
            while (next[j] > 0 && sArr[i] != sArr[j]) {
                // 如果不相等，则回退
                j = next[j - 1];
            }
            if (sArr[i] == sArr[j]) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}

/**
 * rancher匹配算法
 */
class ShortestPalindromeSolution1 {

    public static String shortestPalindrome(String s) {
        StringBuilder tmpSB = new StringBuilder();
        tmpSB.append('#');
        for (int i = 0; i < s.length(); i++) {
            tmpSB.append(s.charAt(i));
            tmpSB.append('#');
        }
        String tmpS = tmpSB.toString();
        int len1 = tmpS.length();
        char[] tmpSArr = tmpS.toCharArray();
        // 记录每个字符对应的回文串臂展
        int[] armArr = new int[len1];
        // 从左往右遍历，记录每一个下标的回文半径
        int center = 0, r = 0;
        int zeroR = 0;
        for (int i = 0; i <= len1 / 2; i++) {
            int start = i;
            if (i < r + center) {
                // i关于中心点的对称点
                int symmetricalI = center - (i - center);
                start = Math.min(center + r, armArr[symmetricalI] + i);
            }
            int tmpR = start - i;
            while (i - tmpR >= 0 && i + tmpR <= len1 - 1 && tmpSArr[i - tmpR] == tmpSArr[i + tmpR]) {
                tmpR++;
            }
            int curR = tmpR - 1;
            armArr[i] = curR;
            if (i + curR > center + r) {
                center = i;
                r = curR;
            }
            if (i == curR) {
                zeroR = curR;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len1 - 2 * zeroR; i++) {
            if (tmpSArr[len1 - 1 - i] != '#') {
                sb.append(tmpSArr[len1 - 1 - i]);
            }
        }
//        for (int i = 0; i < len1; i++) {
//            if (tmpSArr[i] != '#') {
//                sb.append(tmpSArr[i]);
//            }
//        }
        if (len1 - 2 * zeroR < len1) {
            sb.append(s);
        }
        return sb.toString();
    }


}
