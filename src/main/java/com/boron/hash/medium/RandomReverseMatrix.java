package com.boron.hash.medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <pre>
 *  @description: 519. 随机翻转矩阵 <a href="https://leetcode.cn/problems/random-flip-matrix/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/10
 * </pre>
 */
public class RandomReverseMatrix {

    public static void test() {
        RandomReverseMatrixSolution solution = new RandomReverseMatrixSolution(3, 1);
        int[] flip1 = solution.flip();// 返回 [1, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
        int[] flip2 = solution.flip();  // 返回 [2, 0]，因为 [1,0] 已经返回过了，此时返回 [2,0] 和 [0,0] 的概率应当相同
        int[] flip3 = solution.flip();  // 返回 [0, 0]，根据前面已经返回过的下标，此时只能返回 [0,0]
        solution.reset(); // 所有值都重置为 0 ，并可以再次选择下标返回
        int[] flip4 = solution.flip();  // 返回 [2, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
        System.out.println(Arrays.toString(flip1));
        System.out.println(Arrays.toString(flip2));
        System.out.println(Arrays.toString(flip3));
        System.out.println(Arrays.toString(flip4));
    }

    public static void main(String[] args) {
        test();
    }
}

class RandomReverseMatrixSolution {

    private int m;
    private int n;
    private int size;
    private Map<Integer, Integer> map = new HashMap<>();
    private Random random = new Random();


    public RandomReverseMatrixSolution(int m, int n) {
        this.m = m;
        this.n = n;
        this.size = m * n;
    }

    public int[] flip() {
        // 生成随机数
        int x = this.random.nextInt(size);
        // size-1
        size--;
        // 因为该随机数可能被使用过一次，所以，要从map中获取被使用的下标，没被使用则用自身
        int idx = map.getOrDefault(x, x);
        // 将当前下标，和当前size放入map，但是当前size所在下标也有可能被使用，所以还得从map中查找是否使用过
        map.put(x, map.getOrDefault(size, size));
        // 返回下标对应的坐标
        return new int[] {idx / n, idx % n};
    }

    public void reset() {
        map.clear();
        this.size = m * n;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(m, n);
 * int[] param_1 = obj.flip();
 * obj.reset();
 */
