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
 *  @description: 38. 外观数列 <a href="https://leetcode.cn/problems/count-and-say/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/2
 * </pre>
 */
public class CountAndSay {

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
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().n(4).build();
        Result result = Result.builder().result("1211").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().n(1).build();
        Result result = Result.builder().result("1").build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = CountAndSaySolution.countAndSay(param.getN());
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
//        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

class CountAndSaySolution {
    public static String countAndSay(int n) {
        String tmp = "1";
        // 从1遍历到n
        for (int i = 1; i < n; i++) {
            // 生成的结果继续作为参数使用
            tmp = deal(tmp);
        }
        return tmp;
    }

    /**
     * 将数字字符转为外观数列
     */
    public static String deal(String num) {
        // 初始化i=1
        int i = 1;
        // 初始化当前字符和上一个字符
        char now = num.charAt(0);
        char last = now;
        // 统计上一个字符的数量
        int count = 1;
        // 存放结果集的builder
        StringBuilder sb = new StringBuilder();
        // 遍历字符串num
        while (i < num.length()) {
            // 获取当前字符
            now = num.charAt(i);
            // 如果前一个字符不等于当前字符
            if (last != now) {
                // 将结果添加进sb
                sb.append(count);
                sb.append(last);
                // 重置last和count
                last = now;
                count = 1;
            } else {
                // 如果相等，则计数+1
                count++;
            }
            i++;
        }
        // 将最后一个字符放入sb
        sb.append(count);
        sb.append(last);
        return sb.toString();
    }
}
