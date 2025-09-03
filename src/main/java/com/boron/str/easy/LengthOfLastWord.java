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
 *  @description: 58. 最后一个单词的长度 <a href="https://leetcode.cn/problems/length-of-last-word/submissions/659170935/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/3
 * </pre>
 */
public class LengthOfLastWord {
    
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
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("   fly me   to   the moon  ").build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("luffy is still joyboy").build();
        Result result = Result.builder().result(6).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s(" ").build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("123").build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = LengthOfLastWordSolution.lengthOfLastWord(param.getS());
        int expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
//        test(generate2());
        test(generate3());
//        test(generate4());
    }

}

class LengthOfLastWordSolution {

    public static int lengthOfLastWord(String s) {
        boolean enter = false;
        int start = 0, end = 0;
        int lastLen = 0;
        int len = s.length();
        for (int i = len - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                if (enter) {
                    end--;
                } else {
                    enter = true;
                    start = i;
                    end = i;
                }
            } else {
                if (enter) {
                    lastLen = start - end + 1;
                    enter = false;
                    start = 0;
                    end = 0;
                    break;
                }
            }
        }
        if (enter) {
            lastLen = start - end + 1;
        }
        return lastLen;
    }
}
