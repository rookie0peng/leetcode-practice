package com.boron.hash.medium;

import java.util.*;

/**
 * <pre>
 *  @description: 380. O(1) 时间插入、删除和获取随机元素 <a href="https://leetcode.cn/problems/insert-delete-getrandom-o1/submissions/641232905/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class RandomizedSetOuter {

    public static void test() {
        RandomizedSet randomizedSet = new RandomizedSet();
        boolean result1 = randomizedSet.insert(1); // 向集合中插入 1 。返回 true 表示 1 被成功地插入。
        boolean result2 = randomizedSet.remove(2); // 返回 false ，表示集合中不存在 2 。
        boolean result3 = randomizedSet.insert(2); // 向集合中插入 2 。返回 true 。集合现在包含 [1,2] 。
        int value1 = randomizedSet.getRandom(); // getRandom 应随机返回 1 或 2 。
        boolean result4 = randomizedSet.remove(1); // 从集合中移除 1 ，返回 true 。集合现在包含 [2] 。
        boolean result5 = randomizedSet.insert(2); // 2 已在集合中，所以返回 false 。
        int value2 = randomizedSet.getRandom(); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(result5);
        System.out.println(value1);
        System.out.println(value2);
    }

    public static void main(String[] args) {
        test();
    }
}

class RandomizedSet {

    private final Map<Integer, Integer> val2IdxMap;
    private final List<Integer> table;
    private final Random random = new Random();

    public RandomizedSet() {
        val2IdxMap = new HashMap<>();
        table = new ArrayList<>();
    }

    public boolean insert(int val) {
        if (!val2IdxMap.containsKey(val)){
            table.add(val);
            val2IdxMap.put(val, table.size() - 1);
            return true;
        }
        return false;
    }

    /**
     * 移除元素
     * 先找到指定元素，然后将其与尾节点进行交换，再移除尾节点，这样只需要修改一个即可达成目标
     */
    public boolean remove(int val) {
        if (val2IdxMap.containsKey(val)) {
            Integer idx = val2IdxMap.get(val);
            Integer tailVal = table.get(table.size() - 1);
            table.set(idx, tailVal);
            val2IdxMap.put(tailVal, idx);
            val2IdxMap.remove(val);
            table.remove(table.size() - 1);
            return true;
        }
        return false;
    }

    public int getRandom() {
        int nextIdx = random.nextInt(table.size());
        return table.get(nextIdx);
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
