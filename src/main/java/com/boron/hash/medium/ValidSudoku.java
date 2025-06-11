package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 36. 有效的数独 <a href="https://leetcode.cn/problems/valid-sudoku/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/10
 * </pre>
 */
public class ValidSudoku {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        char[][] board;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().board(new char[][] {
                {'5','3','.','.','7','.','.','.','.'}
                ,{'6','.','.','1','9','5','.','.','.'}
                ,{'.','9','8','.','.','.','.','6','.'}
                ,{'8','.','.','.','6','.','.','.','3'}
                ,{'4','.','.','8','.','3','.','.','1'}
                ,{'7','.','.','.','2','.','.','.','6'}
                ,{'.','6','.','.','.','.','2','8','.'}
                ,{'.','.','.','4','1','9','.','.','5'}
                ,{'.','.','.','.','8','.','.','7','9'}
        }).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().board(new char[][] {
                {'8','3','.','.','7','.','.','.','.'}
                ,{'6','.','.','1','9','5','.','.','.'}
                ,{'.','9','8','.','.','.','.','6','.'}
                ,{'8','.','.','.','6','.','.','.','3'}
                ,{'4','.','.','8','.','3','.','.','1'}
                ,{'7','.','.','.','2','.','.','.','6'}
                ,{'.','6','.','.','.','.','2','8','.'}
                ,{'.','.','.','4','1','9','.','.','5'}
                ,{'.','.','.','.','8','.','.','7','9'}
        }).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().board(new char[][] {
                {'.','.','5','.','.','.','.','.','.'},
                {'1','.','.','2','.','.','.','.','.'},
                {'.','.','6','.','.','3','.','.','.'},
                {'8','.','.','.','.','.','.','.','.'},
                {'3','.','1','5','2','.','.','.','.'},
                {'.','.','.','.','.','.','.','4','.'},
                {'.','.','6','.','.','.','.','.','.'},
                {'.','.','.','.','.','.','.','9','.'},
                {'.','.','.','.','.','.','.','.','.'}
        }).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = ValidSudokuSolution.isValidSudoku(param.getBoard());
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
    }

}

class ValidSudokuSolution {

    static Map<Character, Integer> charToIntMap = generateCharIntMap();

    // Map形式
    public static boolean isValidSudoku0(char[][] board) {
        Map<Integer, Set<Character>> rowSetMap = new HashMap<>();
        Map<Integer, Set<Character>> columnSetMap = new HashMap<>();
        Map<Integer, Set<Character>> diagonalSetMap = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char boardV = board[i][j];
                if (boardV == '.')
                    continue;
                Set<Character> rowSet = rowSetMap.get(i);
                if (rowSet == null) {
                    rowSet = new HashSet<>();
                    rowSetMap.put(i, rowSet);
                } else {
                    if (rowSet.contains(boardV)) {
                        return false;
                    }
                }
                rowSet.add(boardV);

                Set<Character> columnSet = columnSetMap.get(j);
                if (columnSet == null) {
                    columnSet = new HashSet<>();
                    columnSetMap.put(j, columnSet);
                } else {
                    if (columnSet.contains(boardV)) {
                        return false;
                    }
                }
                columnSet.add(boardV);

                int diagonalIndex = (i / 3) * 3 + (j / 3);
                Set<Character> diagonalSet = diagonalSetMap.get(diagonalIndex);
                if (diagonalSet == null) {
                    diagonalSet = new HashSet<>();
                    diagonalSetMap.put(diagonalIndex, diagonalSet);
                } else {
                    if (diagonalSet.contains(boardV)) {
                        return false;
                    }
                }
                diagonalSet.add(boardV);
            }
        }
        return true;
    }


    // 数组形式
    public static boolean isValidSudoku(char[][] board) {
        // 先建立三个二维数组，分别记录行、列、斜向的元素
        int[][] rowArr = new int[9][9];
        int[][] columnArr = new int[9][9];
        int[][] diagonalArr = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                char boardV = board[i][j];
                // 如果元素为'.'，直接跳过
                if (boardV == '.') {
                    continue;
                }
                // 获取元素对应的数值
                int boardIntV = boardV - '0';
                // 因为数组的长度是9，而元素的大小是1~9，所以-1
                boardIntV -= 1;
                // 读取行的元素，存在就返回false，不存在就标记
                int existRowV = rowArr[i][boardIntV];
                if (existRowV != 0) {
                    return false;
                }
                rowArr[i][boardIntV] = 1;
                // 读取列的元素，存在就返回false，不存在就标记
                int existColumnV = columnArr[j][boardIntV];
                if (existColumnV != 0) {
                    return false;
                }
                columnArr[j][boardIntV] = 1;
                // 读取斜线的元素，存在就返回false，不存在就标记
                int diagonalIndex = (i / 3) * 3 + (j / 3);
                int existDiagonalV = diagonalArr[diagonalIndex][boardIntV];
                if (existDiagonalV != 0) {
                    return false;
                }
                diagonalArr[diagonalIndex][boardIntV] = 1;
            }
        }
        return true;
    }

    private static Map<Character, Integer> generateCharIntMap() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('1', 1);
        map.put('2', 2);
        map.put('3', 3);
        map.put('4', 4);
        map.put('5', 5);
        map.put('6', 6);
        map.put('7', 7);
        map.put('8', 8);
        map.put('9', 9);
        return map;
    }
}
