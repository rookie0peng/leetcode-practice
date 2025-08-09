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

/**
 * <pre>
 *  @description: 5.最长回文子串 <a href="https://leetcode.cn/problems/longest-palindromic-substring/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/8/7
 * </pre>
 */
public class LongestPalindrome {

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
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("babad").build();
        Result result = Result.builder().result("").build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = LongestPalindromeSolution1.longestPalindrome(param.getS());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

        test(generate0());
    }
}

class LongestPalindromeSolution1 {
    public static String longestPalindrome(String s) {
        int start = 0, end = -1;
        StringBuffer t = new StringBuffer("#");
        for (int i = 0; i < s.length(); ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        t.append('#');
        s = t.toString();

        List<Integer> arm_len = new ArrayList<Integer>();
        int right = -1, j = -1;
        for (int i = 0; i < s.length(); ++i) {
            int cur_arm_len;
            if (right >= i) {
                int i_sym = j * 2 - i;
                int min_arm_len = Math.min(arm_len.get(i_sym), right - i);
                cur_arm_len = expand(s, i - min_arm_len, i + min_arm_len);
            } else {
                cur_arm_len = expand(s, i, i);
            }
            arm_len.add(cur_arm_len);
            if (i + cur_arm_len > right) {
                j = i;
                right = i + cur_arm_len;
            }
            if (cur_arm_len * 2 + 1 > end - start) {
                start = i - cur_arm_len;
                end = i + cur_arm_len;
            }
        }

        StringBuffer ans = new StringBuffer();
        for (int i = start; i <= end; ++i) {
            if (s.charAt(i) != '#') {
                ans.append(s.charAt(i));
            }
        }
        return ans.toString();
    }

    public static int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return (right - left - 2) / 2;
    }
}


/**
 * 动态规划
 */
class LongestPalindromeSolution {
    /**
     * dp[i][i] = true
     * dp[i][j] = dp[i+1][j-1]&&S[i]==S[j]
     * S.length() == 2 && S[i]==S[j]
     */
    public static String longestPalindrome(String s) {
        int len = s.length();
        if (len == 1) {
            return s;
        }
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        char[] sArr = s.toCharArray();
        int maxLen = 1;
        String palindrome = s.substring(0, 1);
        for (int L = 2; L <= len; L++) {
            for (int left = 0; left < len; left++) {
                int right = left + L - 1;
                if (right >= len) {
                    break;
                }
                if (L <= 3) {
                    dp[left][right] = sArr[left] == sArr[right];
                } else {
                    dp[left][right] = dp[left + 1][right - 1] && sArr[left] == sArr[right];
                }
                if (dp[left][right] && L > maxLen) {
                    maxLen = L;
                    palindrome = s.substring(left, right + 1);
                }
            }
        }
        return palindrome;
    }
}