package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 447. 回旋镖的数量 <a href="https://leetcode.cn/problems/number-of-boomerangs/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/7
 * </pre>
 */
public class NumberOfBoomerangs {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[][] points;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().points(new int[][] {{0,0},{1,0},{2,0}}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().points(new int[][] {{1,1},{2,2},{3,3}}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().points(new int[][] {{1,1}}).build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().points(new int[][] {{0,0},{1,0},{-1,0},{0,1},{0,-1}}).build();
        Result result = Result.builder().result(20).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = NumberOfBoomerangsSolution.numberOfBoomerangs(param.getPoints());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
    }
}

class NumberOfBoomerangsSolution {


    /**
     * 题目所描述的回旋镖可以视作一个 V 型的折线。我们可以枚举每个 points[i]，将其当作 V 型的拐点。设 points 中有 m 个点到 points[i] 的距离均相等，我们需要从这 m 点中选出 2 个点当作回旋镖的 2 个端点，由于题目要求考虑元组的顺序，因此方案数即为在 m 个元素中选出 2 个不同元素的排列数，即：
     *
     * A^2_m=m⋅(m−1)
     * 据此，我们可以遍历 points，计算并统计所有点到 points[i] 的距离，将每个距离的出现次数记录在哈希表中，然后遍历哈希表，并用上述公式计算并累加回旋镖的个数。
     *
     * 在代码实现时，我们可以直接保存距离的平方，避免复杂的开方运算。
     *
     * 作者：力扣官方题解
     * 链接：https://leetcode.cn/problems/number-of-boomerangs/solutions/994189/hui-xuan-biao-de-shu-liang-by-leetcode-s-lft5/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public static int numberOfBoomerangs(int[][] points) {
        int result = 0;
        // 获取任意两个点的距离
        // 遍历第一个点
        for (int[] a : points) {
            // 距离->数量
            Map<Integer, Integer> map = new HashMap<>();
            // 遍历第二个点
            for (int[] b : points) {
                int d_2 = (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]);
                map.put(d_2, map.getOrDefault(d_2, 0) + 1);
            }
            // 从相同距离的点中选取两个，A^2_m = m * ( m - 1)
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int d_2count = entry.getValue();
                result += d_2count * (d_2count - 1);
            }
        }
        return result;
    }

}
