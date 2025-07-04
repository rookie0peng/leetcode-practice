package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * <pre>
 *  @description: 356. 直线镜像 <a href="https://leetcode.cn/problems/line-reflection/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class IsReflected {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[][] points;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().points(new int[][]{{1,1}, {-1,1}}).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().points(new int[][]{{1,1}, {-1,-1}}).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().points(new int[][]{{0, 0}}).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().points(new int[][]{{-16, 1}, {16, 1}, {16, 1}}).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().points(new int[][]{{0, 0}, {0, -1}}).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsReflectedSolution.isReflected(param.getPoints());
        boolean expectResult = result.isResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
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

class IsReflectedSolution {

    /**
     * 哈希
     * 将所有坐标放入y->set(x)的map中，如果坐标相同，直接去重
     */
    public static boolean isReflected(int[][] points) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        Map<Integer, Set<Integer>> map = new HashMap<>();
        // 遍历所有点，获取x的最左端和最右端
        // 将y->set(x)放入map
        for (int i = 0; i < points.length; i++) {
            minX = Math.min(minX, points[i][0]);
            maxX = Math.max(maxX, points[i][0]);
            map.computeIfAbsent(points[i][1], k -> new HashSet<>()).add(points[i][0]);
        }
        // 计算左右两端的和
        int sumLeftRight = minX + maxX;
        // 遍历map
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            // 取出y对应的x
            Set<Integer> values = entry.getValue();
            // 遍历x
            for (int value : values) {
                // 如果当前x的集合不包含x相对应的另一个x，则返回false
                if (!values.contains(sumLeftRight - value)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 排序后，双指针，从两边向中间遍历、比对
     */
    public static boolean isReflected1(int[][] points) {
        Set<String> set = new HashSet<>();
        List<int[]> sortedPoints = Stream.of(points)
                .filter(ints -> set.add(ints[0] + "-" + ints[1]))
                .sorted(Comparator.comparing((int[] x) -> x[0]).thenComparing(x -> x[1]))
                .toList();
        boolean isOdd = sortedPoints.size() % 2 != 0;
        int mid = sortedPoints.size() / 2;
        List<int[]> leftPoints = sortedPoints.subList(0, isOdd ? mid + 1 : mid);
        List<int[]> rightPoints = sortedPoints
                .subList(mid, sortedPoints.size())
                .stream()
                .sorted(Comparator.comparing((int[] x) -> x[0])
                        .thenComparing(Comparator.comparing((int[] x) -> x[1]).reversed()))
                .toList();

        int left = 0;
        int right = rightPoints.size() - 1;
        boolean needInit = true;
        long initLeft = -1;
        long initRight = -1;
        while (left < leftPoints.size() && right >= 0) {
            int[] leftPoint = leftPoints.get(left);
            int[] rightPoint = rightPoints.get(right);
            if (needInit) {
                initLeft = leftPoint[0];
                initRight = rightPoint[0];
                needInit = false;
            }
            if (leftPoint[1] != rightPoint[1] && leftPoint[0] != rightPoint[0]) {
                return false;
            }
            if (initLeft + initRight != (long) leftPoint[0] + rightPoint[0]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
