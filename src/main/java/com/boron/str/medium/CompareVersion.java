package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *  @description: 165. 比较版本号 <a href="https://leetcode.cn/problems/compare-version-numbers/submissions/663084144/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/15
 * </pre>
 */
public class CompareVersion {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String version1;
        String version2;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        int result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().version1("1.2").version2("1.10").build();
        Result result = Result.builder().result(-1).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().version1("1.01").version2("1.001").build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().version1("1.0").version2("1.0.0.0").build();
        Result result = Result.builder().result(0).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().version1("1.0.1").version2("1").build();
        Result result = Result.builder().result(1).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        int actualResult = CompareVersionSolution.compareVersion(param.getVersion1(), param.getVersion2());
        int expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());
//        test(generate4());
//        test(generate5());
//        test(generate6());
    }
}

class CompareVersionSolution {

    public static int compareVersion(String version1, String version2) {
        int len1 = version1.length(), len2 = version2.length();
        int i = 0, j = 0;
        char c1, c2;
        while (i < len1 || j < len2) {
            int tmp1 = 0;
            while (i < len1 && (c1 = version1.charAt(i)) != '.') {
                tmp1 = tmp1 * 10 + (c1 - '0');
                i++;
            }
            i++;
            int tmp2 = 0;
            while (j < len2 && (c2 = version2.charAt(j)) != '.') {
                tmp2 = tmp2 * 10 + (c2 - '0');
                j++;
            }
            j++;
            if (tmp1 > tmp2) {
                return 1;
            }
            if (tmp1 < tmp2) {
                return -1;
            }
        }
        return 0;
    }
}

class CompareVersionSolution1 {

    public static int compareVersion(String version1, String version2) {
        int len1 = version1.length(), len2 = version2.length();
        List<Integer> points1 = new ArrayList<>();
        List<Integer> points2 = new ArrayList<>();
        for (int i = 0; i < len1; i++) {
            if (version1.charAt(i) == '.') {
                points1.add(i);
            }
        }
        points1.add(len1);
        for (int i = 0; i < len2; i++) {
            if (version2.charAt(i) == '.') {
                points2.add(i);
            }
        }
        points2.add(len2);
        int commonLen = Math.min(points1.size(), points2.size());
        int left1 = 0, left2 = 0;
        for (int i = 0; i < commonLen; i++) {
            int right1 = points1.get(i);
            int right2 = points2.get(i);
            int tmp1 = 0, tmp2 = 0;
            for (int j = left1; j < right1; j++) {
                char c1 = version1.charAt(j);
                tmp1 += (c1 - '0') * (int) Math.pow(10, right1 - j - 1);
            }
            for (int k = left2; k < right2; k++) {
                char c2 = version2.charAt(k);
                tmp2 += (c2 - '0') * (int) Math.pow(10, right2 - k - 1);
            }
            left1 = right1 + 1;
            left2 = right2 + 1;
            if (tmp1 > tmp2) {
                return 1;
            } else if (tmp1 < tmp2) {
                return -1;
            }
        }
        if (points1.size() > points2.size()) {
            boolean beyond0 = remainderMoreThanZero(points2.size() - 1, points1, version1);
            return beyond0 ? 1 : 0;
        } else if (points1.size() < points2.size()) {
            boolean beyond0 = remainderMoreThanZero(points1.size() - 1, points2, version2);
            return beyond0 ? -1 : 0;
        }
        return 0;
    }

    public static boolean remainderMoreThanZero(int startIdx, List<Integer> points, String version) {
        int tmp = 0;
        int left = points.get(startIdx);
        for (int i = startIdx; i < points.size(); i++) {
            int right = points.get(i);
            for (int k = left; k < right; k++) {
                char c2 = version.charAt(k);
                tmp += (c2 - '0') * (int) Math.pow(10, right - k - 1);
            }
            if (tmp > 0) {
                return true;
            }
            left = right + 1;
        }
        return false;
    }
}