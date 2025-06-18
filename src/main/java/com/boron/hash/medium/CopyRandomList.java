package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 138. 随机链表的复制 <a href="https://leetcode.cn/problems/copy-list-with-random-pointer/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/18
 * </pre>
 */
public class CopyRandomList {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        CopyRandomListSolution.Node node;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        CopyRandomListSolution.Node node;
    }

    public static Pair<Param, Result> generate0() {
        CopyRandomListSolution.Node node1 = new CopyRandomListSolution.Node(7);
        CopyRandomListSolution.Node node2 = new CopyRandomListSolution.Node(13);
        CopyRandomListSolution.Node node3 = new CopyRandomListSolution.Node(11);
        CopyRandomListSolution.Node node4 = new CopyRandomListSolution.Node(10);
        CopyRandomListSolution.Node node5 = new CopyRandomListSolution.Node(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node1.random = null;
        node2.random = node1;
        node3.random = node5;
        node4.random = node3;
        node5.random = node1;
        Param param = Param.builder().node(node1).build();
        CopyRandomListSolution.Node node11 = new CopyRandomListSolution.Node(7);
        CopyRandomListSolution.Node node21 = new CopyRandomListSolution.Node(13);
        CopyRandomListSolution.Node node31 = new CopyRandomListSolution.Node(11);
        CopyRandomListSolution.Node node41 = new CopyRandomListSolution.Node(10);
        CopyRandomListSolution.Node node51 = new CopyRandomListSolution.Node(1);
        node11.next = node21;
        node21.next = node31;
        node31.next = node41;
        node41.next = node51;
        node11.random = null;
        node21.random = node11;
        node31.random = node51;
        node41.random = node31;
        node51.random = node11;
        Result result = Result.builder().node(node11).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        CopyRandomListSolution.Node node1 = new CopyRandomListSolution.Node(7);
        CopyRandomListSolution.Node node2 = new CopyRandomListSolution.Node(13);
        node1.next = node2;
        node2.next = null;
        node1.random = node2;
        node2.random = node2;
        Param param = Param.builder().node(node1).build();
        CopyRandomListSolution.Node node11 = new CopyRandomListSolution.Node(7);
        CopyRandomListSolution.Node node21 = new CopyRandomListSolution.Node(13);
        node11.next = node21;
        node21.next = null;
        node11.random = node21;
        node21.random = node21;
        Result result = Result.builder().node(node11).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        CopyRandomListSolution.Node node1 = new CopyRandomListSolution.Node(3);
        CopyRandomListSolution.Node node2 = new CopyRandomListSolution.Node(3);
        CopyRandomListSolution.Node node3 = new CopyRandomListSolution.Node(3);
        node1.next = node2;
        node2.next = node3;
        node3.next = null;
        node1.random = null;
        node2.random = node1;
        node3.random = null;
        Param param = Param.builder().node(node1).build();
        CopyRandomListSolution.Node node11 = new CopyRandomListSolution.Node(3);
        CopyRandomListSolution.Node node21 = new CopyRandomListSolution.Node(3);
        CopyRandomListSolution.Node node31 = new CopyRandomListSolution.Node(3);
        node11.next = node21;
        node21.next = node31;
        node31.next = null;
        node11.random = null;
        node21.random = node11;
        node31.random = null;
        Result result = Result.builder().node(node11).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        CopyRandomListSolution.Node actualResult = CopyRandomListSolution.copyRandomList(param.getNode());
        CopyRandomListSolution.Node expectResult = result.getNode();
        boolean compareResult = deepEquals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
//        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static boolean deepEquals(CopyRandomListSolution.Node one, CopyRandomListSolution.Node other) {
//        return false;
        return deepEquals(one, other, new HashSet<>());
    }

    private static boolean deepEquals(CopyRandomListSolution.Node a, CopyRandomListSolution.Node b, Set<CopyRandomListSolution.Node> visited) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.val != b.val) return false;
//        if (a.neighbors.size() != b.neighbors.size()) return false;

        // 防止循环引用
        if (!visited.add(a) || !visited.add(b)) return true;

//        boolean equals = false;
        boolean equals1 = deepEquals(a.next, b.next, visited);
        boolean equals2 = deepEquals(a.random, b.random, visited);

        return equals1 && equals2;
    }



    public static void main(String[] args) {

        test(generate0());
        test(generate1());
        test(generate2());

//        Pair<Param, Result> paramResultPair = generate0();
//        boolean compare = deepEquals(paramResultPair.getKey().getNode(), paramResultPair.getValue().getNode());
//        System.out.println(compare);
//        Map<CopyRandomListSolution.Node, CopyRandomListSolution.Node> map = new HashMap<>();
//        CopyRandomListSolution.Node node1 = new CopyRandomListSolution.Node(1);
//        CopyRandomListSolution.Node node2 = new CopyRandomListSolution.Node(2);
//        node1.hashCode();
//        Object o = new Object();
//        o.hashCode();
//        map.put(node1, node2);
//        CopyRandomListSolution.Node node = map.get(node1);
//        System.out.println("over");
    }

}

class CopyRandomListSolution {

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList(Node head) {
        // 初始化map，用于存放已生成的node，(oldNode -> freshNode)
        Map<Node, Node> map = new HashMap<>();
        // 递归生成node
        Node freshHead = generateNode(head, map);
        // 递归生成随机属性
        generateRandom(head, freshHead, map);
        return freshHead;
    }

    /**
     * 递归生成节点
     * @param curOldNode 当前位置的旧节点
     * @param map 存放已生成的node，(oldNode -> freshNode)
     * @return 当前生成的新节点
     */
    public static Node generateNode(Node curOldNode, Map<Node, Node> map) {
        // 如果当前旧节点为空，则返回空
        if (curOldNode == null) {
            return null;
        }
        // 初始化当前节点
        Node curNode = new Node(curOldNode.val);
        // 将初始化的节点和新节点放入map
        map.put(curOldNode, curNode);
        // 递归生成next节点
        curNode.next = generateNode(curOldNode.next, map);
        return curNode;
    }

    /**
     * 递归生成随机节点
     * @param curOldNode 当前位置的旧节点
     * @param curNode 当前位置的新节点
     * @param map 存放已生成的node，(oldNode -> freshNode)
     */
    public static void generateRandom(Node curOldNode, Node curNode, Map<Node, Node> map) {
        // 如果当前旧节点为空，则返回空
        if (curOldNode == null) {
            return;
        }
        // 从map中获取新节点
        curNode.random = map.get(curOldNode.random);
        // 生成随机属性节点
        generateRandom(curOldNode.next, curNode.next, map);
    }
}