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
 *  @description: 71. 简化路径 <a href="https://leetcode.cn/problems/simplify-path/submissions/662157463/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/12
 * </pre>
 */
public class SimplifyPath {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String path;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        String result;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().path("/home/").build();
        Result result = Result.builder().result("/home").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().path("/home//foo/").build();
        Result result = Result.builder().result("/home/foo").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().path("/home/user/Documents/../Pictures").build();
        Result result = Result.builder().result("/home/user/Pictures").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().path("/../").build();
        Result result = Result.builder().result("/").build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate4() {
        Param param = Param.builder().path("/.../a/../b/c/../d/./").build();
        Result result = Result.builder().result("/.../b/d").build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        String actualResult = SimplifyPathSolution.simplifyPath(param.getPath());
        String expectResult = result.getResult();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
        test(generate4());
    }
}

class SimplifyPathSolution {
    public static String simplifyPath(String path) {
        // 返回集
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        // 是否进入窗口
        boolean enter = false;
        // 窗口左右端点
        int left = 0, right = 0;
        // 单词列表，将路径切割为x个单词
        List<String> words = new ArrayList<>();
        // 遍历路径
        for (int i = 0; i < path.length(); i++) {
            // 如果当前字符是 '/'
            if (path.charAt(i) == '/') {
                // 如果进入窗口
                if (enter) {
                    // 获取窗口内字符串
                    String tmpStr = path.substring(left, right + 1);
                    // 处理字符串
                    process(words, tmpStr);
                    // 退出窗口
                    enter = false;
                }
                // 跳过当前循环
                continue;
            }
            // 如果未进入窗口
            if (!enter) {
                // 设置为已进入
                enter = true;
                // 记录左端点
                left = i;
            }
            // 记录右端点
            right = i;
            // 如果右端点等于最后一个下标
            if (right == path.length() - 1) {
                // 获取对应的子字符串，并处理
                String tmpStr = path.substring(left, right + 1);
                process(words, tmpStr);
            }
        }
        // 遍历生成的字符串列表，将其转为简化路径
        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i));
            if (i != words.size() - 1) {
                sb.append('/');
            }
        }
        return sb.toString();
    }

    public static void process(List<String> words, String word) {
        // 如果字符串为 '.'，跳过
        if (Objects.equals(word, ".")) {
            return;
        } else if (Objects.equals(word, "..")) {
            // 如果字符串为 ".."，删除上一个字符串（如有）
            if (!words.isEmpty()) {
                words.remove(words.size() - 1);
            }
        } else {
            // 否则，添加字符串
            words.add(word);
        }
    }
}
