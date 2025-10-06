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
 *  @description: 247. 中心对称数 II <a href="https://leetcode.cn/problems/strobogrammatic-number-ii/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/6
 * </pre>
 */
public class FindStrobogrammatic {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int n;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<String> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .n(2)
                .build();
        Result result = Result.builder().results(List.of("11","69","88","96")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .n(1)
                .build();
        Result result = Result.builder().results(List.of("0","1","8")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .n(3)
                .build();
        Result result = Result.builder().results(List.of("101","111","181","609","619","689","808","818","888","906","916","986")).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResult = FindStrobogrammaticSolution.findStrobogrammatic(param.getN());
        List<String> expectResult = result.getResults();
        List<String> actualResult1 = actualResult.stream().sorted().toList();
        List<String> expectResult1 = expectResult.stream().sorted().toList();
        boolean compareResult = Objects.equals(actualResult1, expectResult1);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult1), JSON.toJSONString(expectResult1));
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

class FindStrobogrammaticSolution {

    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 0
    private static final char[] SINGLE_PAIR_NUM = new char[] {
            '0', '1', '8'
    };

    private static final char[][] DOUBLE_PAIR_NUM = new char[][] {
            {'1', '1'},
            {'6', '9'},
            {'8', '8'},
            {'9', '6'},
            {'0', '0'}
    };

    private static boolean IS_ODD = false;

    public static List<String> findStrobogrammatic(int n) {
        if (n == 1) {
            return List.of("0", "1", "8");
        }
        IS_ODD = n % 2 == 1;
        char[] tmpArr = new char[n];
        List<String> results = new ArrayList<>();
        dfs(n / 2, 0, tmpArr, results);
        return results;
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
