package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  @description: 532. 数组中的 k-diff 数对 <a href="https://leetcode.cn/problems/k-diff-pairs-in-an-array/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/24
 * </pre>
 */
public class FindPairs {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums;
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
        Param param = Param.builder().nums(new int[] {3, 1, 4, 1, 5}).k(2).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {1, 2, 3, 4, 5}).k(1).build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {1, 3, 1, 5, 4}).k(0).build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().nums(new int[] {1,2,4,4,3,3,0,9,2,3}).k(3).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().nums(new int[] {-1,-2,-3}).k(1).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = FindPairsSolution.findPairs(param.getNums(), param.getK());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
        test(generate4());
    }
}

class FindPairsSolution {

    public static int findPairs(int[] nums, int k) {
        // 存储访问过的数字
        Set<Integer> visited = new HashSet<>();
        // 存储结果集数字，只存左端点
        Set<Integer> results = new HashSet<>();
        // 遍历nums数组
        for (int num : nums) {
            // 如果访问集包含num-k，则结果集添加num-k
            if (visited.contains(num - k)) {
                results.add(num - k);
            }
            // 如果访问集包含num+k，则结果集添加num
            if (visited.contains(num + k)) {
                results.add(num);
            }
            visited.add(num);
        }
        // 返回结果集的大小
        return results.size();
    }

    public static int findPairs1(int[] nums, int k) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int num : nums) {
            numMap.put(num, numMap.getOrDefault(num, 0) + 1);
        }
        Set<Integer> traceNums = new HashSet<>();
        int result = 0;
        Set<Integer> numSets = numMap.keySet();
        if (k == 0) {
            for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
                Integer value = entry.getValue();
                if (value >= 2) {
                    result += 1;
                }
            }
            return result;
        }
        for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
            int num = entry.getKey();
            int addK = num + k;
            int divideK = num - k;
            if (!traceNums.contains(addK) && numSets.contains(addK)) {
                result += 1;
            }
            if (addK != divideK && !traceNums.contains(divideK) && numSets.contains(divideK)) {
                result += 1;
            }
            traceNums.add(num);
        }
        return result;
    }
}
