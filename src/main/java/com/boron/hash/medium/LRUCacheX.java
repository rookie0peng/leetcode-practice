package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * <pre>
 *  @description: 146. LRU 缓存 <a href="https://leetcode.cn/problems/lru-cache/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/19
 * </pre>
 */
public class LRUCacheX {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] inorder;
        int[] postorder;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        BuildTreeByInPostSolution.TreeNode result;
    }

    public static void test0() {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(3));    // 返回 3
        System.out.println(lRUCache.get(4));    // 返回 4
    }

    public static void test1() {
        LRUCache lRUCache = new LRUCache(3);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache.put(3, 3);
        lRUCache.put(4, 4);
        System.out.println(lRUCache.get(4));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache.get(2));
        System.out.println(lRUCache.get(1));
        lRUCache.put(5, 5);
        System.out.println(lRUCache.get(1));
        System.out.println(lRUCache.get(2));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache.get(4));
        System.out.println(lRUCache.get(5));
    }

    public static void main(String[] args) {
        test0();
//        test1();
    }
}


/**
 * 双向链表+哈希表
 */
class LRUCache {

    static class LRUCacheNode {

        public LRUCacheNode(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }

        Integer key;

        Integer value;

        LRUCacheNode pre;

        LRUCacheNode next;

    }

    private int capacity;

    private Map<Integer, LRUCacheNode> map;

    private final LRUCacheNode head0 = new LRUCacheNode(-1, -1);

    private final LRUCacheNode tail0 = new LRUCacheNode(-2, -2);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.head0.next = this.tail0;
        this.tail0.pre = this.head0;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        // 从map中获取缓存值
        LRUCacheNode lruCacheNode = map.get(key);
        // 将旧节点移出来，并且将其旧的前后节点连接起来
        LRUCacheNode pre1 = lruCacheNode.pre;
        LRUCacheNode next1 = lruCacheNode.next;
        pre1.next = next1;
        next1.pre = pre1;
        // 旧头节点
        LRUCacheNode oldHead = head0.next;
        // 将新节点放入头部，修改head0和新头节点的双向指针。
        head0.next = lruCacheNode;
        lruCacheNode.pre = head0;
        // 修改新头节点和旧头节点的双向指针
        lruCacheNode.next = oldHead;
        oldHead.pre = lruCacheNode;
        // 返回节点的值
        return lruCacheNode.value;
    }

    public void put(int key, int value) {
        // 如果map不包含key
        if (!map.containsKey(key)) {
            // 如果map的容量大于等于阈值
            if (map.size() >= capacity) {
                // 移除尾结点，但尾结点不能是头部哨兵节点
                if (tail0.pre != head0) {
                    LRUCacheNode tail = tail0.pre;
                    LRUCacheNode tailPre = tail.pre;
                    tailPre.next = tail0;
                    tail0.pre = tailPre;

                    tail.pre = null;
                    tail.next = null;

                    map.remove(tail.key);
                }
            }
            // 创建新头节点
            LRUCacheNode lruCacheNode = new LRUCacheNode(key, value);
            // 旧头节点
            LRUCacheNode oldHead = head0.next;
            // 将新节点放入头部，修改head0和新头节点的双向指针。
            head0.next = lruCacheNode;
            lruCacheNode.pre = head0;
            // 修改新头节点和旧头结点的双向指针
            lruCacheNode.next = oldHead;
            oldHead.pre = lruCacheNode;
            map.put(key, lruCacheNode);
        } else {
            // 从map中获取该节点
            LRUCacheNode lruCacheNode = map.get(key);
            // 将目标节点从源位置中移出，并再次连接前后节点
            LRUCacheNode pre1 = lruCacheNode.pre;
            LRUCacheNode next1 = lruCacheNode.next;
            pre1.next = next1;
            next1.pre = pre1;
            // 将目标节点放入头结点
            // 旧头节点
            LRUCacheNode oldHead = head0.next;
            // 将新节点放入头部，修改head0和新头节点的双向指针。
            head0.next = lruCacheNode;
            lruCacheNode.pre = head0;
            // 修改新头节点和旧头结点的双向指针
            lruCacheNode.next = oldHead;
            oldHead.pre = lruCacheNode;
            // 修改值
            lruCacheNode.value = value;
        }
    }

}

/**
 * 用语言自带的视线方式，Java：LinkedHashMap；Python：OrderedDict
 */
class LRUCache1 extends LinkedHashMap<Integer, Integer>{
    private int capacity;

    public LRUCache1(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */