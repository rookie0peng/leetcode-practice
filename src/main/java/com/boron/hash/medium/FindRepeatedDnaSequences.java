package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  @description: 187. 重复的DNA序列 <a href="https://leetcode.cn/problems/repeated-dna-sequences/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/24
 * </pre>
 */
public class FindRepeatedDnaSequences {

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
        List<String> result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT").build();
        Result result = Result.builder().result(List.of("AAAAACCCCC","CCCCCAAAAA")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("AAAAAAAAAAA").build();
        Result result = Result.builder().result(List.of("AAAAAAAAAA")).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResult = FindRepeatedDnaSequencesSolution.findRepeatedDnaSequences(param.getS());
        List<String> actualResult1 = actualResult.stream().sorted().toList();
        List<String> expectResult = result.getResult();
        List<String> expectResult1 = expectResult.stream().sorted().toList();
        boolean compareResult = actualResult1.equals(expectResult1);
        System.out.println("actualResult1 vs expectResult1");
        System.out.printf("%s vs %s\n", actualResult1, expectResult1);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
    }
}

class FindRepeatedDnaSequencesSolution {


    /**
     * 哈希法解决
     * @param s
     * @return
     */
    public static List<String> findRepeatedDnaSequences(String s) {
        List<String> results = new ArrayList<>();
        Map<String, Integer> countMap = new HashMap<>();
        // 遍历字符串，对长度为10的各个子串进行标记
        for (int i = 0; i <= s.length() - 10; i++) {
            String substring = s.substring(i, i + 10);
            Integer count = countMap.getOrDefault(substring, 0);
            countMap.put(substring, count + 1);
        }
        // 取出数量大于1的子串
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 1) {
                results.add(entry.getKey());
            }
        }
        return results;
    }
}
