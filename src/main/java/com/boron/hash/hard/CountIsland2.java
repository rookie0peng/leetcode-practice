package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 305. 岛屿数量 II <a href="https://leetcode.cn/problems/number-of-islands-ii/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/29
 * </pre>
 */
public class CountIsland2 {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        int m;
        int n;
        int[][] positions;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {

        List<Integer> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().m(3).n(3).positions(new int[][] {
                {0,0},{0,1},{1,2},{2,1}
        }).build();
        Result result = Result.builder().results(List.of(1,1,2,3)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().m(1).n(1).positions(new int[][] {
                {0,0}
        }).build();
        Result result = Result.builder().results(List.of(1)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().m(3).n(3).positions(new int[][] {
                {0,1},{1,2},{2,1},{1,0},{0,2},{0,0},{1,1}
        }).build();
        Result result = Result.builder().results(List.of(1, 2, 3, 4, 3, 2, 1)).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().m(8).n(2).positions(new int[][] {
                {7,0}
        }).build();
        Result result = Result.builder().results(List.of(1)).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<Integer> actualResults = CountIslandSolution2.numIslands2(param.getM(), param.getN(), param.getPositions());
        List<Integer> expectResults = result.getResults();
        boolean compareResult = Objects.equals(actualResults, expectResults);
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResults, expectResults);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
//        test(generate2());
        test(generate3());
    }
}

/**
 * 寻址
 */
class CountIslandSolution2 {

    static int[][] NEIGHBOR = new int[][] {
            {-1, 0},
            {0, -1},
            {1, 0},
            {0, 1},
    };

    public static List<Integer> numIslands2(int m, int n, int[][] positions) {
        // 访问记录器
        boolean[] visited = new boolean[m * n];
        // 搜索器，记录岛屿数量，隶属父岛屿情况
        Searcher searcher = new Searcher(m * n);
        // 结果
        List<Integer> results = new ArrayList<>();
        // 遍历positions
        for (int i = 0; i < positions.length; i++) {
            // 获取对应的下标
            int x = positions[i][0];
            int y = positions[i][1];
            // 二维转一维
            int index = n * x + y;
            // 如果未访问过，则进入
            if (!visited[index]) {
                // 设置访问true
                visited[index] = true;
                // 搜索器统计岛屿+1
                searcher.count++;
                // 遍历邻居
                for (int j = 0; j < NEIGHBOR.length; j++) {
                    // 获取邻居的下标
                    int freshX = x + NEIGHBOR[j][0];
                    int freshY = y + NEIGHBOR[j][1];
                    // 二维转一维
                    int freshIndex = freshX * n + freshY;
                    // 如果邻居在范围内，且访问过（因为没访问过的邻居肯定不在岛屿内），且两个下标未连接
                    if (inArea(freshX, freshY, m, n) && visited[freshIndex] && !searcher.isConnected(index, freshIndex)) {
                        // 连接两个下标
                        searcher.connect(index, freshIndex);
                    }
                }
            }
            // 添加结果
            results.add(searcher.count);
        }
        // 返回结果
        return results;
    }

    private static boolean inArea(int x, int y, int m, int n) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }

    static class Searcher {
        // 记录每个下标的父下标
        private int[] parents;
        // 统计岛屿数量
        private int count;

        public Searcher(int all) {
            // 初始化父岛屿
            parents = new int[all];
            for (int i = 0; i < all; i++) {
                parents[i] = i;
            }
            // 初始化岛屿数量
            count = 0;
        }

        public int find(int x) {
            // 查找父岛屿，如果父岛屿不等于当前下标
            if (parents[x] != x) {
                // 则递归查找，并递归修改，并且将当前岛屿指向父岛屿
                parents[x] = find(parents[x]);
            }
            // 返回父岛屿
            return parents[x];
        }

        public boolean isConnected(int index, int newIndex) {
            // 判断两个下标是否有相同的父岛屿
            return find(index) == find(newIndex);
        }

        public void connect(int index, int newIndex) {
            // 连接两个下标
            // 先找到对应的父下标
            int parent1 = find(index);
            int parent2 = find(newIndex);
            // 如果两个父相等，返回
            if (parent1 == parent2) {
                return;
            }
            // 不相等，则将父下标的父指向第二个父
            parents[parent1] = parent2;
            // 岛屿数量减一
            count--;
        }
    }
}

/**
 * 哈希，但是超时
 */
class CountIslandSolution {

    static class Index {
        int x;
        int y;

        public static Index generate(int x, int y) {
            Index index = new Index();
            index.x = x;
            index.y = y;
            return index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Index index = (Index) o;
            return x == index.x && y == index.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static List<Integer> numIslands2(int m, int n, int[][] positions) {
        // 岛屿对应下标集合
        Map<Index, Set<Index>> island2IndexMap = new HashMap<>();
        // 下标对应岛屿
        Index[][] index2IslandMap = new Index[m][n];
        List<Integer> results = new ArrayList<>();
        for (int k = 0; k < positions.length; k++) {
            int i = positions[k][0];
            int j = positions[k][1];
            Index now = Index.generate(i, j);
            // 将当前下标指向新岛屿
            index2IslandMap[i][j] = now;
            // 旧岛屿对应下标
            Set<Index> oldIslandIndices = new HashSet<>();
            oldIslandIndices.add(now);
            // 旧岛屿
            Set<Index> oldIslands = new HashSet<>();
            if (i - 1 >= 0 && index2IslandMap[i - 1][j] != null) {
                oldIslands.add(index2IslandMap[i - 1][j]);
            }
            if (j - 1 >= 0 && index2IslandMap[i][j - 1] != null) {
                oldIslands.add(index2IslandMap[i][j - 1]);
            }
            if (i + 1 < m && index2IslandMap[i + 1][j] != null) {
                oldIslands.add(index2IslandMap[i + 1][j]);
            }
            if (j + 1 < n && index2IslandMap[i][j + 1] != null) {
                oldIslands.add(index2IslandMap[i][j + 1]);
            }
            for (Index index : oldIslands) {
                Set<Index> indices = island2IndexMap.get(index);
                oldIslandIndices.addAll(indices);
                island2IndexMap.remove(index);
            }
            for (Index index : oldIslandIndices) {
                index2IslandMap[index.x][index.y] = now;
            }
            island2IndexMap.put(now, oldIslandIndices);
            results.add(island2IndexMap.keySet().size());
        }
        return results;
    }

}
