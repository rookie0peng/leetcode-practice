package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 49. 字母异位词分组 <a href="https://leetcode.cn/problems/group-anagrams/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/12
 * </pre>
 */
public class GroupAnagrams {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String[] strs;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<List<String>> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().strs(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}).build();
        Result result = Result.builder().results(List.of(
                List.of("bat"),
                List.of("nat","tan"),
                List.of("ate","eat","tea")
                )).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<List<String>> actualResults = GroupAnagramsSolution.groupAnagrams(param.getStrs());
        List<List<String>> actualResults2 = actualResults.stream().map(v -> v.stream().sorted().toList()).sorted(Comparator.comparing(x -> x.isEmpty() ? "1" : x.get(0))).toList();
        List<List<String>> expectResults = result.getResults();
        List<List<String>> expectResults2 = expectResults.stream().map(v -> v.stream().sorted().toList()).sorted(Comparator.comparing(x -> x.isEmpty() ? "1" : x.get(0))).toList();
        boolean compareResult = Objects.equals(actualResults2, expectResults2);
        System.out.println("expectResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
    }
}

class GroupAnagramsSolution {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> results = new HashMap<>(strs.length);
        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String key = new String(charArray);
            List<String> list = results.computeIfAbsent(key, k -> new ArrayList<>());
            list.add(str);
        }
        return new ArrayList<>(results.values());
    }
}