package com.boron.hash.medium;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;

/**
 * <pre>
 *  @description: 348. 设计井字棋 <a href="https://leetcode.cn/problems/design-tic-tac-toe/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class TicTacToeOuter {

    public static void test0() {
        TicTacToe toe = new TicTacToe(3);
        int move1 = toe.move(0, 0, 1);
        int move2 = toe.move(0, 2, 2);
        int move3 = toe.move(2, 2, 1);
        int move4 = toe.move(1, 1, 2);
        int move5 = toe.move(2, 0, 1);
        int move6 = toe.move(1, 0, 2);
        int move7 = toe.move(2, 1, 1);
        System.out.println(move1);
        System.out.println(move2);
        System.out.println(move3);
        System.out.println(move4);
        System.out.println(move5);
        System.out.println(move6);
        System.out.println(move7);
    }

    public static void test1() {
        TicTacToe toe = new TicTacToe(3);
        int move1 = toe.move(0,2,2);
        int move2 = toe.move(1,0,1);
        int move3 = toe.move(2,1,2);
        int move4 = toe.move(1,1,1);
        int move5 = toe.move(0,0,2);
        int move6 = toe.move(2,0,1);
        int move7 = toe.move(1,2,2);

        int move8 = toe.move(2,2,1);
        int move9 = toe.move(0,1,2);

        System.out.println(move1);
        System.out.println(move2);
        System.out.println(move3);
        System.out.println(move4);
        System.out.println(move5);
        System.out.println(move6);
        System.out.println(move7);
        System.out.println(move8);
        System.out.println(move9);
    }

    public static void main(String[] args) {
//        test0();
        test1();

//        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

/**
 * 存储状态
 */
class TicTacToe {

//    private final int[][] board;
    private final int[] rowCount1;
    private final int[] rowCount2;
    private final int[] colCount1;
    private final int[] colCount2;
    private int diagCount1;
    private int xDiagCount1;
    private int diagCount2;
    private int xDiagCount2;
    private final int n;

    public TicTacToe(int n) {
//        this.board = new int[n][n];
        this.n = n;
        this.rowCount1 = new int[n];
        this.rowCount2 = new int[n];
        this.colCount1 = new int[n];
        this.colCount2 = new int[n];
        this.diagCount1 = 0;
        this.xDiagCount1 = 0;
    }

    public int move(int row, int col, int player) {
//        board[row][col] = player;
        if (player == 1) {
            rowCount1[row]++;
            colCount1[col]++;
            if (row == col) {
                diagCount1++;
            }
            if (row == n - 1 - col) {
                xDiagCount1++;
            }
            if (rowCount1[row] == n || colCount1[col] == n || diagCount1 == n || xDiagCount1 == n) {
                return player;
            }
        } else {
            rowCount2[row]++;
            colCount2[col]++;
            if (row == col) {
                diagCount2++;
            }
            if (row == n - 1 - col) {
                xDiagCount2++;
            }
            if (rowCount2[row] == n || colCount2[col] == n || diagCount2 == n || xDiagCount2 == n) {
                return player;
            }
        }
        return 0;
    }
}


/**
 * 暴力遍历，n^2
 */
class TicTacToe1 {

    private int[][] board;

    public TicTacToe1(int n) {
        board = new int[n][n];
    }

    public int move(int row, int col, int player) {
        board[row][col] = player;
        int rowPreValue = -1;
        int colPreValue = -1;
        int diagonalPreValue = -1;
        int diagonalPreValue2 = -1;
        boolean rowIsSame = true;
        boolean colIsSame = true;
        boolean diagonalIsSame = true;
        boolean diagonalIsSame2 = true;
        int result = 0;
        for (int i = 0; i < board.length; i++) {
            if (diagonalPreValue == -1) {
                diagonalPreValue = board[i][i];
            }
            if (diagonalPreValue2 == -1) {
                diagonalPreValue2 = board[board.length - 1 - i][i];
            }
            if (board[i][i] != diagonalPreValue) {
                diagonalIsSame = false;
            }
            if (board[board.length - 1 - i][i] != diagonalPreValue2) {
                diagonalIsSame2 = false;
            }
            rowPreValue = -1;
            colPreValue = -1;
            rowIsSame = true;
            colIsSame = true;
            for (int j = 0; j < board[i].length; j++) {
                if (rowPreValue == -1) {
                    rowPreValue = board[i][j];
                }
                if (board[i][j] != rowPreValue) {
                    rowIsSame = false;
                }
                if (colPreValue == -1) {
                    colPreValue = board[j][i];
                }
                if (board[j][i] != colPreValue) {
                    colIsSame = false;
                }
            }
            if (rowIsSame && rowPreValue != 0) {
                result = rowPreValue;
                break;
            }
            if (colIsSame && colPreValue != 0) {
                result = colPreValue;
                break;
            }
        }
        if (rowIsSame || colIsSame) {
            return result;
        }
        if (diagonalIsSame && diagonalPreValue != 0) {
            result = diagonalPreValue;
        }
        if (diagonalIsSame2 && diagonalPreValue2 != 0) {
            result = diagonalPreValue2;
        }
        return result;
    }
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */
