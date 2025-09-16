package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 179. 最大数 <a href="https://leetcode.cn/problems/largest-number/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/16
 * </pre>
 */
public class LargestNumber {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {10, 2}).build();
        Result result = Result.builder().result("210").build();
        return Pair.of(param, result);
    }

    // 3,30,34,5,9
    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {3,30,34,5,9}).build();
        Result result = Result.builder().result("9534330").build();
        return Pair.of(param, result);
    }

    // 3,30,34,5,9
    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {34323,3432}).build();
        Result result = Result.builder().result("343234323").build();
        return Pair.of(param, result);
    }

    //10,2,9,39,17
    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().nums(new int[] {10,2,9,39,17}).build();
        Result result = Result.builder().result("93921710").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = LargestNumberSolution.largestNumber(param.getNums());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

//        test(generate0());
//        test(generate1());
//        test(generate2());
        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }

}

class LargestNumberSolution {
    public static String largestNumber(int[] nums) {
        int len = nums.length;
        List<String> strNums = new ArrayList<>(len);
        for (int num : nums) {
            strNums.add(String.valueOf(num));
        }
        List<String> sortedStrNums = new ArrayList<>(len);
        sortedStrNums.add(strNums.get(0));
        for (int i = 1; i < len; i++) {
            String num1 = strNums.get(i);
            // 二分法排序
            int left = 0, right = sortedStrNums.size();
            int mid = (left + right) / 2;
            while (left <= right) {
                String num2 = sortedStrNums.get(mid);
                int maxLen = Math.max(num1.length(), num2.length());
                int minLen = Math.min(num1.length(), num2.length());
                int compare = dfsCompare(num1, num2, maxLen, minLen, 0);
                if (compare == 1) {
                    right = mid - 1;
                } else if (compare == -1) {
                    left = mid + 1;
                } else {
                    break;
                }
                mid = (left + right) / 2;
            }
            sortedStrNums.add(mid, num1);
        }
        StringBuilder sb = new StringBuilder();
        for (String num : sortedStrNums) {
            sb.append(num);
        }
        return sb.toString();
    }

    public static int dfsCompare(String num1, String num2, int maxLen, int minLen, int step) {
        int i = 0;
        boolean _1less_2 = num1.length() < num2.length();
        int right = Math.min(minLen, maxLen - step);
        char c1, c2;
        int idx1, idx2;
        while (i < right) {
            if (_1less_2) {
                idx1 = i;
                idx2 = i + step;
            } else {
                idx1 = i + step;
                idx2 = i;
            }
            c1 = num1.charAt(idx1);
            c2 = num2.charAt(idx2);
            if (c1 > c2) {
                return 1;
            }
            if (c1 < c2) {
                return -1;
            }
            i++;
        }
        if (maxLen > step + right) {
            return dfsCompare(num1, num2, maxLen, minLen, step + minLen);
        } else if (maxLen == step + right) {
            int j = 0;
            int idx3, idx4;
            char c3, c4;
            while (j < minLen - right) {
                if (_1less_2) {
                    idx3 = j;
                    idx4 = right + j;
                } else {
                    idx3 = right + j;
                    idx4 = j;
                }
                c3 = num1.charAt(idx3);
                c4 = num2.charAt(idx4);
                if (c3 > c4) {
                    return 1;
                }
                if (c3 < c4) {
                    return 1;
                }
                j++;
            }
        }
        return 0;
    }
}
