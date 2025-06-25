package com.boron.hash.easy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  @description: 202. 快乐数 <a href="https://leetcode.cn/problems/happy-number/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/24
 * </pre>
 */
public class IsHappyNumber {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int n;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        boolean result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().n(19).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().n(2).build();
        Result result = Result.builder().result(false).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().n(31).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().n(1563712132).build();
        Result result = Result.builder().result(true).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        boolean actualResult = IsHappyNumberSolution.isHappy(param.getN());
        boolean expectResult = result.isResult();
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

class IsHappyNumberSolution {

    private static int getNext(int num) {
        int a = 0;
        int b = num;
        int c = 0;
        while (b > 0) {
            a = b % 10;
            b = b / 10;
            c += a * a;
        }
        return c;
    }

    /**
     * 快慢指针
     */
    public static boolean isHappy(int n) {
        // 快慢指针
        int slow = n;
        int fast = n;
        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        } while (fast != 1 && slow != fast);
        return fast == 1;
    }

    /**
     * 哈希
     * @param n
     * @return
     */
    public static boolean isHappy1(int n) {
        int tmpNumber = n;
        int tmpRemainder = 0;
        int tmpQuotient = 0;
        int tmpHappyNum = 0;
        Set<Integer> happyNums = new HashSet<>();
        boolean isCycle = false;
        boolean isHappy = false;
        while (!isHappy && !isCycle) {
            tmpHappyNum = 0;
            while (tmpNumber > 0) {
                tmpRemainder = tmpNumber % 10;
                tmpQuotient = tmpNumber / 10;
                tmpHappyNum += tmpRemainder * tmpRemainder;
                if (tmpQuotient == 0) {
                    if (happyNums.contains(tmpHappyNum)) {
                        isCycle = true;
                        break;
                    }
                    happyNums.add(tmpHappyNum);
                    break;
                }
                tmpNumber = tmpQuotient;
            }
            if (tmpHappyNum == 1) {
                isHappy = true;
                break;
            }
            tmpNumber = tmpHappyNum;
        }
        return isHappy;
    }
}