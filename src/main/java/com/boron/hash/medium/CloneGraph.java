package com.boron.hash.medium;

import com.alibaba.fastjson2.JSON;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 133. 克隆图 <a href="https://leetcode.cn/problems/clone-graph/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/18
 * </pre>
 */
public class CloneGraph {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        CloneGraphSolution.Node node;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        CloneGraphSolution.Node node;
    }

    public static Pair<Param, Result> generate0() {
        CloneGraphSolution.Node node1 = new CloneGraphSolution.Node(1);
        CloneGraphSolution.Node node2 = new CloneGraphSolution.Node(2);
        CloneGraphSolution.Node node3 = new CloneGraphSolution.Node(3);
        CloneGraphSolution.Node node4 = new CloneGraphSolution.Node(4);
        node1.neighbors = List.of(node2, node4);
        node2.neighbors = List.of(node1, node3);
        node3.neighbors = List.of(node2, node4);
        node4.neighbors = List.of(node1, node3);
        Param param = Param.builder().node(node1).build();
        CloneGraphSolution.Node node11 = new CloneGraphSolution.Node(1);
        CloneGraphSolution.Node node21 = new CloneGraphSolution.Node(2);
        CloneGraphSolution.Node node31 = new CloneGraphSolution.Node(3);
        CloneGraphSolution.Node node41 = new CloneGraphSolution.Node(4);
        node11.neighbors = List.of(node21, node41);
        node21.neighbors = List.of(node11, node31);
        node31.neighbors = List.of(node21, node41);
        node41.neighbors = List.of(node11, node31);
        Result result = Result.builder().node(node11).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        CloneGraphSolution.Node node1 = new CloneGraphSolution.Node(1);
        Param param = Param.builder().node(node1).build();
        CloneGraphSolution.Node node11 = new CloneGraphSolution.Node(1);
        Result result = Result.builder().node(node11).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().node(null).build();
        Result result = Result.builder().node(null).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        CloneGraphSolution.Node actualResult = CloneGraphSolution.cloneGraph(param.getNode());
        CloneGraphSolution.Node expectResult = result.getNode();
        boolean compareResult = deepEquals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
//        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static boolean deepEquals(CloneGraphSolution.Node one, CloneGraphSolution.Node other) {
        return deepEquals(one, other, new HashSet<>());
    }

    private static boolean deepEquals(CloneGraphSolution.Node a, CloneGraphSolution.Node b, Set<CloneGraphSolution.Node> visited) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.val != b.val) return false;
        if (a.neighbors.size() != b.neighbors.size()) return false;

        // 防止循环引用
        if (!visited.add(a) || !visited.add(b)) return true;

        for (int i = 0; i < a.neighbors.size(); i++) {
            if (!deepEquals(a.neighbors.get(i), b.neighbors.get(i), visited)) {
                return false;
            }
        }
        return true;
    }



    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());
    }
}


// Definition for a Node.



class CloneGraphSolution {

    static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public static Node cloneGraph(Node node) {
        // 初始化map，用来存放已经构建成功的Node，val -> node
        Map<Integer, Node> existMap = new HashMap<>();
        // 检测并生成节点
        return detectNode(node, existMap);
    }

    /**
     * 检测并生成邻居
     * @param oldPreNode 旧的前置节点
     * @param existMap 已生成节点Map
     * @return 新邻居节点列表
     */
    private static List<Node> detectNeighbours(Node oldPreNode, Map<Integer, Node> existMap) {
        // 如果旧前置节点为空，或者旧前置节点的邻居为空，则返回空
        if (oldPreNode == null || oldPreNode.neighbors == null) {
            return null;
        }
        // 遍历生成邻居节点
        List<Node> newNeighbours = new ArrayList<>();
        for (Node node1 : oldPreNode.neighbors) {
            // 调用方法生成节点
            Node tmpNode = detectNode(node1, existMap);
            newNeighbours.add(tmpNode);
        }
        return newNeighbours;
    }

    /**
     * 生成新节点
     * @param oldPreNode 旧前置节点
     * @param existMap 已生成节点Map
     * @return 新节点
     */
    private static Node detectNode(Node oldPreNode, Map<Integer, Node> existMap) {
        // 如果旧前置节点为空，则返回空
        if (oldPreNode == null) {
            return null;
        }
        // 如果已生成节点包含待生成节点的val，则直接从map中拿
        if (existMap.containsKey(oldPreNode.val)) {
            return existMap.get(oldPreNode.val);
        }
        // 生成新节点
        Node freshNode = new Node();
        freshNode.val = oldPreNode.val;
        // 将新生成的节点放入map
        existMap.put(freshNode.val, freshNode);
        // 继续检测邻居节点
        freshNode.neighbors = detectNeighbours(oldPreNode, existMap);
        return freshNode;
    }
}