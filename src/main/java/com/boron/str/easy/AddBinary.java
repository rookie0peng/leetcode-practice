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
 *  @description: 67. 二进制求和 <a href="https://leetcode.cn/problems/add-binary/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/9
 * </pre>
 */
public class AddBinary {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String a;
        String b;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().a("11").b("1").build();
        Result result = Result.builder().result("100").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().a("1010").b("1011").build();
        Result result = Result.builder().result("10101").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = AddBinarySolution.addBinary(param.getA(), param.getB());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

class AddBinarySolution {
    public static String addBinary(String a, String b) {
        int len0 = a.length();
        int len1 = b.length();
        int maxLen = Math.max(len0, len1);
        int carry = 0, curValue = 0;
        int[] resArr = new int[maxLen + 1];
        for (int i = 0; i < maxLen; i++) {
            int i1 = safeGet(a, i);
            int i2 = safeGet(b, i);
            int tmpRes = i1 + i2 + carry;
            carry = tmpRes / 2;
            curValue = tmpRes % 2;
            resArr[i] = curValue;
        }
        if (carry == 1) {
            resArr[maxLen] = 1;
        }
        StringBuilder sb = new StringBuilder();
        if (resArr[resArr.length - 1] == 1) {
            sb.append(1);
        }
        for (int i = resArr.length - 2; i >= 0; i--) {
            sb.append(resArr[i]);
        }
        return sb.toString();
    }

    public static int safeGet(String x, int idx) {
        return idx >= 0 && idx < x.length() ? x.charAt(x.length() - 1 - idx) - 48 : 0;
    }
}
