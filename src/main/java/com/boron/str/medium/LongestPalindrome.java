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
        Result result = Result.builder().result("bab").build();
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

/**
 * manacher匹配算法
 */
class LongestPalindromeSolution1 {

    public static String longestPalindrome(String s) {
        // 获取初始长度
        int len0 = s.length();
        // 将初始字符串转为字符数组
        char[] sArr = s.toCharArray();
        // 将字符串的每个字符中间插入#，奇数、偶数字符都转为奇数字符
        // aba -> #a#b#a#, aa -> #a#a#
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for (int i = 0; i < len0; i++) {
            sb.append(sArr[i]);
            sb.append('#');
        }
        // 转为字符串
        String combineStr = sb.toString();
        // 字符串转字符数组
        char[] csr = combineStr.toCharArray();
        // 字符串的长度
        int len = combineStr.length();
        // 记录每个字符的下标和最长回文半径
        int[] sr = new int[len];
        // 回文串的最右端点，中心点
        int right = 0, center = 0;
        for (int i = 0; i < len; i++) {
            // 初始化当前字符的回文半径0
            int r = 0;
            // 如果下标小于某个回文串的最右端点
            if (i < right) {
                // 再次初始化下标
                // 要么是该下标关于中心点的对称点
                // 要么是最右端点减去当前下标
                r = Math.min(sr[center - (center - i)], right - i);
            }
            // 从当前半径往左右扩展
            while (i - r >= 0 && i + r <= len - 1 && csr[i - r] == csr[i + r]) {
                r++;
            }
            // 真实半径等于半径-1，因为前面扩展到不合适的下标才跳出循环
            int realRadius = r - 1;
            // 将真实半径赋值到当前的下标
            sr[i] = realRadius;
            // 如果真实下标+半径等于右端点，如果它大于最右端点，则赋值
            if (realRadius + i > right) {
                right = realRadius + i;
                center = i;
            }
        }
        // 获取最长半径和对应的下标
        int maxRadius = -1;
        int maxI = -1;
        for (int i = 0; i < len; i++) {
            if (sr[i] > maxRadius) {
                maxRadius = sr[i];
                maxI = i;
            }
        }
        // 根据半径和下标获取对应的字符串
        int left1 = (maxI - maxRadius) / 2;
        int right1 = left1 + maxRadius - 1;
        String result = s.substring(left1, right1 + 1);
        return result;
    }

    public static String longestPalindrome1(String s) {
        int len = s.length();
        int start = 0, end = -1;
        StringBuffer t = new StringBuffer("#");
        for (int i = 0; i < len; ++i) {
            t.append(s.charAt(i));
            if (i != len - 1) {
                t.append('#');
            }
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
        // 获取字符串的长度
        int len = s.length();
        // 如果长度为1，直接返回字符串自身
        if (len == 1) {
            return s;
        }
        // 初始化动态规划数组
        boolean[][] dp = new boolean[len][len];
        // 单个字符串都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        // 将字符串转为字符数组，方便读取
        char[] sArr = s.toCharArray();
        // 初始化最大长度为1，单个子串都是回文串
        int maxLen = 1;
        // 初始化最大回文子串
        String palindrome = s.substring(0, 1);
        // 从长度2开始遍历
        for (int L = 2; L <= len; L++) {
            // 从下标0开始遍历
            for (int left = 0; left < len; left++) {
                // 根据左端点计算右端点
                int right = left + L - 1;
                // 如果右端点超长，跳过循环
                if (right >= len) {
                    break;
                }
                // 如果长度小于等于3，只要左端点字符等于右端点字符，该子串就是回文串
                if (L <= 3) {
                    dp[left][right] = sArr[left] == sArr[right];
                } else {
                    // 如果长度大于等于3，则判断中间的子串是否回文串，以及左端点字符是否等于右端点字符
                    dp[left][right] = dp[left + 1][right - 1] && sArr[left] == sArr[right];
                }
                // 如果该子串是回文串，且当前子串的长度大于最大回文串长度，记录该值
                if (dp[left][right] && L > maxLen) {
                    maxLen = L;
                    palindrome = s.substring(left, right + 1);
                }
            }
        }
        return palindrome;
    }
}