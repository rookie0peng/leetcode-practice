package com.boron.hash.hard;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.lang.reflect.Type;
import java.util.*;

/**
 * <pre>
 *  @description: 460. LFU 缓存 <a href="https://leetcode.cn/problems/lfu-cache/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/8
 * </pre>
 */
public class LFUCacheOuter {

    public static void test1() {
        // cnt(x) = 键 x 的使用计数
        // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
        LFUCache lfu = new LFUCache(2);
        lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
        lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
        int result1 = lfu.get(1);      // 返回 1
        // cache=[1,2], cnt(2)=1, cnt(1)=2
        lfu.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
        // cache=[3,1], cnt(3)=1, cnt(1)=2
        int result2 = lfu.get(2);      // 返回 -1（未找到）
        int result3 = lfu.get(3);      // 返回 3
        // cache=[3,1], cnt(3)=2, cnt(1)=2
        lfu.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
        // cache=[4,3], cnt(4)=1, cnt(3)=2
        int result4 = lfu.get(1);      // 返回 -1（未找到）
        int result5 = lfu.get(3);      // 返回 3
        // cache=[3,4], cnt(4)=1, cnt(3)=3
        int result6 = lfu.get(4);      // 返回 4
        // cache=[3,4], cnt(4)=2, cnt(3)=3
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(result5);
        System.out.println(result6);
    }

    public static void test2() {
        // cnt(x) = 键 x 的使用计数
        // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
        LFUCache lfu = new LFUCache(2);
        lfu.put(2, 1);
        lfu.put(2, 2);
        int result1 = lfu.get(2);
        lfu.put(1, 1);
        lfu.put(2, 1);
        int result2 = lfu.get(2);
        System.out.println(result1);
        System.out.println(result2);
    }

    public static void test3() {
        // cnt(x) = 键 x 的使用计数
        // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
        LFUCache lfu = new LFUCache(2);
        lfu.put(2, 1);
        lfu.put(1, 1);
        lfu.put(2, 3);
        lfu.put(4, 1);
        int result1 = lfu.get(1);
        int result2 = lfu.get(2);
        System.out.println(result1);
        System.out.println(result2);
    }

    public static void test4() {
        // cnt(x) = 键 x 的使用计数
        // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
        LFUCache lfu = new LFUCache(10);
        int[][] params = JSON.parseObject("[[10],[10,13],[3,17],[6,11],[10,5],[9,10],[13],[2,19],[2],[3],[5,25],[8],[9,22],[5,5],[1,30],[11],[9,12],[7],[5],[8],[9],[4,30],[9,3],[9],[10],[10],[6,14],[3,1],[3],[10,11],[8],[2,14],[1],[5],[4],[11,4],[12,24],[5,18],[13],[7,23],[8],[12],[3,27],[2,12]]", int[][].class);
        for (int i = 0; i < params.length; i++) {
            if (params[i].length == 1) {
                int result = lfu.get(params[i][0]);
                System.out.printf("i=%d, param=%d, result=%d\n", i, params[i][0], result);
            } else {
                lfu.put(params[i][0], params[i][1]);
            }
        }

    }

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }
}


class LFUCache {

    static class Node {
        int key;
        int value;
        int count;
        Node pre;
        Node next;

        public Node() {

        }

        public Node(int key, int value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                    .add("key=" + key)
                    .add("value=" + value)
                    .add("count=" + count)
                    .toString();
        }
    }

    private final Map<Integer, Node> database = new HashMap<>();
    private final Map<Integer, Node> countHeadMap = new HashMap<>();
    private final int capacity;
    private final Node headSentry = new Node(-1, -1, 0);
    private final Node tailSentry = new Node(-2, -2, 0);

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.headSentry.next = this.tailSentry;
        this.tailSentry.pre = this.headSentry;
    }

    public int get(int key) {
        if (key == 12) {
            System.out.println("now-12");
        }
        if (!database.containsKey(key)) {
            return -1;
        }
        Node node = database.get(key);
        int oldCount = node.count;
        node.count++;

        Node preNode = node.pre;
        Node nextNode = node.next;

        // 处理链表
        // 先从旧下标移出
        preNode.next = nextNode;
        nextNode.pre = preNode;

        // 看情况插入新下标前面，或者旧计数对应的head前面
        Node freshCountHead = countHeadMap.get(node.count);
        Node oldCountHead = countHeadMap.get(oldCount);

        Node tmpNode = freshCountHead == null ? Objects.equals(oldCountHead, node) ? nextNode : oldCountHead : freshCountHead;
        Node tmpPreNode = tmpNode.pre;

        // tmpPreNode <--> node
        tmpPreNode.next = node;
        node.pre = tmpPreNode;
        // node <--> tmpNode
        node.next = tmpNode;
        tmpNode.pre = node;

        // 处理DATABASE
        // 处理旧计数
        countHeadMap.remove(oldCount);
        if (Objects.equals(oldCountHead, node)) {
            if (nextNode.count == oldCount) {
                countHeadMap.put(oldCount, nextNode);
            }
        } else {
            countHeadMap.put(oldCount, oldCountHead);
        }
        // 处理新计数
        countHeadMap.put(node.count, node);
        return node.value;
    }

    public void put(int key, int value) {
        // 如果key不存在
        if (!database.containsKey(key)) {
            Node node = new Node(key, value, 1);
            // 将新节点插入到计数为1的节点头部
            Node countHead1 = countHeadMap.get(1);
            // 如果旧计数为1的节点为null，则直接插入到尾节点前面，不为null，则插入到该节点前面
            Node tmpNode = countHead1 == null ? tailSentry : countHead1;
            Node tmpPreNode = tmpNode.pre;

            // tmpPreNode <--> node
            tmpPreNode.next = node;
            node.pre = tmpPreNode;
            // node <--> tmpNode
            node.next = tmpNode;
            tmpNode.pre = node;

            // 如果容量超出，则需要删除尾节点
            if (database.size() == capacity) {
                // 如果新节点的下一个节点是尾节点，那就直接删除新节点的前一个节点
                if (Objects.equals(node.next, tailSentry)) {
                    Node preNode = node.pre;

                    Node prePreNode = preNode.pre;
                    Node preNextNode = preNode.next;
                    // prePreNode <--> preNextNode
                    prePreNode.next = preNextNode;
                    preNextNode.pre = prePreNode;

                    // 处理countHeadMap
                    // 因为前一个节点有可能是其他计数的head节点
                    if (Objects.equals(countHeadMap.get(preNode.count), preNode)) {
                        // 删除该计数对应的countHeadMap
                        countHeadMap.remove(preNode.count);
                    }
                    // 删除该节点对应的数据库数据
                    database.remove(preNode.key);
                } else {
                    // 否则直接删除尾哨兵节点的前一个节点
                    Node preTail = tailSentry.pre;

                    Node prePreTail = preTail.pre;
                    Node preNextTail = preTail.next;
                    // prePreTail <--> preNextTail
                    prePreTail.next = preNextTail;
                    preNextTail.pre = prePreTail;
                    // 删除该节点对应的数据库数据
                    database.remove(preTail.key);
                }
            }
            countHeadMap.put(1, node);
            database.put(key, node);
        } else {
            // 如果key存在
            // 处理database
            Node node = database.get(key);
            int oldCount = node.count;
            node.count++;
            node.value = value;

            // 处理链表
            Node preNode = node.pre;
            Node nextNode = node.next;
            // 将节点从旧下标移出
            preNode.next = nextNode;
            nextNode.pre = preNode;

            // 看情况插入新下标前面，或者旧计数对应的head前面
            Node freshCountHead = countHeadMap.get(node.count);
            Node oldCountHead = countHeadMap.get(oldCount);

            Node tmpNode = freshCountHead == null ? Objects.equals(oldCountHead, node) ? nextNode : oldCountHead : freshCountHead;
            Node tmpPreNode = tmpNode.pre;

            // tmpPreNode <--> node
            tmpPreNode.next = node;
            node.pre = tmpPreNode;
            // node <--> tmpNode
            node.next = tmpNode;
            tmpNode.pre = node;

            // 处理countHeadMap
            // 如果下一个节点的计数等于旧计数，则将下一个节点作为head节点
            // 否则直接删除旧计数对应的map
            countHeadMap.remove(oldCount);
            if (Objects.equals(oldCountHead, node)) {
                if (nextNode.count == oldCount) {
                    countHeadMap.put(oldCount, nextNode);
                }
            } else {
                countHeadMap.put(oldCount, oldCountHead);
            }

            // 处理countHeadMap
            countHeadMap.put(node.count, node);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */