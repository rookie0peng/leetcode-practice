package com.boron.hash.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 395. 至少有 K 个重复字符的最长子串 <a href="https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class LongestSubstring {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
        int k;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("aaabb").k(3).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("bbaaacbd").k(3).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("baaabcb").k(3).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = LongestSubstringSolution.longestSubstring2(param.getS(), param.getK());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

//        test(generate0());
//        test(generate1());
        test(generate2());
    }

}

class LongestSubstringSolution {

    /**
     * 分治法
     * 字符串中字符数量少于k的都要排除掉。因此，先将这些字符作为分隔符，再去分别判断各个子串是否满足条件
     */
    public static int longestSubstring2(String s, int k) {
        int n = s.length();
        return dfs2(s, 0, n - 1, k);
    }

    public static int dfs2(String s, int left, int right, int k) {
        // 统计在left到right窗口中，各个字符的数量
        int[] dict = new int[26];
        for (int i = left; i <= right; i++) {
            dict[s.charAt(i) - 'a']++;
        }
        // 查找对应的分隔符
        char split = 0;
        for (int i = 0; i < 26; i++) {
            if (dict[i] > 0 && dict[i] < k) {
                split = (char) (i + 'a');
                break;
            }
        }
        // 如果没找到分隔符，则认为满足条件，直接返回该窗口长度
        if (split == 0) {
            return right - left + 1;
        }
        // 初始化遍历该窗口的下标
        int i = left;
        // 初始化结果
        int result = 0;
        // 遍历该窗口
        while (i <= right) {
            // 如果游标小于等于右端点，且游标对应的字符等于分隔符
            // 则游标+1
            while (i <= right && s.charAt(i) == split) {
                i++;
            }
            // 如果游标大于右端点，直接break
            if (i > right) {
                break;
            }
            // 初始化窗口中的子窗口右端点
            int start = i;
            // 如果游标小于右端点，且游标对应的字符不等于分隔符
            // 则游标+1
            while (i <= right && s.charAt(i) != split) {
                i++;
            }
            // 递归调用dfs，查找该子串的结果
            int length = dfs2(s, start, i - 1, k);
            // 取两个结果的最大值
            result = Math.max(result, length);
        }
        return result;
    }

    /**
     * 滑动窗口
     */
    public static int longestSubstring1(String s, int k) {
        // 字典，记录每个字符对应的数量
        int[] dict = new int[26];
        char[] sArr = s.toCharArray();
        for (char c : sArr) {
            dict[c - 'a']++;
        }
        // 左端点和右端点
        int left = 0, right = 0;
        // 主游标和副游标
        int idx = 0, tmpIdx;
        // 统计窗口的字符种类，统计满足条件的字符种类
        int countChar = 0, countK = 0;
        // 统计该窗口中的每个字符的数量
        int[] char2CountArr = new int[26];
        // 最大长度
        int maxLen = 0;
        // 遍历
        while (idx < s.length()) {
            // 获取该下标的字符
            char c = sArr[idx];
            // 如果该字符的数量小于k
            if (dict[c - 'a'] < k) {
                // 初始化窗口右端点
                right = idx - 1;
                // 遍历窗口中的元素
                while (left <= right) {
                    // 如果左端点的字符数量小于k，则优先移动左端点
                    if (char2CountArr[sArr[left] - 'a'] < k) {
                        left++;
                        tmpIdx = left - 1;
                    } else if (char2CountArr[sArr[right] - 'a'] < k) {
                        // 如果左端点的字符数量小于k，则移动右端点
                        right--;
                        tmpIdx = right + 1;
                    } else {
                        // 否则优先移动左端点
                        left++;
                        tmpIdx = left - 1;
                    }
                    // 获取副下标的字符
                    char c2 = sArr[tmpIdx];
                    // 扣减字典中该字符的数量
                    dict[c2 - 'a']--;
                    // 扣减临时字典中该字符的数量
                    char2CountArr[c2 - 'a']--;
                    // 如果该字符的数量==k-1，则满足k的字符种类-1
                    if (char2CountArr[c2 - 'a'] == k - 1) {
                        countK--;
                    }
                    // 如果该字符的数量==0，则字符种类-1
                    if (char2CountArr[c2 - 'a'] == 0) {
                        countChar--;
                    }
                    // 如果k的数量和字符的种类一致，且大于0，则记录最大长度
                    if (countK == countChar && countK > 0) {
                        maxLen = Math.max(maxLen, right - left + 1);
                    }
                }
                // 将主下标+1，左端点指向下标
                left = ++idx;
                continue;
            }
            // 字符数量+1
            char2CountArr[c - 'a']++;
            // 如果字符数量==1，则字符种类+1
            if (char2CountArr[c - 'a'] == 1) {
                countChar++;
            }
            // 如果字符数量==k，则k数量+1
            if (k == char2CountArr[c - 'a']) {
                countK++;
            }
            // 如果k的数量和字符的种类一致，则记录最大长度
            if (countK == countChar) {
                maxLen = Math.max(maxLen, idx - left + 1);
            }
            // 主下标右移
            idx++;
        }
        return maxLen;
    }
}
