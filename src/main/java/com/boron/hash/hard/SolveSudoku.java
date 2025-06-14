package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 *  @description: 37. 解数独 <a href="https://leetcode.cn/problems/sudoku-solver/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/11
 * </pre>
 */
public class SolveSudoku {

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

        char[][] board;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().board(new char[][]{
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        }).build();
        Result result = Result.builder().board(new char[][]{
                {'5','3','4','6','7','8','9','1','2'},
                {'6','7','2','1','9','5','3','4','8'},
                {'1','9','8','3','4','2','5','6','7'},
                {'8','5','9','7','6','1','4','2','3'},
                {'4','2','6','8','5','3','7','9','1'},
                {'7','1','3','9','2','4','8','5','6'},
                {'9','6','1','5','3','7','2','8','4'},
                {'2','8','7','4','1','9','6','3','5'},
                {'3','4','5','2','8','6','1','7','9'}
        }).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().board(new char[][]{
                {'.','.','9','7','4','8','.','.','.'},
                {'7','.','.','.','.','.','.','.','.'},
                {'.','2','.','1','.','9','.','.','.'},
                {'.','.','7','.','.','.','2','4','.'},
                {'.','6','4','.','1','.','5','9','.'},
                {'.','9','8','.','.','.','3','.','.'},
                {'.','.','.','8','.','3','.','2','.'},
                {'.','.','.','.','.','.','.','.','6'},
                {'.','.','.','2','7','5','9','.','.'}
        }).build();
        Result result = Result.builder().board(new char[][]{
                {'5','1','9','7','4','8','6','3','2'},
                {'7','8','3','6','5','2','4','1','9'},
                {'4','2','6','1','3','9','8','7','5'},
                {'3','5','7','9','8','6','2','4','1'},
                {'2','6','4','3','1','7','5','9','8'},
                {'1','9','8','5','2','4','3','6','7'},
                {'9','7','5','8','6','3','1','2','4'},
                {'8','3','2','4','9','1','7','5','6'},
                {'6','4','1','2','7','5','9','8','3'}
        }).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().board(new char[][]{
                {'3','2','.','9','.','7','.','1','4'},
                {'4','.','.','6','.','2','.','.','3'},
                {'.','.','.','.','3','.','.','.','.'},
                {'9','1','.','.','.','.','.','6','5'},
                {'.','.','4','.','.','.','3','.','.'},
                {'2','5','.','.','.','.','.','9','8'},
                {'.','.','.','.','6','.','.','.','.'},
                {'6','.','.','4','.','5','.','.','7'},
                {'5','9','.','3','.','1','.','4','6'}
        }).build();
        Result result = Result.builder().board(new char[][]{
                {'5','1','9','7','4','8','6','3','2'},
                {'7','8','3','6','5','2','4','1','9'},
                {'4','2','6','1','3','9','8','7','5'},
                {'3','5','7','9','8','6','2','4','1'},
                {'2','6','4','3','1','7','5','9','8'},
                {'1','9','8','5','2','4','3','6','7'},
                {'9','7','5','8','6','3','1','2','4'},
                {'8','3','2','4','9','1','7','5','6'},
                {'6','4','1','2','7','5','9','8','3'}
        }).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        SolveSudokuSolution.solveSudoku(param.getBoard());
        char[][] actualBoard = param.getBoard();
        char[][] expectBoard = result.getBoard();
        int compare = 0;
        for (int i = 0; i < 9; i++) {
            compare = Arrays.compare(actualBoard[i], expectBoard[i]);
            if (compare != 0) {
                break;
            }
        }
        System.out.println(Arrays.deepToString(param.getBoard()));
        boolean compareResult = compare == 0;
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
        test(generate2());
//        int intValue = 45 - 40;
//        char a = (char) ('0' + intValue);
//        System.out.println(a);
    }
}

class SolveSudokuSolution {

    private static boolean[][] rowArr = new boolean[9][9];
    private static boolean[][] columnArr = new boolean[9][9];
    private static boolean[][][] blockArr = new boolean[3][3][9];
    private static boolean valid = false;
    private static List<int[]> spaces = new ArrayList<int[]>();

    public static void solveSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    spaces.add(new int[] {i, j});
                } else {
                    int value = board[i][j] - '0' - 1;
                    rowArr[i][value] = true;
                    columnArr[j][value] = true;
                    blockArr[i/3][j/3][value] = true;
                }
            }
        }
        dfs(board, 0);
    }

    public static void dfs(char[][] board, int pos) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1];
        for (int x = 0; x < 9 && !valid; x++) {
            if (!rowArr[i][x] && !columnArr[j][x] && !blockArr[i / 3][j / 3][x]) {
                rowArr[i][x] = columnArr[j][x] = blockArr[i / 3][j / 3][x] = true;
                board[i][j] = (char) (x + '0' + 1);
                dfs(board, pos + 1);
                rowArr[i][x] = columnArr[j][x] = blockArr[i / 3][j / 3][x] = false;
            }
        }
    }

    private static boolean assume(
            char[][] board, int i, int j,
            Map<Integer, Set<Integer>> rowSetMap, Map<Integer, Set<Integer>> columnSetMap, Map<Integer, Set<Integer>> diagonalSetMap
    ) {
        char rowValue = board[i][j];
        if (rowValue != '.') {
            return true;
        }
        Set<Integer> rowSet = rowSetMap.get(i);
        Set<Integer> columnSet = columnSetMap.get(j);
        int diagonalIndex = i / 3 * 3 + j / 3;
        Set<Integer> diagonalSet = diagonalSetMap.get(diagonalIndex);
        Set<Integer> allSet = Stream.of(rowSet, columnSet, diagonalSet).flatMap(Collection::stream).collect(Collectors.toSet());
        if (allSet.size() != 9) {
            for (int x = 1; x <= 9; x++) {
                if (allSet.contains(x))
                    continue;
                board[i][j] = (char) ('0' + x);
                rowSet.add(x);
                columnSet.add(x);
                diagonalSet.add(x);
                boolean rowAssume = true;
                boolean columnAssume = true;
                boolean diagonalAssume = true;
                for (int y = 0; y < 9; y++) {
                    // 处理行、列、斜线
                    rowAssume = assume(board, i, y, rowSetMap, columnSetMap, diagonalSetMap);
                    if (!rowAssume)
                        break;
                    columnAssume = assume(board, y, j, rowSetMap, columnSetMap, diagonalSetMap);
                    if (!columnAssume)
                        break;
                    // 将斜线转换为小九宫格的左上角坐标
                    int a1 = diagonalIndex / 3;
                    int a2 = diagonalIndex % 3;
                    // 根据左上角坐标去遍历小九宫格
                    int i1 = a1 * 3 + y / 3;
                    int j1 = a2 * 3 + y % 3;
                    diagonalAssume = assume(board, i1, j1, rowSetMap, columnSetMap, diagonalSetMap);
                    if (!diagonalAssume)
                        break;
                }
                if (!rowAssume || !columnAssume || !diagonalAssume) {
                    rowSet.remove(x);
                    columnSet.remove(x);
                    diagonalSet.remove(x);
                    board[i][j] = '.';
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private static void dealAffected(
            char[][] board, int i, int j,
            Map<Integer, Set<Integer>> rowSetMap, Map<Integer, Set<Integer>> columnSetMap, Map<Integer, Set<Integer>> diagonalSetMap
    ) {
//        if (i == 7 && j == 5) {
//            System.out.println("now");
//        }
        char rowValue = board[i][j];
        if (rowValue != '.') {
            return;
        }
        Set<Integer> rowSet = rowSetMap.get(i);
        Set<Integer> columnSet = columnSetMap.get(j);
        int diagonalIndex = i / 3 * 3 + j / 3;
        Set<Integer> diagonalSet = diagonalSetMap.get(diagonalIndex);
        Set<Integer> allSet = Stream.of(rowSet, columnSet, diagonalSet).flatMap(Collection::stream).collect(Collectors.toSet());
        if (allSet.size() == 8) {
            int sum = allSet.stream().mapToInt(a -> a).sum();
            int intValue = 45 - sum;
            board[i][j] = (char) ('0' + intValue);
            rowSet.add(intValue);
            columnSet.add(intValue);
            diagonalSet.add(intValue);

            for (int x = 0; x < 9; x++) {
                // 处理行、列、斜线
                dealAffected(board, i, x, rowSetMap, columnSetMap, diagonalSetMap);
                dealAffected(board, x, j, rowSetMap, columnSetMap, diagonalSetMap);
                // 将斜线转换为小九宫格的左上角坐标
                int a1 = diagonalIndex / 3;
                int a2 = diagonalIndex % 3;
                // 根据左上角坐标去遍历小九宫格
                int i1 = a1 * 3 + x / 3;
                int j1 = a2 * 3 + x % 3;
                dealAffected(board, i1, j1, rowSetMap, columnSetMap, diagonalSetMap);
            }
        }
    }
}