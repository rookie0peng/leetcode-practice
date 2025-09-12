package com.boron.str.hard;

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
 *  @description: 68. 文本左右对齐 <a href="https://leetcode.cn/problems/text-justification/submissions/662139679/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/9/11
 * </pre>
 */
public class FullJustify {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {

        String[] words;
        int maxWidth;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<String> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().words(new String[] {"This", "is", "an", "example", "of", "text", "justification."}).maxWidth(16).build();
        Result result = Result.builder().results(List.of("This    is    an",
                "example  of text",
                "justification.  ")).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().words(new String[] {"What","must","be","acknowledgment","shall","be"}).maxWidth(16).build();
        Result result = Result.builder().results(List.of("What   must   be",
                "acknowledgment  ",
                "shall be        ")).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<String> actualResult = FullJustifySolution.fullJustify(param.getWords(), param.getMaxWidth());
        List<String> expectResult = result.getResults();
        boolean compareResult = Objects.equals(actualResult, expectResult);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(actualResult), JSON.toJSONString(expectResult));
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
//        test(generate1());
//        test(generate2());
//        test(generate3());
//        test(generate4());
    }
}

class FullJustifySolution {
    public static List<String> fullJustify(String[] words, int maxWidth) {
        // 是否进入窗口
        boolean enter = false;
        // 窗口左右端点
        int left = 0, right = 0;
        // 所有长度、单词长度、间隔长度
        int allLen = 0, wordsLen = 0, minGapLen = 0;
        // 结果集
        List<String> results = new ArrayList<>();
        // 遍历单词列表
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            // 如果未进入窗口
            if (!enter) {
                // 当前单词进入窗口，并记录左端点
                enter = true;
                left = i;
            }
            // 将当前下标赋值给右端点
            right = i;
            // 计算最小间隙长度
            minGapLen = right - left;
            // 计算窗口内所有单词长度
            wordsLen += word.length();
            // 单词长度+间隙长度
            allLen = minGapLen + wordsLen;
            // 总长度大于最大宽度
            if (allLen > maxWidth) {
                // 窗口右端点需往左移动一个下标
                int tmpRight = right - 1;
                // 计算新窗口的单词长度
                int tmpWordsLen = wordsLen - words[right].length();
                // 生成行
                results.add(generate(words, left, tmpRight, tmpWordsLen, maxWidth));
                // 重新赋值窗口左端点
                left = i;
                // 重新赋值窗口单词总长度
                wordsLen = word.length();
                // 如果窗口右端点是最后一个下标
                if (right == words.length - 1) {
                    results.add(generate(words, left, right, wordsLen, maxWidth));
                }
                // 总长度等于最大宽度
            } else if (allLen == maxWidth) {
                results.add(generate(words, left, right, wordsLen, maxWidth));
                // 设置未进入窗口
                enter = false;
                // 重新赋值窗口单词总长度
                wordsLen = 0;
            } else if (right == words.length - 1) {
                // 如果窗口右端点是最后一个下标
                results.add(generate(words, left, right, wordsLen, maxWidth));
            }
        }
        return results;
    }

    public static String generate(String[] words, int left, int right, int wordsLen, int maxWidth) {
        // 如果是最后一行，或者一行内只有一个单词
        if (right == words.length - 1 || left == right) {
            return generateLastLine(words, left, right, wordsLen, maxWidth);
        } else {
            return generateCommon(words, left, right, wordsLen, maxWidth);
        }
    }

    public static String generateLastLine(String[] words, int left, int right, int wordsLen, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        // 生成前面x个单词以及对应的间隙
        for (int i = left; i <= right; i++) {
            sb.append(words[i]);
            if (i != right) {
                sb.append(' ');
            }
        }
        // 生成后缀的间隙
        sb.append(" ".repeat(Math.max(0, maxWidth - sb.length())));
        return sb.toString();
    }

    public static String generateCommon(String[] words, int left, int right, int wordsLen, int maxWidth) {
        // 计算最大间隙长度
        int maxGapLen = maxWidth - wordsLen;
        // 计算平均间隙长度
        int gapLen = maxGapLen / (right - left);
        // 生成平均间隙字符串
        String gap = " ".repeat(Math.max(0, gapLen));
        // 计算余数
        int remainderLen = maxGapLen % (right - left);
        StringBuilder sb = new StringBuilder();
        // 生成行
        for (int i = left; i <= right; i++) {
            sb.append(words[i]);
            // 如果不等于右端点
            if (i != right) {
                // 添加间隙字符串
                sb.append(gap);
                // 如果余数大于0
                if (remainderLen > 0) {
                    // 将余数平均的补充到每一个间隙中
                    sb.append(' ');
                    remainderLen--;
                }
            }
        }
        return sb.toString();
    }
}
