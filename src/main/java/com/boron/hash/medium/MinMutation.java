package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *  @description: 433. 最小基因变化 <a href="https://leetcode.cn/problems/minimum-genetic-mutation/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/6
 * </pre>
 */
public class MinMutation {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String startGene;
        String endGene;
        String[] bank;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().startGene("AACCGGTT").endGene("AACCGGTA").bank(new String[] {"AACCGGTA"}).build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().startGene("AACCGGTT").endGene("AAACGGTA").bank(new String[] {"AACCGGTA","AACCGCTA","AAACGGTA"}).build();
        Result result = Result.builder().result(2).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().startGene("AAAAACCC").endGene("AACCCCCC").bank(new String[] {"AAAACCCC","AAACCCCC","AACCCCCC"}).build();
        Result result = Result.builder().result(3).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().startGene("AAAACCCC").endGene("CCCCCCCC").bank(new String[] {"AAAACCCA","AAACCCCA","AACCCCCA","AACCCCCC","ACCCCCCC","CCCCCCCC","AAACCCCC","AACCCCCC"}).build();
        Result result = Result.builder().result(4).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = MinMutationSolution.minMutation(param.getStartGene(), param.getEndGene(), param.getBank());
        int expectResult = result.getResult();
        boolean compareResult = actualResult == expectResult;
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", actualResult, expectResult);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
//        test(generate2());
        test(generate3());
    }
}

class MinMutationSolution {



    public static int minMutation(String startGene, String endGene, String[] bank) {
        // 候选基因集合
        Set<String> candidateGenes = new HashSet<>();
        // 遍历基因库，需排除掉起始基因
        for (String gene : bank) {
            if (Objects.equals(startGene, gene)) {
                continue;
            }
            candidateGenes.add(gene);
        }
        // 已使用基因集合
        Set<String> usedGenes = new HashSet<>();
        // 结果集，只取最小值
        AtomicInteger countRef = new AtomicInteger();
        countRef.set(Integer.MAX_VALUE);
        // 递归
        dfs(candidateGenes, usedGenes, startGene, endGene, countRef);
        return countRef.get() == Integer.MAX_VALUE ? -1 : countRef.get();
    }

    public static void dfs(Set<String> candidateGenes, Set<String> usedGenes, String preGene, String endGene, AtomicInteger countRef) {
        // 如果前一个到达的基因等于终止基因
        if (Objects.equals(preGene, endGene)) {
            // 设置最小步骤
            countRef.set(Math.min(countRef.get(), usedGenes.size()));
            return;
        }
        // 遍历候选基因
        for (String gene : candidateGenes) {
            // 排除掉已使用的基因
            if (usedGenes.contains(gene)) {
                continue;
            }
            // 比对遍历基因和前一个基因是否只相差一个字符
            int countDiff = 0;
            for (int i = 0; i < gene.length(); i++) {
                if (gene.charAt(i) != preGene.charAt(i)) {
                    countDiff++;
                }
            }
            // 如果不是只差一个字符，则跳过到下一个循环
            if (countDiff != 1) {
                continue;
            }
            // 添加到已使用基因
            usedGenes.add(gene);
            // 递归调用
            dfs(candidateGenes, usedGenes, gene, endGene, countRef);
            // 移出已使用基因
            usedGenes.remove(gene);
        }
    }

}
