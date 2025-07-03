package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * <pre>
 *  @description: 336. 回文对 <a href="https://leetcode.cn/problems/palindrome-pairs/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/2
 * </pre>
 */
public class PalindromePairs {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String[] words;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {

        List<List<Integer>> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().words(new String[]{"abcd","dcba","lls","s","sssll"}).build();
        Result result = Result.builder().results(List.of(List.of(0,1), List.of(1,0), List.of(3, 2), List.of(2, 4))).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().words(new String[]{"abcd","dcba","lls","s","sssll",""}).build();
        Result result = Result.builder().results(List.of(List.of(0,1),List.of(1,0),List.of(3,2),List.of(3,5),List.of(5,3),List.of(2,4))).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().words(new String[]{"a",""}).build();
        Result result = Result.builder().results(List.of(List.of(0,1),List.of(1,0))).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<List<Integer>> actualResults = new PalindromePairsSolution().palindromePairs(param.getWords());
        List<List<Integer>> actualResults1 = actualResults.stream()
                .sorted(Comparator.comparing((List<Integer> inner) -> inner.get(0))
                        .thenComparing(inner -> inner.get(1))).toList();
        List<List<Integer>> expectResults = result.getResults();
        List<List<Integer>> expectResults1 = expectResults.stream()
                .sorted(Comparator.comparing((List<Integer> inner) -> inner.get(0))
                        .thenComparing(inner -> inner.get(1))).toList();
        boolean compareResult = Objects.equals(actualResults1, expectResults1);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResults1, expectResults1);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
    }

}

class PalindromePairsSolution {

    // 选一个质数做进制数
    private static final int BASE = 499;

    // 字符串最大长度
    private static final int MAX_N = 301;

    public static long hashcode(String str) {
        if (str.equals("")) {
            return 0;
        }
        var ans = str.charAt(0) - 'a' + 1L;
        for (int i = 1; i < str.length(); i++) {
            ans = ans * BASE + str.charAt(i) - 'a' + 1;
        }
        return ans;
    }

    /**
     * 哈希
     */
    public List<List<Integer>> palindromePairs1(String[] words) {
        // 存储反转单词的map
        Map<String, Integer> revMap = new HashMap<>();
        // 将反转单词和下标放入map
        for (int i = 0; i < words.length; i++) {
            revMap.put(reverse(words[i]), i);
        }
        // 结果集
        List<List<Integer>> results = new ArrayList<>();
        // 遍历单词
        for (int i = 0; i < words.length; i++) {
            int n = words[i].length();
            // 遍历单词的每个字符，这里需要遍历到等于n
            for (int j = 0; j <= n; j++) {
                // 获取前缀和后缀
                String prefix = words[i].substring(0, j);
                String suffix = words[i].substring(j);
                // 判断是否回文，如果先判断前缀，那么后面就需要使用j!=n排除掉后缀等于word的情况
                if (isPalindrome(prefix)) {
                    int idx;
                    // 因为revMap是反转单词，所以，后缀就是目标
                    // 比如，cxcab，原本要查找的是ba，但是在revMap中就是查找ab，刚好等于后缀
                    if (revMap.containsKey(suffix) && (idx = revMap.get(suffix)) != i) {
                        results.add(List.of(idx, i));
                    }
                }
                if (j != n && isPalindrome(suffix)) {
                    int idx;
                    if (revMap.containsKey(prefix) && (idx = revMap.get(prefix)) != i) {
                        results.add(List.of(i, idx));
                    }
                }
            }
        }
        return results;
    }

    private static String reverse(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    private static boolean isPalindrome(String word) {
        int i = 0, j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i) != word.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }
}

class PalindromePairsSolution1 {
    public static List<List<Integer>> palindromePairs(String[] words) {
        Map<String, Integer> word2IndexMap = new HashMap<>();
        String[] revWords = new String[words.length];
        Map<String, Boolean> isPalindromeMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            word2IndexMap.put(words[i], i);
            revWords[i] = reverse(words[i]);
        }
        List<List<Integer>> results = new ArrayList<>();
        String word, revWord, prefix, suffix, reverseSuffix, reversePrefix;
        for (int i = 0; i < words.length; i++) {
            word = words[i];
            revWord = revWords[i];
            int n = words[i].length();
            for (int j = 0; j < n; j++) {
                prefix = word.substring(0, j);
                suffix = word.substring(j);
                if (isPalindrome(isPalindromeMap, prefix)) {
                    reverseSuffix = revWord.substring(0, n - j);
                    Integer index;
                    if ((index = word2IndexMap.get(reverseSuffix)) != null && index != i) {
                        results.add(List.of(index, i));
                    }
                }
                // 如果 j == n - 1，代表着suffix等于word，这和j==0，prefix等于word重复，因此排除掉
                if (j != n - 1 && isPalindrome(isPalindromeMap, suffix)) {
                    reversePrefix = revWord.substring(0, n - j);
                    Integer index;
                    if ((index = word2IndexMap.get(reversePrefix)) != null && index != i) {
                        results.add(List.of(index, i));
                    }
                }
            }
        }
        return results;
    }

    private static boolean isPalindrome(Map<String, Boolean> isPalindromeMap, String word) {
        Boolean result;
        String hash = getHash(word);
        if ((result = isPalindromeMap.get(hash)) != null) {
            return result;
        }
        result = true;
        int i = 0;
        int j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i) != word.charAt(j)) {
                result = false;
                break;
            }
            i++;
            j--;
        }
        isPalindromeMap.put(word, result);
        return result;
    }

    private static String reverse(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            sb.append(word.charAt(word.length() - 1 - i));
        }
        return sb.toString();
    }

    public static String getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
