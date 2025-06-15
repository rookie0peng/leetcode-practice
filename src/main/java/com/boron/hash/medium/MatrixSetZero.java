package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;

/**
 * <pre>
 *  @description: 73. 矩阵置零 <a href="https://leetcode.cn/problems/set-matrix-zeroes/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/14
 * </pre>
 */
public class MatrixSetZero {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        int[][] matrix;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int[][] results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().matrix(new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
        }).build();
        Result result = Result.builder().results(new int[][]{
                {1, 0, 1},
                {0, 0, 0},
                {1, 0, 1},
        }).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().matrix(new int[][]{
                {0,1,2,0},
                {3,4,5,2},
                {1,3,1,5}
        }).build();
        Result result = Result.builder().results(new int[][]{
                {0,0,0,0},
                {0,4,5,0},
                {0,3,1,0}
        }).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().matrix(new int[][]{
                {-4,-2147483648,6,-7,0},
                {-8,6,-8,-6,0},
                {2147483647,2,-9,-6,-10}
        }).build();
        Result result = Result.builder().results(new int[][]{
                {0,0,0,0,0},
                {0,0,0,0,0},
                {2147483647,2,-9,-6,0}
        }).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        MatrixSetZeroSolution.setZeroes(param.getMatrix());
        int[][] actualResults = param.getMatrix();
        int[][] expectResults = result.getResults();
        boolean compareResult = Arrays.deepEquals(actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
        test(generate2());
    }
}

class MatrixSetZeroSolution {
    public static void setZeroes(int[][] matrix) {
        boolean _00_row = false;
        boolean _00_column = false;
        int m = matrix.length;
        int n = matrix[0].length;
        if (m == 1 && n == 1) {
            return;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                    if (i == 0) {
                        _00_row = true;
                    }
                    if (j == 0) {
                        _00_column = true;
                    }
                }
            }
        }
        if (m == 1) {
            for (int j = 0; j < n; j++) {
                if (matrix[0][0] == 0) {
                    matrix[0][j] = 0;
                }
            }
        } else if (n == 1) {
            for (int i = 0; i < m; i++) {
                if (matrix[0][0] == 0) {
                    matrix[i][0] = 0;
                }
            }
        } else {
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        continue;
                    }
                    if (matrix[0][j] == 0 || matrix[i][0] == 0) {
                        matrix[i][j] = 0;
                    }
                }
            }
            if (_00_row) {
                for (int j = 1; j < n; j++) {
                    matrix[0][j] = 0;
                }
            }
            if (_00_column) {
                for (int i = 1; i < m; i++) {
                    matrix[i][0] = 0;
                }
            }
        }
    }
}
