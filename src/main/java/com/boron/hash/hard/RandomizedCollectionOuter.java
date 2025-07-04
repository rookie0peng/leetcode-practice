package com.boron.hash.hard;

import java.util.*;

/**
 * <pre>
 *  @description: 381. O(1) 时间插入、删除和获取随机元素 - 允许重复 <a href="https://leetcode.cn/problems/insert-delete-getrandom-o1-duplicates-allowed/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class RandomizedCollectionOuter {

    public static void test1() {
        RandomizedCollection collection = new RandomizedCollection();// 初始化一个空的集合。
        boolean result1 = collection.insert(1);   // 返回 true，因为集合不包含 1。
        // 将 1 插入到集合中。
        boolean result2 = collection.insert(1);   // 返回 false，因为集合包含 1。
        // 将另一个 1 插入到集合中。集合现在包含 [1,1]。
        boolean result3 = collection.insert(2);   // 返回 true，因为集合不包含 2。
        // 将 2 插入到集合中。集合现在包含 [1,1,2]。
        int value1 = collection.getRandom(); // getRandom 应当:
        // 有 2/3 的概率返回 1,
        // 1/3 的概率返回 2。
        boolean result4 = collection.remove(1);   // 返回 true，因为集合包含 1。
        // 从集合中移除 1。集合现在包含 [1,2]。
        int value2 = collection.getRandom(); // getRandom 应该返回 1 或 2，两者的可能性相同。

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(value1);
        System.out.println(value2);
    }

    public static void test2() {
        RandomizedCollection collection = new RandomizedCollection();// 初始化一个空的集合。
        boolean result1 = collection.insert(1);   // 返回 true，因为集合不包含 1。
        boolean result2 = collection.insert(1);   // 返回 false，因为集合包含 1。
        boolean result3 = collection.insert(2);   // 返回 true，因为集合不包含 2。
        boolean result4 = collection.insert(1);   // 返回 true，因为集合不包含 2。
        boolean result5 = collection.insert(2);   // 返回 true，因为集合不包含 2。
        boolean result6 = collection.insert(2);   // 返回 true，因为集合不包含 2。

        boolean result7 = collection.remove(1);
        boolean result8 = collection.remove(2);
        boolean result9 = collection.remove(2);
        boolean result10 = collection.remove(2);

        int value1 = collection.getRandom();
        int value2 = collection.getRandom();
        int value3 = collection.getRandom();
        int value4 = collection.getRandom();
        int value5 = collection.getRandom();

        int value6 = collection.getRandom();
        int value7 = collection.getRandom();
        int value8 = collection.getRandom();
        int value9 = collection.getRandom();
        int value10 = collection.getRandom();

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(result5);

        System.out.println(result6);
        System.out.println(result7);
        System.out.println(result8);
        System.out.println(result9);
        System.out.println(result10);

        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);
        System.out.println(value4);
        System.out.println(value5);

        System.out.println(value6);
        System.out.println(value7);
        System.out.println(value8);
        System.out.println(value9);
        System.out.println(value10);
    }

    public static void test3() {
        RandomizedCollection collection = new RandomizedCollection();// 初始化一个空的集合。
        boolean result1 = collection.insert(1);   // 返回 true，因为集合不包含 1。
        boolean result2 = collection.insert(1);   // 返回 false，因为集合包含 1。
        boolean result3 = collection.insert(2);   // 返回 true，因为集合不包含 2。
        boolean result4 = collection.insert(2);   // 返回 true，因为集合不包含 2。
        boolean result5 = collection.insert(2);   // 返回 true，因为集合不包含 2。

        boolean result6 = collection.remove(1);   // 返回 true，因为集合不包含 2。
        boolean result7 = collection.remove(1);
        boolean result8 = collection.remove(2);
        boolean result9 = collection.insert(1);
        boolean result10 = collection.remove(2);

        int value1 = collection.getRandom();
        int value2 = collection.getRandom();
        int value3 = collection.getRandom();
        int value4 = collection.getRandom();
        int value5 = collection.getRandom();

        int value6 = collection.getRandom();
        int value7 = collection.getRandom();
        int value8 = collection.getRandom();
        int value9 = collection.getRandom();
        int value10 = collection.getRandom();

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(result5);

        System.out.println(result6);
        System.out.println(result7);
        System.out.println(result8);
        System.out.println(result9);
        System.out.println(result10);

        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);
        System.out.println(value4);
        System.out.println(value5);

        System.out.println(value6);
        System.out.println(value7);
        System.out.println(value8);
        System.out.println(value9);
        System.out.println(value10);
    }

    public static void test4() {
        RandomizedCollection collection = new RandomizedCollection();// 初始化一个空的集合。
        boolean result1 = collection.insert(1);   // 返回 true，因为集合不包含 1。
        boolean result2 = collection.remove(1);   // 返回 false，因为集合包含 1。
        boolean result3 = collection.insert(1);   // 返回 true，因为集合不包含 2。

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
//        test4();
    }
}

class RandomizedCollection {

    // 存放元素对应的索引
    private final Map<Integer, Set<Integer>> val2IdxMap;
    private final List<Integer> table;

    public RandomizedCollection() {
        this.val2IdxMap = new HashMap<>();
        this.table = new ArrayList<>();
    }

    public boolean insert(int val) {
        table.add(val);
        Set<Integer> set = val2IdxMap.computeIfAbsent(val, k -> new HashSet<>());
        set.add(table.size() - 1);
        return set.size() == 1;
    }

    public boolean remove(int val) {
        if (!val2IdxMap.containsKey(val)) {
            return false;
        }
        Iterator<Integer> it = val2IdxMap.get(val).iterator();
        int i = it.next();
        int lastNum = table.get(table.size() - 1);
        table.set(i, lastNum);
        val2IdxMap.get(val).remove(i);
        val2IdxMap.get(lastNum).remove(table.size() - 1);
        if (i < table.size() - 1) {
            val2IdxMap.get(lastNum).add(i);
        }
        if (val2IdxMap.get(val).size() == 0) {
            val2IdxMap.remove(val);
        }
        table.remove(table.size() - 1);
        return true;
    }

    public int getRandom() {
        int nextIdx = new Random().nextInt(table.size());
        return table.get(nextIdx);
    }
}

/**
 * 哈希，但是双map
 */
class RandomizedCollection1 {

    // 存放元素对应的索引
    private final Map<Integer, List<Integer>> val2IdxMap;
    // 存放元素+索引 -> 索引列表中的下标
    private final Map<String, Integer> valIdx2IdxMap;
    private final List<Integer> table;
    private final Random random;

    public RandomizedCollection1() {
        this.val2IdxMap = new HashMap<>();
        this.valIdx2IdxMap = new HashMap<>();
        this.table = new ArrayList<>();
        this.random = new Random();
    }

    public boolean insert(int val) {
        if (!val2IdxMap.containsKey(val)) {
            table.add(val);
            List<Integer> indices = new ArrayList<>();
            indices.add(table.size() - 1);
            // 记录元素对应的索引
            val2IdxMap.put(val, indices);
            // map(val -> map(tableIdx -> indicesIdx))
            valIdx2IdxMap.put(generateKey(val, table.size() - 1), 0);
            return true;
        } else {
            table.add(val);
            // 记录元素对应的索引
            List<Integer> indices = val2IdxMap.get(val);
            indices.add(table.size() - 1);

            // map(val -> map(tableIdx -> indicesIdx))
            // 记录元素的索引的索引
            valIdx2IdxMap.put(generateKey(val, table.size() - 1), indices.size() - 1);
            return false;
        }
    }

    public boolean remove(int val) {
        if (val2IdxMap.containsKey(val)) {
            int tableSize = table.size();
            // 获取元素的索引，todo
            List<Integer> targetValIndices = val2IdxMap.get(val);
            // 获取元素的索引的尾节点
            Integer targetIdx = targetValIndices.get(targetValIndices.size() - 1);
            if (targetIdx == tableSize - 1) {
                targetValIndices.remove(targetValIndices.size() - 1);
                valIdx2IdxMap.remove(generateKey(val, targetIdx));
                table.remove((int) targetIdx);
                if (targetValIndices.isEmpty()) {
                    val2IdxMap.remove(val);
                }
                return true;
            }

            // 从table中获取尾节点的值
            Integer tailVal = table.get(tableSize - 1);
            // 获取尾结点对应的索引
            List<Integer> tailValIndices = val2IdxMap.get(tailVal);
            // 获取尾结点对应的索引的索引map
            String oldTailKey = generateKey(tailVal, tableSize - 1);
            Integer tailValIdx2Idx = valIdx2IdxMap.get(oldTailKey);
            // 交换尾节点，再删除尾节点
            Integer tailTailValIdx = tailValIndices.get(tailValIndices.size() - 1);
            tailValIndices.set(tailValIdx2Idx, tailTailValIdx);

            table.set(targetIdx, tailVal);
            table.remove(tableSize - 1);
            // 删除目标元素相关
            targetValIndices.remove(targetValIndices.size() - 1);
            valIdx2IdxMap.remove(generateKey(val, targetIdx));
            // 删除尾节点相关

            tailValIndices.remove(tailValIndices.size() - 1);
            // 删除旧引用
            valIdx2IdxMap.remove(oldTailKey);
            // 添加新索引
            tailValIndices.add(targetIdx);
            // 并且将新的索引对应的值，放入索引map
            valIdx2IdxMap.put(generateKey(tailVal, targetIdx), tailValIndices.size() - 1);

            if (targetValIndices.isEmpty()) {
                val2IdxMap.remove(val);
            }
            return true;
        }
        return false;
    }

    private static String generateKey(int val, int idx) {
        return val + "-" + idx;
    }

    public int getRandom() {
        int nextIdx = random.nextInt(table.size());
        return table.get(nextIdx);
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
