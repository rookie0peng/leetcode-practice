package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 166. 分数到小数 <a href="https://leetcode.cn/problems/fraction-to-recurring-decimal/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/22
 * </pre>
 */
public class FractionToDecimal {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int numerator;
        int denominator;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().numerator(1).denominator(2).build();
        Result result = Result.builder().result("0.5").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().numerator(2).denominator(1).build();
        Result result = Result.builder().result("2").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().numerator(4).denominator(333).build();
        Result result = Result.builder().result("0.(012)").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().numerator(1).denominator(333).build();
        Result result = Result.builder().result("0.(003)").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().numerator(-50).denominator(8).build();
        Result result = Result.builder().result("-6.25").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate5() {
        Param param = Param.builder().numerator(1).denominator(-1).build();
        Result result = Result.builder().result("-1").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate6() {
        Param param = Param.builder().numerator(1).denominator(214748364).build();
        Result result = Result.builder().result("0.00(000000465661289042462740251655654056577585848337359161441621040707904997124914069194026549138227660723878669455195477065427143370461252966751355553982241280310754777158628319049732085502639731402098131932683780538602845887105337854867197032523144157689601770377165713821223802198558308923834223016478952081795603341592860749337303449725)").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate7() {
        Param param = Param.builder().numerator(-1).denominator(-2147483648).build();
        Result result = Result.builder().result("0.0000000004656612873077392578125").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate8() {
        Param param = Param.builder().numerator(7).denominator(-12).build();
        Result result = Result.builder().result("-0.58(3)").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate9() {
        Param param = Param.builder().numerator(-2147483648).denominator(-1).build();
        Result result = Result.builder().result("2147483648").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = FractionToDecimalSolution.fractionToDecimal(param.getNumerator(), param.getDenominator());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
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
        test(generate7());
        test(generate8());
        test(generate9());
    }
}

class FractionToDecimalSolution {
    public static String fractionToDecimal(int numerator, int denominator) {
        // 使用long是怕int数值溢出
        long numerator1 = numerator;
        long denominator1 = denominator;
        long quotient = numerator1 / denominator1;
        long remainder = numerator1 % denominator1;
        // 如果余数为0直接返回
        if (remainder == 0) {
            return String.valueOf(quotient);
        }
        boolean needFlag = false;
        // 判断两个数的符号是否相同
        if ((numerator1 < 0 && denominator1 > 0) || (numerator1 > 0 && denominator1 < 0)) {
            numerator1 = Math.abs(numerator1);
            denominator1 = Math.abs(denominator1);
            needFlag = true;
        }
        long quotient1 = numerator1 / denominator1;
        long remainder1 = numerator1 % denominator1;
        long tmpQuotient = -1;
        long tmpRemainder = remainder1, nextRemainder = -1, preRemainder = -1;
        List<Long> quotients = new ArrayList<>();
        Set<Long> remainderSet = new HashSet<>();
        List<Long> remainders = new ArrayList<>();
        boolean hasCycle = false;
        // 构建该数值的小数部分
        while (true) {
            preRemainder = tmpRemainder;
            nextRemainder = tmpRemainder * 10;
            tmpQuotient = nextRemainder / denominator1;
            tmpRemainder = nextRemainder % denominator1;
            remainderSet.add(preRemainder);
            remainders.add(preRemainder);
            quotients.add(tmpQuotient);
            // 如果余数重复，则说明该数值是无限循环小数
            if (remainderSet.contains(tmpRemainder)) {
                hasCycle = true;
                break;
            }
            if (tmpRemainder == 0) {
                break;
            }
            tmpRemainder = tmpQuotient > 0 ? tmpRemainder : nextRemainder;
        }
        StringBuilder resultSB = new StringBuilder();
        if (needFlag) {
            resultSB.append('-');
        }
        resultSB.append(quotient1);
        resultSB.append('.');
        // 有循环、则需要补充括号
        if (hasCycle) {
            for (int i = 0; i < quotients.size(); i++) {
                if (Objects.equals(remainders.get(i), tmpRemainder)) {
                    resultSB.append('(');
                }
                resultSB.append(quotients.get(i));
            }
            resultSB.append(')');
        } else {
            for (Long value : quotients) {
                resultSB.append(value);
            }
        }
        return resultSB.toString();
    }
}
