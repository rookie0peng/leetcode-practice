package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * <pre>
 *  @description: 6. Z 字形变换 <a href="https://leetcode.cn/problems/zigzag-conversion/submissions/654452454/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/8/19
 * </pre>
 */
public class ZCharConvert {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String s;
        int numRows;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().s("PAYPALISHIRING").numRows(3).build();
        Result result = Result.builder().result("PAHNAPLSIIGYIR").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().s("PAYPALISHIRING").numRows(4).build();
        Result result = Result.builder().result("PINALSIGYAHRPI").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().s("A").numRows(1).build();
        Result result = Result.builder().result("A").build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = ZCharConvertSolution.convert(param.getS(), param.getNumRows());
        String expectResult = result.getResult();
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
    }
}

class ZCharConvertSolution {

    public static String convert(String s, int numRows) {
        // 如果行数为1，则直接返回原字符串
        if (numRows == 1) {
            return s;
        }
        int len = s.length();
        char[] sArr = s.toCharArray();
        // 记录结果字符串
        StringBuilder sb = new StringBuilder();
        // z字形，每一个帧的字符数
        int frame = numRows - 2 + numRows;
        // 根据每一个帧的数量，计算所有的环，往上扩展一个
        int allCircle = len / frame + 1;
        // 遍历所有行
        for (int i = 0; i < numRows; i++) {
            // 遍历所有环
            for (int j = 0; j < allCircle; j++) {
                // 计算下一个字符在字符串中的位置
                int idx0 = frame * j + i;
                // 如果字符的位置不超过字符串长度，就将字符添加进结果集
                if (idx0 < len) {
                    sb.append(sArr[idx0]);
                }
                // 如果行数不是第一行和最后一行，则需要第二个字符
                if (i != 0 && i != numRows - 1) {
                    // 第二个字符的下标，通过下一个环的下标减去行数即可
                    int idx1 = frame * (j + 1) - i;
                    // 如果第二个字符的下标不超过字符串长度，则添加进结果集
                    if (idx1 < len) {
                        sb.append(sArr[idx1]);
                    }
                }
            }
        }
        String result = sb.toString();
        return result;
    }
}
