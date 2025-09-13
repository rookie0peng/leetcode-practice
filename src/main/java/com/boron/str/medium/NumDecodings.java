package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *  @description: 91. 解码方法 <a href="https://leetcode.cn/problems/decode-ways/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/13
 * </pre>
 */
public class NumDecodings {

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
        Param param = Param.builder().s("12").build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("226").build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("06").build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("2611055971756562").build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().s("261017").build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate5() {
        Param param = Param.builder().s("1111").build();
        Result result = Result.builder().result(5).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate6() {
        Param param = Param.builder().s("11111").build();
        Result result = Result.builder().result(8).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate7() {
        Param param = Param.builder().s("111111").build();
        Result result = Result.builder().result(13).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate8() {
        Param param = Param.builder().s("11110").build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = NumDecodingsSolution.numDecodings(param.getS());
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
        test(generate3());
        test(generate4());
        test(generate5());
        test(generate6());
        test(generate7());
        test(generate8());
    }
    
}

class NumDecodingsSolution {

    static int MIN = 1;
    static int MAX = 26;


    /**
     * 以下为普通规律，dp[i + 1] = dp[i] + dp[i - 1]
     * 11
     * 1,2
     * 111
     * 1,2,3
     * 1111
     * 1,2,3,5
     * 11111
     * 1,2,3,5,8
     * ------------------------------
     * 以下为出现大于2的数字的规律，dp[i + 1] = dp[i] || dp[i + 1] == dp[i] + dp[i - 1]
     * 1115=5
     * 11155=5
     * 111515=10
     * 1115151=10
     * 11151515=20
     * ------------------------------
     * 以下为含0规律，dp[i + 1] = dp[i - 1]
     * 110
     * 1,2,1
     * 1110
     * 1,2,3,2
     * 11110
     * 1,2,3,5,3
     */
    public static int numDecodings(String s) {
        // 如果第一个字符为 0 ，则直接返回 0
        if (s.charAt(0) == '0') {
            return 0;
        }
        // 初始化动态规划表
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        int base = 1;
        int tmp = calSlice(s, dp);
        base *= tmp;
        return base;
    }

    public static int calSlice(String slice, int[] dp) {
        // 初始化第一个元素的dp
        dp[1] = 1;
        for (int i = 1; i < slice.length(); i++) {
            // 获取当前元素和前一个元素
            char lastC = slice.charAt(i - 1);
            char curC = slice.charAt(i);
            // 如果当前元素为 0，
            if (curC == '0') {
                // 如果前一个元素为0，或者大于2，则直接返回0
                if (lastC == '0' || lastC > '2') {
                    return 0;
                } else {
                    // 否则，将i-1的值赋值给i+1。
                    dp[i + 1] = dp[i - 1];
                }
            } else {
                // 如果当前元素不为0
                // 且，前一个元素为0，或者前一个元素大于2，或者前一个元素等于2并且当前元素大于6
                if (lastC == '0' || lastC > '2' || (lastC == '2' && curC > '6')) {
                    // 将i的值赋值给i+1
                    dp[i + 1] = dp[i];
                } else {
                    // 否则
                    dp[i + 1] = dp[i] + dp[i - 1];
                }
            }
        }
        // 返回dp表中最后一个元素
        return dp[slice.length()];
    }

}

/**
 * 深度优先搜索，超时
 */
class NumDecodingsSolution1 {

    static int MIN = 1;
    static int MAX = 26;


    public static int numDecodings(String s) {
        AtomicInteger res = new AtomicInteger(0);
        dfs(s, 0, 1, res);
        dfs(s, 0, 2, res);
        return res.get();
    }

    public static int dfs(String str, int idx, int len, AtomicInteger result) {
        if (len == 1 && str.charAt(idx) == '0') {
            return -1;
        }
        if (len == 2) {
            if (idx == str.length() - 1) {
                return 1;
            }
            char c1 = str.charAt(idx);
            char c2 = str.charAt(idx + 1);
            if (c1 == '0' || c1 > '2' || (c1 == '2' && c2 > '6')) {
                return -1;
            }
        }
        if (idx == str.length() - 1 && len == 1) {
            result.addAndGet(1);
            return 1;
        }
        if (idx == str.length() - 2 && len == 2) {
            result.addAndGet(1);
            return 1;
        }
        int dfs1 = dfs(str, idx + len, 1, result);
        int dfs2 = dfs(str, idx + len, 2, result);
        if (dfs1 == -1 && dfs2 == -1) {
            return -1;
        }
        return 0;
    }
}
