package com.boron.str.hard;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 212. 单词搜索 II <a href="https://leetcode.cn/problems/word-search-ii/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/18
 * </pre>
 */
public class FindWords {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        char[][] board;
        String[] words;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<String> result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder()
                .board(new char[][]{{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}})
                .words(new String[]{"oath","pea","eat","rain"})
                .build();
        Result result = Result.builder().result(List.of("oath", "eat")).build();
        return Pair.of(param, result);
    }

    // ["a","b"],["c","d"]
    public static Pair<Param, Result> generate1() {
        Param param = Param.builder()
                .board(new char[][]{{'a','b'},{'c','d'}})
                .words(new String[]{"abcb"})
                .build();
        Result result = Result.builder().result(List.of()).build();
        return Pair.of(param, result);
    }

    // ["a","b"],["c","d"]
    public static Pair<Param, Result> generate2() {
        Param param = Param.builder()
                .board(new char[][]{{'m','b','c','d','e','f','g','h','i','j','k','l'},{'n','a','a','a','a','a','a','a','a','a','a','a'},{'o','a','a','a','a','a','a','a','a','a','a','a'},{'p','a','a','a','a','a','a','a','a','a','a','a'},{'q','a','a','a','a','a','a','a','a','a','a','a'},{'r','a','a','a','a','a','a','a','a','a','a','a'},{'s','a','a','a','a','a','a','a','a','a','a','a'},{'t','a','a','a','a','a','a','a','a','a','a','a'},{'u','a','a','a','a','a','a','a','a','a','a','a'},{'v','a','a','a','a','a','a','a','a','a','a','a'},{'w','a','a','a','a','a','a','a','a','a','a','a'},{'x','y','z','a','a','a','a','a','a','a','a','a'}})
                .words(new String[]{"aaaaaaaaaa", "aaaaaaaaab", "aaaaaaaaij", "aaaaaaaaih"})
                .build();
        Result result = Result.builder().result(List.of("aaaaaaaaij", "aaaaaaaaih")).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResult = FindWordsSolution.findWords(param.getBoard(), param.getWords());
        List<String> expectResult = result.getResult();
        List<String> actualResult1 = actualResult.stream().sorted(Comparator.comparing(s -> s)).toList();
        List<String> expectResult1 = expectResult.stream().sorted(Comparator.comparing(s -> s)).toList();
        boolean compareResult = Objects.equals(actualResult1, expectResult1);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult1), JSON.toJSONString(expectResult1));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {

//        test(generate0());
//        test(generate1());
        test(generate2());
//        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }

}

class FindWordsSolution {

    static class Node {

        int x;
        int y;
        char c;
        boolean trace = false;
        Map<Character, List<Node>> next = new HashMap<>();

        public Node() {

        }

        public Node(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    static char[][] BOARD;
//    static boolean[][] TRACES;
    static Node[][] NODES;
    
    private final static List<int[]> FOUR_DIRECTION = List.of(
            new int[] {0, 1}, new int[] {0, -1}, 
            new int[] {1, 0}, new int[] {-1, 0}
    );
    
    public static List<String> findWords(char[][] board, String[] words) {
        BOARD = board;
        int m = board.length, n = board[0].length;
        Node[][] nodes = new Node[m][n];
        Map<Character, List<int[]>> charIndices = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c0 = board[i][j];
                if (nodes[i][j] == null) {
                    nodes[i][j] = new Node(i, j, c0);
                }
                for (int[] direction : FOUR_DIRECTION) {
                    int x1 = i + direction[0];
                    int y1 = j + direction[1];
                    if (x1 < 0 || x1 >= m || y1 < 0 || y1 >= n) {
                        continue;
                    }
                    char c1 = board[x1][y1];
                    if (nodes[x1][y1] == null) {
                        nodes[x1][y1] = new Node(i, j, c1);
                    }
                    List<Node> nextNodes = nodes[i][j].next.computeIfAbsent(c1, k -> new ArrayList<>());
                    nextNodes.add(nodes[x1][y1]);
                }
                List<int[]> indices = charIndices.computeIfAbsent(c0, k -> new ArrayList<>());
                indices.add(new int[] {i, j});
            }
        }
        NODES = nodes;


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = board[i][j];
                List<int[]> indices = charIndices.computeIfAbsent(c, k -> new ArrayList<>());
                indices.add(new int[] {i, j});
            }
        }
        List<String> foundWords = new ArrayList<>();
        for (String word : words) {
            int startEndpoint = getStartEndpoint(word, charIndices);
            // 有字符不存在于board中
            if (startEndpoint == 0) {
                continue;
            }
            int start = 0, step = 1;
            if (startEndpoint == -1) {
                start = word.length() - 1;
                step = -1;
            }
            List<int[]> indices = charIndices.get(word.charAt(start));
            for (int[] idx : indices) {
                Node node = NODES[idx[0]][idx[1]];
                node.trace = true;
                boolean dfsFind = dfsFind(word, start + step, step, node);
                if (dfsFind) {
                    foundWords.add(word);
                    node.trace = false;
                    break;
                }
                node.trace = false;
            }


        }
        return foundWords;
    }

    /**
     * 判断从哪一端开始遍历
     * @param word 单词
     * @param charIndices 字符索引
     * @return 0: 字符缺失, 1: 从左往右, 2: 从右往左
     */
    public static int getStartEndpoint(String word, Map<Character, List<int[]>> charIndices) {
        int mid = word.length() / 2;
        int sumPrefix = 0, sumSuffix = 0;
        int idx1, idx2;
        for (int i = 0; i < mid; i++) {
            idx1 = i;
            idx2 = mid - 1 - i;
            char c1 = word.charAt(idx1);
            char c2 = word.charAt(idx2);
            List<int[]> indices1 = charIndices.getOrDefault(c1, List.of());
            List<int[]> indices2 = charIndices.getOrDefault(c2, List.of());
            if (indices1.size() == 0 || indices2.size() == 0) {
                return 0;
            }
            sumPrefix += indices1.size();
            sumSuffix += indices2.size();
        }
        return sumSuffix > sumPrefix ? 1 : -1;
    }

    public static boolean dfsFind(String word, int idx, int step, Node node) {
        if (idx < 0 || idx >= word.length()) {
            return true;
        }
        if (node == null || !node.trace) {
            return false;
        }
        char c = word.charAt(idx);
        List<Node> nodes = node.next.getOrDefault(c, List.of());
        for (Node nextNode : nodes) {
            nextNode.trace = true;
            boolean dfsFind = dfsFind(word, idx + step, step, nextNode);
            if (dfsFind) {
                nextNode.trace = false;
                return true;
            }
            nextNode.trace = false;
        }
        return false;
    }

}
