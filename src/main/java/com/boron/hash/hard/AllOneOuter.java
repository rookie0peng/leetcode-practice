package com.boron.hash.hard;

import java.util.*;

/**
 * <pre>
 *  @description: 432. 全 O(1) 的数据结构 <a href="https://leetcode.cn/problems/all-oone-data-structure/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/6
 * </pre>
 */
public class AllOneOuter {

    public static void test1() {
        AllOne allOne = new AllOne();
        allOne.inc("hello");
        allOne.inc("hello");
        String value1 = allOne.getMaxKey(); // 返回 "hello"
        String value2 = allOne.getMinKey(); // 返回 "hello"
        allOne.inc("leet");
        String value3 = allOne.getMaxKey(); // 返回 "hello"
        String value4 = allOne.getMinKey(); // 返回 "leet"
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);
        System.out.println(value4);
    }

    public static void test2() {
        AllOne allOne = new AllOne();
        allOne.inc("hello");
        allOne.inc("goodbye");
        allOne.inc("hello");
        allOne.inc("hello");
        String value1 = allOne.getMaxKey();

        allOne.inc("leet");
        allOne.inc("code");
        allOne.inc("leet");
        allOne.dec("hello");
        allOne.inc("leet");

        allOne.inc("code");
        allOne.inc("code");
        String value2 = allOne.getMaxKey();

        System.out.println(value1);
        System.out.println(value2);
    }

    public static void test3() {
        AllOne allOne = new AllOne();
        allOne.inc("a");
        allOne.inc("b");
        allOne.inc("b");
        allOne.inc("b");
        allOne.inc("c");

        allOne.inc("c");
        allOne.inc("c");
        allOne.inc("c");
        allOne.inc("c");
        allOne.inc("c");

        allOne.inc("d");
        allOne.inc("d");
        allOne.inc("d");
        allOne.inc("d");
        allOne.inc("d");

        allOne.inc("d");
        allOne.inc("d");
        allOne.inc("d");
        String value1 = allOne.getMaxKey();
        String value2 = allOne.getMinKey();

        allOne.inc("b");
        allOne.dec("d");
        String value3 = allOne.getMaxKey();
        String value4 = allOne.getMinKey();


        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);
        System.out.println(value4);
    }

    public static void main(String[] args) {
//        test1();
        test2();
//        test3();
    }
}

class AllOne {

    static class OneNode {
        String val;
        int count;
        OneNode pre;
        OneNode next;

        public OneNode() {
        }

        public OneNode(String val, int count) {
            this.val = val;
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OneNode oneNode = (OneNode) o;
            return Objects.equals(val, oneNode.val);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    private final Map<String, OneNode> wordCountMap = new HashMap<>();
    // count -> headWord
    private final Map<Integer, OneNode> countHeadMap = new HashMap<>();
    private final Map<Integer, OneNode> countTailMap = new HashMap<>();
    private final OneNode headSentry = new OneNode("0", -10);
    private final OneNode tailSentry = new OneNode("100", -10);

    public AllOne() {
        headSentry.next = tailSentry;
        tailSentry.pre = headSentry;
    }

    public void inc(String key) {
        OneNode oneNode = wordCountMap.get(key);
        if (oneNode == null) {
            // 处理 wordCountMap
            // 初始化新节点
            oneNode = new OneNode(key, 1);
            wordCountMap.put(key, oneNode);

            // 处理链表
            // 获取计数为1的头节点
            OneNode oldCountHeadNode = countHeadMap.get(1);
            OneNode oldCountTailNode = countTailMap.get(1);
            if (oldCountHeadNode != null) {
                // 将新节点插入到头节点之前
                OneNode oldCountHeadPreNode = oldCountHeadNode.pre;
                // oldOnePreNode <--> oneNode
                oldCountHeadPreNode.next = oneNode;
                oneNode.pre = oldCountHeadPreNode;
                // oneNode <--> oldOneNode
                oneNode.next = oldCountHeadNode;
                oldCountHeadNode.pre = oneNode;

                // 处理 countHeadMap countTailMap
                // 更新该计数下旧的头节点，旧的尾节点不动
                countHeadMap.put(1, oneNode);
            } else {
                // 将新节点添加到尾节点
                OneNode oldTailPre = tailSentry.pre;
                // oldTailPre <--> oneNode
                oldTailPre.next = oneNode;
                oneNode.pre = oldTailPre;
                // oneNode <--> tailNode
                oneNode.next = tailSentry;
                tailSentry.pre = oneNode;

                // 处理 countHeadMap countTailMap
                // 更新该计数下旧的头节点和旧的尾节点
                countHeadMap.put(1, oneNode);
                countTailMap.put(1, oneNode);
            }
        } else {
            // 处理 wordCountMap
            int oldCount = oneNode.count;
            oneNode.count += 1;

            // 将该节点从旧位置移出
            OneNode preNode = oneNode.pre;
            OneNode nextNode = oneNode.next;
            preNode.next = nextNode;
            nextNode.pre = preNode;

            OneNode oldCountHeadNode = countHeadMap.get(oldCount);
            OneNode oldCountTailNode = countTailMap.get(oldCount);

            if (Objects.equals(oneNode, oldCountHeadNode) && Objects.equals(oneNode, oldCountTailNode)) {
                countHeadMap.remove(oldCount);
                countTailMap.remove(oldCount);
            } else if (Objects.equals(oneNode, oldCountHeadNode)) {
                countHeadMap.put(oldCount, nextNode);
            } else if (Objects.equals(oneNode, oldCountTailNode)) {
                countTailMap.put(oldCount, preNode);
            }

            // 新计数下的头节点
            OneNode freshCountHeadNode = countHeadMap.get(oneNode.count);
            OneNode freshCountTailNode = countTailMap.get(oneNode.count);
            // 如果新计数对应的头节点不存在，则将该节点还是插入到旧计数对应的头节点。
            if (freshCountHeadNode == null) {
                OneNode oldCountHeadPreNode = oldCountHeadNode.pre;
                if (Objects.equals(oldCountHeadNode, oneNode)) {
                    OneNode oldCountHeadNextNode = oldCountHeadNode.next;
                    oldCountHeadPreNode.next = oneNode;
                    oldCountHeadNextNode.pre = oneNode;
                } else {
                    // oldCountHeadPreNode <--> oneNode
                    oldCountHeadPreNode.next = oneNode;
                    oneNode.pre = oldCountHeadPreNode;
                    // oneNode <--> oldCountHeadNode
                    oneNode.next = oldCountHeadNode;
                    oldCountHeadNode.pre = oneNode;
                }
                // 更新该计数下新的头节点和新的尾节点
                countHeadMap.put(oneNode.count, oneNode);
                countTailMap.put(oneNode.count, oneNode);
            } else {
                OneNode freshCountHeadPreNode = freshCountHeadNode.pre;
                // freshCountHeadPreNode <--> oneNode
                freshCountHeadPreNode.next = oneNode;
                oneNode.pre = freshCountHeadPreNode;
                // oneNode <--> freshCountHeadNode
                oneNode.next = freshCountHeadNode;
                freshCountHeadNode.pre = oneNode;
                // 更新该计数下新的头节点和新的尾节点
                countHeadMap.put(oneNode.count, oneNode);
            }
        }
    }

    public void dec(String key) {
        if (!wordCountMap.containsKey(key)) {
            return;
        }
        // 处理 wordCountMap 1
        OneNode oneNode = wordCountMap.get(key);
        int oldCount = oneNode.count;
        oneNode.count -= 1;

        // 将该节点从旧位置移出
        OneNode preNode = oneNode.pre;
        OneNode nextNode = oneNode.next;
        // preNode <--> nextNode
        preNode.next = nextNode;
        nextNode.pre = preNode;

        OneNode oldCountHeadNode = countHeadMap.get(oldCount);
        OneNode oldCountTailNode = countTailMap.get(oldCount);

        if (Objects.equals(oneNode, oldCountHeadNode) && Objects.equals(oneNode, oldCountTailNode)) {
            countHeadMap.remove(oldCount);
            countTailMap.remove(oldCount);
        } else if (Objects.equals(oneNode, oldCountHeadNode)) {
            countHeadMap.put(oldCount, oneNode.next);
        } else if (Objects.equals(oneNode, oldCountTailNode)) {
            countTailMap.put(oldCount, oneNode.pre);
        }

        if (oneNode.count == 0) {
            wordCountMap.remove(key);
            return;
        }

        OneNode freshCountHeadNode = countHeadMap.get(oneNode.count);
        OneNode freshCountTailNode = countTailMap.get(oneNode.count);

        // 如果新计数对应的头节点不存在，则直接将该元素放入旧计数对应的尾节点
        if (freshCountHeadNode == null) {
            OneNode oldCountTailNextNode = oldCountTailNode.next;
            if (Objects.equals(oldCountTailNode, oneNode)) {
                OneNode oldCountHeadPreNode = oldCountTailNode.pre;
                oldCountHeadPreNode.next = oneNode;
                oldCountTailNextNode.pre = oneNode;
            } else {
                // oldCountTailNode <--> oneNode
                oldCountTailNode.next = oneNode;
                oneNode.pre = oldCountTailNode;
                // oneNode <--> oldCountTailNextNode
                oneNode.next = oldCountTailNextNode;
                oldCountTailNextNode.pre = oneNode;
            }
            // 更新该计数下新的头节点和新的尾节点
            countHeadMap.put(oneNode.count, oneNode);
            countTailMap.put(oneNode.count, oneNode);
        } else {
            OneNode freshCountHeadPreNode = freshCountHeadNode.pre;
            // freshCountHeadPreNode <--> oneNode
            freshCountHeadPreNode.next = oneNode;
            oneNode.pre = freshCountHeadPreNode;
            // oneNode <--> freshCountHeadNode
            oneNode.next = freshCountHeadNode;
            freshCountHeadNode.pre = oneNode;
            // 更新该计数下新的头节点和新的尾节点
            countHeadMap.put(oneNode.count, oneNode);
        }

    }

    public String getMaxKey() {
        OneNode headNode = headSentry.next;
        if (Objects.equals(headNode, tailSentry)) {
            return "";
        }
        return headNode.val;
    }

    public String getMinKey() {
        OneNode tailNode = tailSentry.pre;
        if (Objects.equals(tailNode, headSentry)) {
            return "";
        }
        return tailNode.val;
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
