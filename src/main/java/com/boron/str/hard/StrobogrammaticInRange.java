package com.boron.str.hard;

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
 *  @description: 248. 中心对称数 III <a href="https://leetcode.cn/problems/strobogrammatic-number-iii/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/6
 * </pre>
 */
public class StrobogrammaticInRange {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String low;
        String high;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .low("50")
                .high("100")
                .build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .low("0")
                .high("0")
                .build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .low("0")
                .high("9")
                .build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder()
                .low("0")
                .high("1680")
                .build();
        Result result = Result.builder().result(21).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = StrobogrammaticInRangeSolution.strobogrammaticInRange(param.getLow(), param.getHigh());
        int expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        System.out.println(Math.pow(10, 15) > Integer.MAX_VALUE);;
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}


class StrobogrammaticInRangeSolution {

    private static final char[] SINGLE_PAIR_NUM = new char[] {
            '0', '1', '8'
    };

    private static final char[][] DOUBLE_PAIR_NUM = new char[][] {
            {'0', '0'},
            {'1', '1'},
            {'6', '9'},
            {'8', '8'},
            {'9', '6'},
    };

    private static boolean IS_ODD = false;


    public static int strobogrammaticInRange(String low, String high) {
        int minLen = low.length();
        int maxLen = high.length();
        long lowValue = Long.parseLong(low);
        long highValue = Long.parseLong(high);
        int minNo = 0, midNo = 0, maxNo = 0;
        // 分两种情况计算
        // 如果low的长度小于high的长度，则需要计算 minNo, midNo, maxNo
        // 如果low的长度等于high的长度，则只需要计算 minNo 即可，且计算方式有所区别
        if (minLen < maxLen) {
            List<String> results = new ArrayList<>();
            char[] tmpArr = new char[minLen];
            IS_ODD = minLen % 2 == 1;
            dfs(minLen / 2, 0, tmpArr, results);
            for (int i = 0; i < results.size(); i++) {
                long x = Long.parseLong(results.get(i));
                if (x >= lowValue) {
                    minNo = results.size() - i;
                    break;
                }
            }
            List<String> results2 = new ArrayList<>();
            char[] tmpArr2 = new char[maxLen];
            IS_ODD = maxLen % 2 == 1;
            dfs(maxLen / 2, 0, tmpArr2, results2);
            for (int i = results2.size() - 1; i >= 0; i--) {
                if (Long.parseLong(results2.get(i)) <= highValue) {
                    maxNo = i + 1;
                    break;
                }
            }
            int tmp = 0;
            for (int i = minLen + 1; i < maxLen; i++) {
                if (i % 2 == 1) {
                    tmp = tmp + 4 * (int) Math.pow(5, (double) (i / 2 - 1)) * 3;
                } else {
                    tmp = tmp + 4 * (int) Math.pow(5, (double) (i / 2 - 1));
                }
            }
            midNo = tmp;
        } else {
            List<String> results = new ArrayList<>();
            char[] tmpArr = new char[minLen];
            IS_ODD = minLen % 2 == 1;
            dfs(minLen / 2, 0, tmpArr, results);
            for (String result : results) {
                long x = Long.parseLong(result);
                if (x >= lowValue && x <= highValue) {
                    minNo += 1;
                }
            }
        }
        int allNo = minNo + midNo + maxNo;
        return allNo;
    }

    private static void dfs(int right, int idx, char[] tmpArr, List<String> results) {
        if (IS_ODD) {
            if (idx > right) {
                results.add(new String(tmpArr));
                return;
            } else if (idx == right) {
                for (char c : SINGLE_PAIR_NUM) {
                    tmpArr[idx] = c;
                    dfs(right, idx + 1, tmpArr, results);
                    tmpArr[idx] = '-';
                }
                return;
            }
        } else {
            if (idx >= right) {
                results.add(new String(tmpArr));
                return;
            }
        }
        for (char[] cr : DOUBLE_PAIR_NUM) {
            if (idx == 0 && cr[0] == '0') {
                continue;
            }
            tmpArr[idx] = cr[0];
            tmpArr[tmpArr.length - 1 - idx] = cr[1];
            dfs(right, idx + 1, tmpArr, results);
            tmpArr[idx] = '-';
            tmpArr[tmpArr.length - 1 - idx] = '-';
        }
    }
}
