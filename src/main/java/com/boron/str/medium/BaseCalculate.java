package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 227. 基本计算器 II <a href="https://leetcode.cn/problems/basic-calculator-ii/submissions/667736806/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/3
 * </pre>
 */
public class BaseCalculate {

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
        Param param = Param.builder().s("3+2*2").build();
        Result result = Result.builder().result(7).build();
        return Pair.of(param, result);
    }

    // 3/2
    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s(" 3/2 ").build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s(" 3+5 / 2 ").build();
        Result result = Result.builder().result(5).build();
        return Pair.of(param, result);
    }

    //"0-2147483647"
    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("0-2147483647").build();
        Result result = Result.builder().result(-2147483647).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = BaseCalculateSolution.calculate(param.getS());
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
    }
}

class BaseCalculateSolution {

    public static int calculate(String s) {
        char[] sArr = s.toCharArray();
        Stack<Long> value = new Stack<>();
        Stack<Integer> ops = new Stack<>();
        int i = 0;
        int len = s.length();
        // 1 代表+，-1 代表-；
        int sign = 1;
        long tmp = 0;
        while (i < len) {
            char c = sArr[i];
            if (c == ' ') {
                i++;
            } else if (c == '+' || c == '-') {
                if (ops.size() == 1) {
                    Long pop1 = value.pop();
                    Long pop2 = value.pop();
                    sign = ops.pop();
                    tmp = cal(pop2, pop1, sign);
                    value.push(tmp);
                }
                ops.push(c == '+' ? 1 : -1);
                i++;
            } else if (c == '*' || c == '/') {
                ops.push(c == '*' ? 2 : -2);
                i++;
            } else {
                tmp = 0;
                while (i < len && sArr[i] >= '0' && sArr[i] <= '9') {
                    tmp = tmp * 10 + (sArr[i] - '0');
                    i++;
                }
                if (!ops.isEmpty()) {
                    sign = ops.peek();
                    if (sign == 2 || sign == -2) {
                        Long pop = value.pop();
                        ops.pop();
                        tmp = cal(pop, tmp, sign);
                    }
                }
                value.push(tmp);
            }
        }
        if (!ops.isEmpty()) {
            Long pop1 = value.pop();
            Long pop2 = value.pop();
            sign = ops.pop();
            tmp = cal(pop2, pop1, sign);
        }
        return (int) tmp;
    }
    
    private static long cal(long num1, long num2, int sign) {
        if (sign == 1) {
            return num1 + num2;
        } else if (sign == -1) {
            return num1 - num2;
        } else if (sign == 2) {
            return num1 * num2;
        } else {
            return num1 / num2;
        }
    }
}
