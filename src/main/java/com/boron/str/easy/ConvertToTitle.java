package com.boron.str.easy;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  @description: 168. Excel 表列名称 <a href="https://leetcode.cn/problems/excel-sheet-column-title/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/16
 * </pre>
 */
public class ConvertToTitle {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int columnNumber;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().columnNumber(702).build();
        Result result = Result.builder().result("ZZ").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().columnNumber(703).build();
        Result result = Result.builder().result("AAA").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().columnNumber(1).build();
        Result result = Result.builder().result("A").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().columnNumber(28).build();
        Result result = Result.builder().result("AB").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().columnNumber(701).build();
        Result result = Result.builder().result("ZY").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate5() {
        Param param = Param.builder().columnNumber(2147483647).build();
        Result result = Result.builder().result("FXSHRXW").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate6() {
        Param param = Param.builder().columnNumber(676).build();
        Result result = Result.builder().result("YZ").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = ConvertToTitleSolution.convertToTitle(param.getColumnNumber());
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
        test(generate5());
        test(generate6());
    }

}

class ConvertToTitleSolution {

    public static String convertToTitle(int columnNumber) {
        // 26进制，但是和进制位的操作有点区别
        int tmp = columnNumber;
        int divide, remainder;
        List<Integer> results = new ArrayList<>();
        while (tmp > 0) {
            remainder = tmp % 26;
            divide = tmp / 26;
            // 如果余数为0，则需要从左边借一位来填充
            if (remainder == 0) {
                results.add(0, 26);
                tmp = divide - 1;
            } else {
                // 否则，直接写入结果
                results.add(0, remainder);
                tmp = divide;
            }

        }
        // 将数字转为字符
        StringBuilder sb0 = new StringBuilder();
        for (int result : results) {
            sb0.append((char) (result + 'A' - 1));
        }
        return sb0.toString();
    }
}
