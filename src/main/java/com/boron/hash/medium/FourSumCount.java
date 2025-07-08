package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  @description: 454. 四数相加 II <a href="https://leetcode.cn/problems/4sum-ii/solutions/499745/si-shu-xiang-jia-ii-by-leetcode-solution/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/8
 * </pre>
 */
public class FourSumCount {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums1;
        int[] nums2;
        int[] nums3;
        int[] nums4;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums1(new int[] {1, 2}).nums2(new int[] {-2,-1}).nums3(new int[] {-1,2}).nums4(new int[] {0,2}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = FourSumCountSolution.fourSumCount(param.getNums1(), param.getNums2(), param.getNums3(), param.getNums4());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
    }
}

class FourSumCountSolution {

    /**
     * 分组哈希
     */
    public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // 获取数组长度
        int n = nums1.length;
        // map(-(sum3 + sum4) -> count)
        // 存负数是因为，x+y=0，那么从map中查找时需要查找-y
        Map<Integer, Integer> countSumMap = new HashMap<>();
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < n; l++) {
                int key = -(nums3[k] + nums4[l]);
                countSumMap.put(key, countSumMap.getOrDefault(key, 0) + 1);
            }
        }
        // 遍历nums1和nums2，然后从map中查找是否有对应的值
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int key = nums1[i] + nums2[j];
                count += countSumMap.getOrDefault(key, 0);
            }
        }
        return count;
    }
}
