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
 *  @description: 171. Excel 表列序号 <a href="https://leetcode.cn/problems/excel-sheet-column-number/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/16
 * </pre>
 */
public class TitleToNumber {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String columnTitle;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().columnTitle("A").build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().columnTitle("AB").build();
        Result result = Result.builder().result(28).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().columnTitle("ZY").build();
        Result result = Result.builder().result(701).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = TitleToNumberSolution.titleToNumber(param.getColumnTitle());
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
//        test(generate6());
    }
}

class TitleToNumberSolution {
    public static int titleToNumber(String columnTitle) {
        int len = columnTitle.length();
        char[] titleArr = columnTitle.toCharArray();
        int value = 0;
        for (int i = 0; i < len; i++) {
            value = value * 26 + (titleArr[i] - 'A' + 1);
        }
        return value;
    }
}
