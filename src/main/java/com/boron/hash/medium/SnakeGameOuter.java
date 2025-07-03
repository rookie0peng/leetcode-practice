package com.boron.hash.medium;

import java.util.*;

/**
 * <pre>
 *  @description: 353. 贪吃蛇 <a href="https://leetcode.cn/problems/design-snake-game/submissions/641058691/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class SnakeGameOuter {

    public static void test() {
        SnakeGame snakeGame = new SnakeGame(3, 2, new int[][]{{1, 2}, {0, 1}});
        int move1 = snakeGame.move("R"); // 返回 0
        int move2 = snakeGame.move("D"); // 返回 0
        int move3 = snakeGame.move("R"); // 返回 1 ，蛇吃掉了第一个食物，同时第二个食物出现在 (0, 1)
        int move4 = snakeGame.move("U"); // 返回 1
        int move5 = snakeGame.move("L"); // 返回 2 ，蛇吃掉了第二个食物，没有出现更多食物
        int move6 = snakeGame.move("U"); // 返回 -1 ，蛇与边界相撞，游戏结束
        System.out.println(move1);
        System.out.println(move2);
        System.out.println(move3);
        System.out.println(move4);
        System.out.println(move5);
        System.out.println(move6);
    }

    public static void test1() {
        SnakeGame snakeGame = new SnakeGame(3,3, new int[][]{{2,0},{0,0},{0,2},{0,1},{2,2},{0,1}});
        int move1 = snakeGame.move("D");
        int move2 = snakeGame.move("D");
        int move3 = snakeGame.move("R");
        int move4 = snakeGame.move("U");
        int move5 = snakeGame.move("U");
        int move6 = snakeGame.move("L");
        int move7 = snakeGame.move("D");
        int move8 = snakeGame.move("R");
        int move9 = snakeGame.move("R");
        int move10 = snakeGame.move("U");
        int move11 = snakeGame.move("L");
        int move12 = snakeGame.move("L");
        int move13 = snakeGame.move("D");
        int move14 = snakeGame.move("R");
        int move15 = snakeGame.move("U");
        System.out.println(move1);
        System.out.println(move2);
        System.out.println(move3);
        System.out.println(move4);
        System.out.println(move5);
        System.out.println(move6);
        System.out.println(move7);
        System.out.println(move8);
        System.out.println(move9);
        System.out.println(move10);
        System.out.println(move11);
        System.out.println(move12);
        System.out.println(move13);
        System.out.println(move14);
        System.out.println(move15);
    }

    public static void test2() {
        SnakeGame snakeGame = new SnakeGame(3,3, new int[][]{{0,1},{0,2},{1,2},{2,2},{2,1},{2,0},{1,0}});
        int move1 = snakeGame.move("R");
        int move2 = snakeGame.move("R");
        int move3 = snakeGame.move("D");
        int move4 = snakeGame.move("D");
        int move5 = snakeGame.move("L");
        int move6 = snakeGame.move("L");
        int move7 = snakeGame.move("U");
        int move8 = snakeGame.move("U");
        int move9 = snakeGame.move("R");
        int move10 = snakeGame.move("R");
        int move11 = snakeGame.move("D");
        int move12 = snakeGame.move("D");
        int move13 = snakeGame.move("L");
        int move14 = snakeGame.move("L");
        int move15 = snakeGame.move("U");
        int move16 = snakeGame.move("R");
        int move17 = snakeGame.move("U");
        int move18 = snakeGame.move("L");
        int move19 = snakeGame.move("D");
        System.out.println(move1);
        System.out.println(move2);
        System.out.println(move3);
        System.out.println(move4);
        System.out.println(move5);
        System.out.println(move6);
        System.out.println(move7);
        System.out.println(move8);
        System.out.println(move9);
        System.out.println(move10);
        System.out.println(move11);
        System.out.println(move12);
        System.out.println(move13);
        System.out.println(move14);
        System.out.println(move15);
        System.out.println(move16);
        System.out.println(move17);
        System.out.println(move18);
        System.out.println(move19);
    }

    public static void main(String[] args) {
//        test();
        test1();
//        test2();
//        Long x = 1000L;
//        int i = x.hashCode();
//        System.out.println(i);
    }
}

class SnakeGame {

    private static final Map<String, int[]> DIR_MAP = Map.of(
            "L", new int[] {0, -1},
            "R", new int[] {0, 1},
            "U", new int[] {-1, 0},
            "D", new int[] {1, 0}
    );

    private final int width;
    private final int height;
    private int[][] foods;
    private int foodIdx;
    private List<int[]> body;
    private int score = 0;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.foods = food;
        this.foodIdx = 0;
        this.body = new ArrayList<int[]>();
        this.body.add(new int[]{0, 0});
    }

    private boolean inBody(int[] node) {
        for (int i = 0; i < body.size(); i++) {
            if (body.get(i)[0] == node[0] && body.get(i)[1] == node[1] && i != body.size() - 1) {
                return true;
            }
        }
        return false;
    }

    public int move(String direction) {
        // 获取方向对应的操作
        int[] dirArr = DIR_MAP.get(direction);
        // 获取头节点
        int[] head = body.get(0);
        // 获取下一个头节点
        int[] nextHead = new int[] {head[0] + dirArr[0],  head[1] + dirArr[1]};
        // 校验下一个头节点是否出界
        if (nextHead[0] < 0 || nextHead[0] > height - 1 || nextHead[1] < 0 || nextHead[1] > width - 1) {
            return -1;
        }
        // 如果nextHead在身体上，但不是尾结点，则返回-1，因为尾结点向前移动一格后，刚好是满足不会咬到自己
        if (inBody(nextHead)) {
            return -1;
        }
        // 添加头节点
        body.add(0, nextHead);
        // 判断是否需要移除尾节点
        boolean removeTail = true;
        // 食物的下标是否小于食物长度
        if (foodIdx < foods.length) {
            // 获取当前食物
            int[] food = foods[foodIdx];
            // 如果下一个头节点命中食物
            if (food[0] == nextHead[0] && food[1] == nextHead[1]) {
                // 得分+1
                score++;
                // 食物下标+1
                foodIdx++;
                // 不需要移除尾节点（增长蛇身）
                removeTail = false;
            }
        }
        // 是否需要移除尾结点
        if (removeTail) {
            body.remove(body.size() - 1);
        }
        return score;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
