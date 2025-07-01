package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *  @description: 311. 稀疏矩阵的乘法 <a href="https://leetcode.cn/problems/sparse-matrix-multiplication/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/1
 * </pre>
 */
public class MatrixMultiply {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        int[][] mat1;
        int[][] mat2;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int[][] results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().mat1(new int[][]{
                {1,0,0},{-1,0,3}
        }).mat2(new int[][]{
                {7,0,0},{0,0,0},{0,0,1}
        }).build();
        Result result = Result.builder().results(new int[][]{
                {7,0,0},{-7,0,3}
        }).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().mat1(new int[][]{
                {1, -5}
        }).mat2(new int[][]{
                {12},{-1}
        }).build();
        Result result = Result.builder().results(new int[][]{
                {17}
        }).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int[][] actualResults = MatrixMultiplySolution.multiply(param.getMat1(), param.getMat2());
        int[][] expectResults = result.getResults();
        boolean compareResult = Arrays.deepEquals(actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
//        test(generate2());
    }
}

class MatrixMultiplySolution {

    public static List<List<Pair<Integer, Integer>>> compressMatrix(int[][] matrix) {
        // 压缩矩阵，排除非零元素
        List<List<Pair<Integer, Integer>>> cm = new ArrayList<>();
        for (int row = 0; row < matrix.length; row++) {
            List<Pair<Integer, Integer>> rowElements = new ArrayList<>();
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] != 0) {
                    rowElements.add(Pair.of(matrix[row][col], col));
                }
            }
            cm.add(rowElements);
        }
        return cm;
    }

    /**
     * 压缩+迭代
     * 核心思想：先将零值排除，再进行计算
     */
    public static int[][] multiply(int[][] mat1, int[][] mat2) {
        int m1 = mat1.length;
        int n2 = mat2[0].length;
        int[][] results = new int[m1][n2];

        // 压缩两个矩阵
        List<List<Pair<Integer, Integer>>> compressMat1 = compressMatrix(mat1);
        List<List<Pair<Integer, Integer>>> compressMat2 = compressMatrix(mat2);

        // 从第一个矩阵的行开始遍历
        for (int row = 0; row < m1; row++) {
            // 获取行的所有非零元素
            List<Pair<Integer, Integer>> rowPairs = compressMat1.get(row);
            // 遍历这些元素
            for (Pair<Integer, Integer> rowPair : rowPairs) {
                // 获取对应的元素以及列
                int element1 = rowPair.getLeft();
                int col1 = rowPair.getRight();
                // mat1的列，对应mat2的行，通过该列获取mat2的元素
                List<Pair<Integer, Integer>> rowPairs2 = compressMat2.get(col1);
                // 遍历第二个矩阵的行元素
                for (Pair<Integer, Integer> rowPair2 : rowPairs2) {
                    // 获取对应的元素以及列
                    int element2 = rowPair2.getLeft();
                    int col2 = rowPair2.getRight();
                    // 计算结果
                    int tmpResult = element1 * element2;
                    // 将结果进行累加
                    results[row][col2] += tmpResult;
                }
            }
        }

        return results;
    }

    /**
     * 直接迭代
     */
    public static int[][] multiply1(int[][] mat1, int[][] mat2) {
        int m1 = mat1.length;
        int n1 = mat1[0].length;
        int m2 = mat2.length;
        int n2 = mat2[0].length;
        int[][] results = new int[m1][n2];

        for (int i = 0; i < m1; i++) {
            for (int j = 0; j < n2; j++) {
                for (int k = 0; k < n1; k++) {
                    results[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }
        return results;
    }
}