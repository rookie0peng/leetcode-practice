package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  @description: 76. 最小覆盖子串 <a href="https://leetcode.cn/problems/minimum-window-substring/description/?envType=problem-list-v2&envId=hash-table">跳转</a>
 *  @author: BruceBoron
 *  @date: 2025/6/16
 * </pre>
 */
public class MinimalCoverSubString {

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

        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("XADOBECODEBANC").t("ABC").build();
        Result result = Result.builder().result("BANC").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("a").t("a").build();
        Result result = Result.builder().result("a").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("a").t("aa").build();
        Result result = Result.builder().result("").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("bba").t("ab").build();
        Result result = Result.builder().result("ba").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().s("acbbaca").t("aba").build();
        Result result = Result.builder().result("baca").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate5() {
        Param param = Param.builder().s("abcabdebac").t("cda").build();
        Result result = Result.builder().result("cabd").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = MinimalCoverSubStringSolution.minWindow(param.getS(), param.getT());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
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
    }
}

class MinimalCoverSubStringSolution {

    public static String minWindow(String s, String t) {
        char[] chars = s.toCharArray();
        char[] targets = t.toCharArray();
        int count = targets.length;
        int[] marks = new int[256];
        for (char c : targets) {
            marks[c]--;
        }
        int l = 0, start = 0, len = Integer.MAX_VALUE;
        for (int i = 0; i < chars.length; i++) {
            // 判断当前字符在标记数组中的数量是否达标
            if (++marks[chars[i]] <= 0) {
                count--;
            }
            // 如果所有的字符都已达标
            while (count == 0) {
                // 当前达标的子串长度是否小于上一次达标的子串长度
                if (i - l + 1 < len) {
                    // 是，则进行赋值
                    len = i - l + 1;
                    start = l;
                }
                // 尝试缩小左边窗口
                // 左边的字符在标记数组中的数量等于0，等于0则说明该字符为目标字符，统计数量+1
                // 如果结果不等于0，说明该字符的数量要么超过所需数量，要么不是目标字符。
                if (marks[chars[l]]-- == 0) {
                    count++;
                }
                // 左下标+1
                l++;
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    public static String minWindow1(String s, String t) {
        // 基础数组用来标记哪些元素存在、字符数组用来对这些存在的元素进行数量增减
        int baseLength = 'z' - 'A' + 1;
        int[] baseArr = new int[baseLength];
        int[] charArr = new int[baseLength];
        int index1;
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            index1 = c - 'A';
            charArr[index1]++;
            baseArr[index1]++;
        }
        int countDown = 0, countUp = t.length();
        int index2, index3;
        // 记录两
        int[] sMarkArr = new int[s.length()];
        int inIndex = -1, outIndex = 0;
        int minLeft = 0, minRight = 0;
        int minLength = 100001;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            index2 = c - 'A';
            if (baseArr[index2] != 0) {
                inIndex++;
                sMarkArr[inIndex] = i;
                charArr[index2]--;
                // 判断本次减少是否有效，有效则将记录增加1
                if (charArr[index2] >= 0) {
                    countDown++;
                }
                // 判断统计值是否已到达上限，到达则进行长度判断，以及各种赋值
                if (countDown == countUp) {
                    int tmpLength = sMarkArr[inIndex] - sMarkArr[outIndex] + 1;
                    if (tmpLength <= minLength) {
                        minLeft = sMarkArr[outIndex];
                        minRight = sMarkArr[inIndex];
                        minLength = tmpLength;
                    }
                    outIndex++;
                    // 赋值完毕，对需要弹出的下标进行+1
                    while (outIndex <= inIndex) {
                        char c1 = s.charAt(sMarkArr[outIndex - 1]);
                        index3 = c1 - 'A';
                        // 该下标对应的字符数量+1，再判断+1后是不是小于0
                        charArr[index3]++;
                        // 判断本次增加是否无效，如果无效则不变，有效则countDown-1
                        if (charArr[index3] <= 0) {
                            int tmpLength1 = sMarkArr[inIndex] - sMarkArr[outIndex] + 1;
                            if (tmpLength1 <= minLength) {
                                minLeft = sMarkArr[outIndex];
                                minRight = sMarkArr[inIndex];
                                minLength = tmpLength1;
                            }
                        } else {
                            countDown--;
                            break;
                        }
                        outIndex++;
                    }
                }
            }
        }
        if (minLength != 100001) {
            return s.substring(minLeft, minRight + 1);
        } else {
            return "";
        }
    }
}
