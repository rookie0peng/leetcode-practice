package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 43. 字符串相乘 <a href="https://leetcode.cn/problems/count-and-say/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/2
 * </pre>
 */
public class StrMultiply {
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String num1;
        String num2;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().num1("2").num2("3").build();
        Result result = Result.builder().result("6").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().num1("123").num2("456").build();
        Result result = Result.builder().result("56088").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().num1("123456789").num2("987654321").build();
        Result result = Result.builder().result("121932631112635269").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().num1("6913259244").num2("71103343").build();
        Result result = Result.builder().result("491555843274052692").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().num1("111").num2("222").build();
        Result result = Result.builder().result("24642").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = StrMultiplySolution.multiply(param.getNum1(), param.getNum2());
        String expectResult = result.getResult();
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
//        test(generate3());
        test(generate4());
    }

}

class StrMultiplySolution {

    public static int toInt(char c) {
        int v;
        switch (c) {
            case '0' -> v = 0;
            case '1' -> v = 1;
            case '2' -> v = 2;
            case '3' -> v = 3;
            case '4' -> v = 4;

            case '5' -> v = 5;
            case '6' -> v = 6;
            case '7' -> v = 7;
            case '8' -> v = 8;
            case '9' -> v = 9;
            default -> v = 0;
        }
        return v;
    }

    public static String multiply(String num1, String num2) {
        int len1 = num1.length(), len2 = num2.length();
        if (Objects.equals(num1, "0") || Objects.equals(num2, "0")) {
            return "0";
        }
        int[][] res = new int[len1][len1 + len2];
        for (int i = len1 - 1; i >= 0; i--) {
            char c1 = num1.charAt(i);
            int number1 = toInt(c1);
            int last_b = 0;
            int[] lineRes = new int[len1 + len2];
            for (int j = len2 - 1; j >= 0; j--) {
                char c2 = num2.charAt(j);
                int number2 = toInt(c2);

                int singleMultiply = number1 * number2;
                int a = singleMultiply % 10;
                int b = singleMultiply / 10;
                lineRes[len1 - 1 - i + len2 - 1 - j] = a + last_b;
                last_b = b;
            }
            lineRes[len1 - 1 - i + len2] = last_b;
            res[len1 - 1 - i] = lineRes;
        }
        int[] res2 = new int[len1 + len2];
        int carryBit = 0;
        for (int i = 0; i < len1 + len2; i++) {
            int plus = carryBit;
            for (int j = 0; j < len1; j++) {
                plus += res[j][i];
            }
            int value = plus % 10;
            res2[i] = value;
            carryBit = plus / 10;
        }
        StringBuilder sb = new StringBuilder();
        if (res2[len1 + len2 - 1] != 0) {
            sb.append(res2[len1 + len2 - 1]);
        }
        for (int i = len1 + len2 - 2; i >= 0; i--) {
            sb.append(res2[i]);
        }

        return sb.toString();
    }

//    public static long toInt(String num) {
//        long number = 0;
//        for (int i = num.length() - 1; i >= 0; i--) {
//            char c = num.charAt(i);
//            int i1 = transferChar(c);
//            long a = i1 * (long) Math.pow(10, num.length() - 1 - i);
//            number += a;
//        }
//        return number;
//    }

    public static String toStr(int num) {
        int tmp = num;
        int[] valueArr = new int[2];
        while (tmp > 0) {
            int a = tmp % 10;
            tmp = tmp / 10;
        }
        return "";
    }
}
