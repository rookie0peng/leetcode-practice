package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.Stack;

/**
 * <pre>
 *  @description: 32. 最长有效括号 <a href="https://leetcode.cn/problems/longest-valid-parentheses/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/8/21
 * </pre>
 */
public class LongestValidParentheses {

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
        Param param = Param.builder().s("(()").build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s(")()())").build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("").build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().s("()(()").build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().s("()(())").build();
        Result result = Result.builder().result(6).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = LongestValidParenthesesSolution.longestValidParentheses(param.getS());
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
//        test(generate5());

    }

}

/**
 * 动态规划
 */
class LongestValidParenthesesSolution {



    public static int longestValidParentheses(String s) {
        // 初始化动态规划数组
        int[] dp = new int[s.length()];
//        char[] sArr = s.toCharArray();
        int maxLen = 0;
        // 遍历s的所有字符
        for (int i = 1; i < s.length(); i++) {
            // 如果是闭括号
            if (s.charAt(i) == ')') {
                // 查看前一个是否开括号
                if (s.charAt(i - 1) == '(') {
                    // 如果是开括号，则dp[i]等于dp[i-2]+2，需要判断i-2是否大于等于0
                    dp[i] = (i - 2 >= 0 ? dp[i - 2] : 0) + 2;
                } else {
                    // 如果是闭括号，则继续判断i是否大于dp[i-1]，因为dp[i-1]的最大值为i，如果dp[i-1]==i，那么意味着没有空余的位置放置多余的闭括号了。
                    // 所以必须是i大于dp[i-1]。
                    // 再查看i关于i-1对称的位置是否是开括号，如果是，则说明从这个点到i的子串是一个合法的括号
                    if (i > dp[i - 1] && s.charAt(i - dp[i - 1] - 1) == '(') {
                        // dp[i] = dp[i-1]+2+dp[i - dp[i - 1] - 1 - 1]，需要判断i - dp[i - 1] - 2大于等于0
                        dp[i] = dp[i - 1] + (i - dp[i - 1] - 2 >= 0 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                    }
                }
            }
            // 每一次遍历都计算一次最大值。
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

}

/**
 * Stack
 */
class LongestValidParenthesesSolution1 {



    public static char pairBracket(char c) {
        return switch (c) {
            case '(' -> ')';
//            case '[' -> ']';
//            case '{' -> '}';
            default -> '3';
        };
    }

    public static boolean isLeftBracket(char c) {
        return c == '(';
//        return c == '(' || c == '[' || c == '{';
    }

    public static int longestValidParentheses(String s) {
        Stack<int[]> stack = new Stack<>();
        char[] sArr = s.toCharArray();
        int[] markArr = new int[s.length()];
        for (int i = 0; i < sArr.length; i++) {
            char curC = sArr[i];
            if (stack.isEmpty() || isLeftBracket(curC)) {
                stack.push(new int[] {i, curC});
            } else {
                int[] peek = stack.peek();
                char lastC = (char) peek[1];
                char lastPairC = pairBracket(lastC);

                if (curC == lastPairC) {
                    markArr[i] = 1;
                    markArr[peek[0]] = 1;
                    stack.pop();
                } else {
                    stack.clear();
                }
            }
        }
        boolean enter = false;
        int left = -1, right = -1;
        int maxLen = 0;
        for (int i = 0; i < markArr.length; i++) {
            if (markArr[i] == 1) {
                if (!enter) {
                    enter = true;
                    left = i;
                }
            } else {
                if (enter) {
                    right = i - 1;
                    maxLen = Math.max(maxLen, right - left + 1);
                    enter = false;
                    left = right = -1;
                }
            }
        }
        if (enter) {
            right = s.length() - 1;
            maxLen = Math.max(maxLen, right - left + 1);
            enter = false;
            left = right = -1;
        }

        return maxLen;
    }
}
