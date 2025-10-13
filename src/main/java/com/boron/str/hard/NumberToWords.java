package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 273. 整数转换英文表示 <a href="https://leetcode.cn/problems/integer-to-english-words/submissions/670323186/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/13
 * </pre>
 */
public class NumberToWords {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int num;
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
                .num(123)
                .build();
        Result result = Result.builder().result("One Hundred Twenty Three").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .num(12345)
                .build();
        Result result = Result.builder().result("Twelve Thousand Three Hundred Forty Five").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .num(1234567)
                .build();
        Result result = Result.builder().result("One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder()
                .num(1000)
                .build();
        Result result = Result.builder().result("One Thousand").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = NumberToWordsSolution.numberToWords(param.getNum());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        System.out.println(Math.pow(10, 15) > Integer.MAX_VALUE);;
//        test(generate0());
//        test(generate1());
//        test(generate2());
        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}


class NumberToWordsSolution {


    private static final String HUNDRED = "Hundred";
    private static final Map<Integer, String> CHUNK_MAP = Map.of(1, "Thousand", 2, "Million", 3, "Billion");
    private static final String[] SINGLE_ARR = new String[] {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    private static final String[] DOUBLE_ARR = new String[] {"Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] DOUBLE_SINGLE_ARR = new String[] {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

    public static String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        int tmp = num;
        int mod, hundred, ten, one;
        List<List<String>> results = new ArrayList<>();
        int k = 0;
        while (tmp > 0) {
            List<String> chunkResults = new ArrayList<>();
            mod = tmp % 1000;
            hundred = mod / 100;
            if (hundred > 0) {
                chunkResults.add(SINGLE_ARR[hundred]);
                chunkResults.add(HUNDRED);
            }
            ten = (mod % 100) / 10;
            one = mod % 10;
            if (ten == 1) {
                chunkResults.add(DOUBLE_SINGLE_ARR[one]);
            } else {
                if (ten > 1) {
                    chunkResults.add(DOUBLE_ARR[ten]);
                }
                if (one > 0) {
                    chunkResults.add(SINGLE_ARR[one]);
                }
            }
            if (k > 0 && !chunkResults.isEmpty()) {
                chunkResults.add(CHUNK_MAP.get(k));
            }
            k++;
            tmp = tmp / 1000;
            if (!chunkResults.isEmpty()) {
                results.add(chunkResults);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = results.size() - 1; i > -1; i--) {
            List<String> chunkResults = results.get(i);
            for (int j = 0; j < chunkResults.size(); j++) {
                sb.append(chunkResults.get(j));
                if (j != chunkResults.size() - 1) {
                    sb.append(' ');
                }
            }
            if (i != 0) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
