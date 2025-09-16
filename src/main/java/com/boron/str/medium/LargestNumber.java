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

    // 8308,8308,830
    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().nums(new int[] {8308,8308,830}).build();
        Result result = Result.builder().result("83088308830").build();
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

        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
        test(generate4());
//        test(generate5());
//        test(generate6());
    }

}

class LargestNumberSolution {
    public static String largestNumber(int[] nums) {
        int len = nums.length;
        // 将整型数组转为字符串数组
        String[] strNumArr = new String[len];
        for (int i = 0; i < len; i++) {
            strNumArr[i] = String.valueOf(nums[i]);
        }
        // 排序
        List<String> sortedStrNums = new ArrayList<>(len);
        sortedStrNums.add(strNumArr[0]);
        for (int i = 1; i < len; i++) {
            String num1 = strNumArr[i];
            // 二分法排序，降序排序
            // 初始化左端点和右端点
            int left = 0, right = sortedStrNums.size() - 1;
            // 二分点
            int mid = 0;
            // 比较结果
            int compare = 0;
            while (left >= 0 && left <= right) {
                mid = (left + right) / 2;
                String num2 = sortedStrNums.get(mid);
                compare = compare(num1, num2);
                // num1大于num2，则右端点左移
                if (compare == 1) {
                    right = mid - 1;
                } else if (compare == -1) {
                    // num1小于num2，则左端点左移
                    left = mid + 1;
                } else {
                    break;
                }
            }
            // 如果num1小于num2，则将结果添加到中间点后一位。
            if (compare == -1) {
                sortedStrNums.add(mid + 1, num1);
            } else {
                // 如果num1大于等于num2，则将结果添加到中间点
                sortedStrNums.add(mid, num1);
            }
        }
        // 如果排序后，第一个数字为0，则直接返回0
        if (Objects.equals(sortedStrNums.get(0), "0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (String num : sortedStrNums) {
            sb.append(num);
        }
        return sb.toString();
    }

    public static int compare(String num1, String num2) {
        // 将两个字符串合并来看
        // 分别比较 num1-num2 和 num2-num1 的大小
        int len1 = num1.length(), len2 = num2.length();
        long x1 = 0, x2 = 0;
        for (int i = 0; i < len1 + len2; i++) {
            if (i >= len1) {
                x1 = x1 * 10 + (num2.charAt(i - len1) - '0');
            } else {
                x1 = x1 * 10 + (num1.charAt(i) - '0');
            }
            if (i >= len2) {
                x2 = x2 * 10 + (num1.charAt(i - len2) - '0');
            } else {
                x2 = x2 * 10 + (num2.charAt(i) - '0');
            }
            if (x1 > x2) {
                return 1;
            }
            if (x2 > x1) {
                return -1;
            }
        }
        return 0;
    }
}
