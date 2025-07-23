package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *  @description: 531. 孤独像素 I <a href="https://leetcode.cn/problems/lonely-pixel-i/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/23
 * </pre>
 */
public class FindLonelyPixel {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        char[][] picture;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().picture(new char[][] {{'W','W','B'},{'W','B','W'},{'B','W','W'}}).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().picture(new char[][] {{'B','B','B'},{'B','B','W'},{'B','B','B'}}).build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = new FindLonelyPixelSolution().findLonelyPixel(param.getPicture());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

class FindLonelyPixelSolution {

    public int findLonelyPixel(char[][] picture) {
        // 获取二维数组的长和宽
        int m = picture.length;
        int n = picture[0].length;
        // 记录每行、列的点的个数
        int[] row = new int[m];
        int[] col = new int[n];
        int result = 0;
        int[][] rowPoints = new int[m][2];
        // 遍历像素表
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果为黑色像素，则对应的行和列点数量+1，并且记录对应的点
                if (picture[i][j] == 'B') {
                    row[i] += 1;
                    col[j] += 1;
                    rowPoints[i] = new int[] {i, j};
                }
            }
        }
        // 遍历行
        for (int i = 0; i < m; i++) {
            // 如果行的点的数量为1
            if (row[i] == 1) {
                // 获取行内唯一像素点
                int[] point = rowPoints[i];
                // 如果该像素点所在列的点的数量为1
                if (col[point[1]] == 1) {
                    // 结果+1
                    result += 1;
                }
            }
        }
        return result;
    }

}
