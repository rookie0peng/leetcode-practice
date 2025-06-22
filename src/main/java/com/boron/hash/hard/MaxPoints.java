package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 149. 直线上最多的点数 <a href="https://leetcode.cn/problems/max-points-on-a-line/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/20
 * </pre>
 */
public class MaxPoints {

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

        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().points(new int[][] {
                {1,1},{2,2},{3,3}
        }).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().points(new int[][] {
                {1,1},{3,2},{5,3},{4,1},{2,3},{1,4}
        }).build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = MaxPointsSolution2.maxPoints(param.getPoints());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
    }
}

/**
 * 遍历
 */
class MaxPointsSolution2 {
    public static int maxPoints(int[][] points) {
        // 如果点的数量小于等于2，则直接返回数量
        if (points.length <= 2) {
            return points.length;
        }
        // 最大数量
        int maxCount = 0;
        // 第一层遍历，对应第一个点
        for (int i = 0; i < points.length; i++) {
            int x1 = points[i][0];
            int y1 = points[i][1];
            // 第二层遍历，对应第二个点
            for (int j = i + 1; j < points.length; j++) {
                int x2 = points[j][0];
                int y2 = points[j][1];
                int count = 2;
                // 第三层遍历，判断第3个点是否在前两个点对应的直线上
                for (int k = j + 1; k < points.length; k++) {
                    int x3 = points[k][0];
                    int y3 = points[k][1];
                    // 两点确定直线的方式：点斜式、斜截式、截距式、两点式
                    // 这里是两点式的变种
                    if ((x3 - x1) * (y2 - y1) == (y3 - y1) * (x2 - x1)) {
                        count++;
                    }
                }
                // 更新最大数量
                maxCount = Math.max(count, maxCount);
            }
        }
        return maxCount;
    }
}

/**
 * 硬着头皮算
 */
class MaxPointsSolution1 {

    static class Line {

        // 0: 水平线；1：垂直线；2：斜线
        int tag;
        Point point1;
        Point point2;
        List<Point> points = new ArrayList<>();
    }

    static class Point {

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }



    public static int maxPoints(int[][] points) {
        if (points.length <= 2) {
            return points.length;
        }
//        int[] dp = new int[points.length + 1];
//        dp[0] = 0;
//        dp[1] = 1;
//        dp[2] = 2;
        Set<Line> lines = new HashSet<>();
        Point point1 = new Point(points[0][0], points[0][1]);
        Point point2 = new Point(points[1][0], points[1][1]);
        lines.add(combineFreshLines(point1, point2));

        int maxCount = 0;
        for (int i = 2; i < points.length; i++) {
            Point point = new Point(points[i][0], points[i][1]);
            int tmpMaxCount = detect(point, lines);
            maxCount = Math.max(maxCount, tmpMaxCount);
        }
        return maxCount;
    }

    private static int detect(Point point, Set<Line> lines) {
        Set<Point> freshLinePoints = new HashSet<>();
        int maxCount = 0;
        for (Line line : lines) {
            boolean inLine = checkInLine(point, line);
            maxCount = Math.max(maxCount, line.points.size());
            if (!inLine) {
                freshLinePoints.addAll(line.points);
            }
        }
        for (Point point2 : freshLinePoints) {
            lines.add(combineFreshLines(point, point2));
        }
        return maxCount;
    }

    private static Line combineFreshLines(Point point1, Point point2) {
        Line line = new Line();
        line.point1 = point1;
        line.point2 = point2;
        if (point1.y == point2.y) {
            line.tag = 0;
        } else if (point1.x == point2.x) {
            line.tag = 1;
        } else {
            line.tag = 2;
        }
        line.points.add(point1);
        line.points.add(point2);
        return line;
    }

    private static boolean checkInLine(Point point, Line line) {
        int x1 = line.point1.x;
        int y1 = line.point1.y;
        int x2 = line.point2.x;
        int y2 = line.point2.y;
        boolean inLine = false;
        if (line.tag == 0) {
            if (point.y == y1) {
                inLine = true;
            }
        } else if (line.tag == 1) {
            if (point.x == x1) {
                inLine = true;
            }
        } else {
            if ((point.x - x1) * (y2 - y1) == (point.y - y1) * (x2 - x1)) {
                inLine = true;
            }
        }
        if (inLine) {
            line.points.add(point);
        }
        return inLine;
    }
}