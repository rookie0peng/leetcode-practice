package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 533. 孤独像素 II <a href="https://leetcode.cn/problems/lonely-pixel-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/24
 * </pre>
 */
public class FindBlackPixel {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        char[][] picture;
        int target;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().picture(new char[][] {{'W','B','W','B','B','W'},{'W','B','W','B','B','W'},{'W','B','W','B','B','W'},{'W','W','B','W','B','W'}}).target(3).build();
        Result result = Result.builder().result(6).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().picture(new char[][] {{'W','W','B'},{'W','W','B'},{'W','W','B'}}).target(3).build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().picture(new char[][] {{'W','B','W','B','B','W'},{'B','W','B','W','W','B'},{'W','B','W','B','B','W'},{'B','W','B','W','W','B'},{'W','W','W','B','B','W'},{'B','W','B','W','W','B'}}).target(3).build();
        Result result = Result.builder().result(9).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = FindBlackPixelSolution.findBlackPixel(param.getPicture(), param.getTarget());
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
//        test(generate3());
//        test(generate4());
    }
}

class FindBlackPixelSolution {
    public static int findBlackPixel(char[][] picture, int target) {
        // 获取行和列的长度
        int m = picture.length;
        int n = picture[0].length;
        // 分别记录行和列的B数量
        int[] row = new int[m];
        int[] col = new int[n];
        Map<Integer, List<int[]>> rowPoints = new HashMap<>();
        // 遍历picture，记录黑色像素点
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    row[i] += 1;
                    col[j] += 1;
                    // 记录每行的所有点
                    List<int[]> points1 = rowPoints.computeIfAbsent(i, k -> new ArrayList<>());
                    points1.add(new int[] {i, j});
                }
            }
        }
        // 记录所有相同的行
        Map<Integer, Set<Integer>> sameRowMap = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                boolean compareRow = true;
                for (int k = 0; k < n; k++) {
                    if (picture[i][k] != picture[j][k]) {
                        compareRow = false;
                        break;
                    }
                }
                if (compareRow) {
                    Set<Integer> integers1 = sameRowMap.computeIfAbsent(i, k -> new HashSet<>());
                    integers1.add(j);
                    Set<Integer> integers2 = sameRowMap.computeIfAbsent(j, k -> new HashSet<>());
                    integers2.add(i);
                }
            }
        }
        // 遍历行
        int result = 0;
        for (int i = 0; i < m; i++) {
            // 如果行的像素点数量等于target
            if (row[i] == target) {
                // 如果相同的行不等于目标-1（因为加上当前行就等于目标）
                Set<Integer> rows = sameRowMap.get(i);
                if (rows == null || rows.size() != target - 1) {
                    continue;
                }
                // 获取当前行的所有黑色像素点
                List<int[]> points = rowPoints.get(i);
                // 遍历
                for (int[] point : points) {
                    // 如果当前像素点的列像素点数量等于目标，则结果+1
                    if (col[point[1]] == target) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

}
