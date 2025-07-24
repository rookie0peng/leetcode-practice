package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  @description: 554. 砖墙 <a href="https://leetcode.cn/problems/brick-wall/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/24
 * </pre>
 */
public class LeastBricks {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        List<List<Integer>> wall;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().wall(List.of(List.of(1,2,2,1), List.of(3,1,2), List.of(1,3,2), List.of(2,4), List.of(3,1,2), List.of(1,3,1,1))).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().wall(List.of(List.of(1), List.of(1), List.of(1))).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = LeastBricksSolution.leastBricks(param.getWall());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

class LeastBricksSolution {

    public static int leastBricks(List<List<Integer>> wall) {
        // 记录每个位置的缝隙出现的次数
        Map<Integer, Integer> gapCountMap = new HashMap<>();
        // 最大缝隙数量
        int maxGapCount = -1;
        for (List<Integer> iw : wall) {
            // 前缀和，缝隙位置
            int prefixSum = 0;
            for (int i = 0; i < iw.size(); i++) {
                // 如果i是最后一个，则无需作为缝隙
                if (i == iw.size() - 1) {
                    break;
                }
                // 前缀和
                prefixSum += iw.get(i);
                // 获取缝隙数量
                int count = gapCountMap.getOrDefault(prefixSum, 0) + 1;
                // 记录缝隙数量
                gapCountMap.put(prefixSum, count);
                // 记录最大缝隙数量
                maxGapCount = Math.max(maxGapCount, count);
            }
        }
        // 如果最大缝隙数量为-1，则穿过的砖块数为wall.size
        if (maxGapCount == -1) {
            return wall.size();
        }
        // 否则，用砖块总数-最大缝隙数，即穿过的最小砖块数
        int minBrick = wall.size() - maxGapCount;
        return minBrick;
    }
}