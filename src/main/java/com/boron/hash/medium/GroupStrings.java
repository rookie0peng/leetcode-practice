package com.boron.hash.medium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 249. 移位字符串分组 <a href="https://leetcode.cn/problems/group-shifted-strings/submissions/639470740/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/6/26
 * </pre>
 */
public class GroupStrings {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        String[] strings;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        List<List<String>> results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().strings(new String[]{"abc","bcd","acef","xyz","az","ba","a","z"}).build();
        Result result = Result.builder().results(List.of(
                List.of("acef"),
                List.of("a","z"),
                List.of("abc","bcd","xyz"),
                List.of("az","ba")
        )).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().strings(new String[]{"qzq", "sbs", "lebzshcb", "ohecvkfe", "gkxpnpbfvjm", "xbogegswmad"}).build();
        Result result = Result.builder().results(List.of(
                List.of("qzq","sbs")
        )).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().strings(new String[]{"fpbnsbrkbcyzdmmmoisaa","cpjtwqcdwbldwwrryuclcngw","a","fnuqwejouqzrif","js","qcpr","zghmdiaqmfelr","iedda","l","dgwlvcyubde","lpt","qzq","zkddvitlk","xbogegswmad","mkndeyrh","llofdjckor","lebzshcb","firomjjlidqpsdeqyn","dclpiqbypjpfafukqmjnjg","lbpabjpcmkyivbtgdwhzlxa","wmalmuanxvjtgmerohskwil","yxgkdlwtkekavapflheieb","oraxvssurmzybmnzhw","ohecvkfe","kknecibjnq","wuxnoibr","gkxpnpbfvjm","lwpphufxw","sbs","txb","ilbqahdzgij","i","zvuur","yfglchzpledkq","eqdf","nw","aiplrzejplumda","d","huoybvhibgqibbwwdzhqhslb","rbnzendwnoklpyyyauemm"}).build();
        Result result = Result.builder().results(List.of(
                List.of("a","l","i","d"),
                List.of("eqdf","qcpr"),
                List.of("lpt","txb"),
                List.of("yfglchzpledkq","zghmdiaqmfelr"),
                List.of("kknecibjnq","llofdjckor"),

                List.of("cpjtwqcdwbldwwrryuclcngw","huoybvhibgqibbwwdzhqhslb"),
                List.of("lbpabjpcmkyivbtgdwhzlxa","wmalmuanxvjtgmerohskwil"),
                List.of("iedda","zvuur"),
                List.of("js","nw"),
                List.of("lebzshcb","ohecvkfe"),

                List.of("dgwlvcyubde","ilbqahdzgij"),
                List.of("lwpphufxw","zkddvitlk"),
                List.of("qzq","sbs"),
                List.of("dclpiqbypjpfafukqmjnjg","yxgkdlwtkekavapflheieb"),
                List.of("mkndeyrh","wuxnoibr"),

                List.of("firomjjlidqpsdeqyn","oraxvssurmzybmnzhw"),
                List.of("gkxpnpbfvjm","xbogegswmad"),
                List.of("fpbnsbrkbcyzdmmmoisaa","rbnzendwnoklpyyyauemm"),
                List.of("aiplrzejplumda","fnuqwejouqzrif")
        )).build();
        return Pair.of(param, result);
    }

    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        List<List<String>> actualResults = GroupStringsSolution.groupStrings(param.getStrings());
        List<List<String>> actualResults2 = actualResults.stream().map(v -> v.stream().sorted().toList()).sorted(Comparator.comparing(x -> x.isEmpty() ? "1" : x.get(0))).toList();
        List<List<String>> expectResults = result.getResults();
        List<List<String>> expectResults2 = expectResults.stream().map(v -> v.stream().sorted().toList()).sorted(Comparator.comparing(x -> x.isEmpty() ? "1" : x.get(0))).toList();
        boolean compareResult = Objects.equals(actualResults2, expectResults2);
        System.out.println("expectResults vs expectResults");
        System.out.printf("%s vs %s\n", actualResults2, expectResults2);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
//        test(generate0());
//        test(generate1());
        test(generate2());
    }
}

class GroupStringsSolution {
    public static List<List<String>> groupStrings(String[] strings) {
        // 将字符串按照长度进行分组
        Map<Integer, List<String>> groupStrings = new HashMap<>();
        for (String str : strings) {
            int key = str.length();
            List<String> values = groupStrings.computeIfAbsent(key, k -> new ArrayList<>());
            values.add(str);
        }
        // 结果集
        List<List<String>> results = new ArrayList<>();
        // 遍历分组后的字符串
        for (Map.Entry<Integer, List<String>> entry : groupStrings.entrySet()) {
            List<String> values = entry.getValue();
            // 临时结果集
            List<List<String>> tmpResults = new ArrayList<>();
            // 遍历该分组下的字符串集合
            for (String value : values) {
                // 如果临时结果集为空，则放入第一个元素
                if (tmpResults.isEmpty()) {
                    List<String> innerTmpResults = new ArrayList<>();
                    innerTmpResults.add(value);
                    tmpResults.add(innerTmpResults);
                    continue;
                }
                // 初始化匹配结果为true
                boolean match = true;
                // 遍历临时结果集
                for (List<String> innerTmpResults : tmpResults) {
                    // 再次将匹配结果重置为true
                    match = true;
                    // 取出第一个字符串
                    String preStr = innerTmpResults.get(0);
                    // 获取内部结果集中的第一个字符串的第一个字符与待匹配字符串的第一个字符的距离
                    int preDistance = value.charAt(0) - preStr.charAt(0);
                    // 匹配两个字符串中后续每个字符的距离
                    for (int i = 0; i < preStr.length(); i++) {
                        // 根据距离推算下一个字符
                        int expectChar = preStr.charAt(i) + preDistance;
                        // 如果字符小于a或者大于z，进行相应的加减操作，将该字符重置到a与z之间
                        if (expectChar < 'a') {
                            expectChar = expectChar + 26;
                        } else if (expectChar > 'z') {
                            expectChar = expectChar - 26;
                        }
                        // 如果预期字符不等于value中的指定位置字符，则说明匹配失败，中断循环，进行下一组结果集的匹配
                        if (expectChar != value.charAt(i)) {
                            match = false;
                            break;
                        }
                    }
                    // 如果匹配成功，将该字符串放入内部临时结果集中，中断循环
                    if (match) {
                        innerTmpResults.add(value);
                        break;
                    }
                }
                // 如果没匹配成功，则将该字符单独放入一个新的结果集分组
                if (!match) {
                    List<String> innerTmpResults = new ArrayList<>();
                    innerTmpResults.add(value);
                    tmpResults.add(innerTmpResults);
                }
            }
            // 将临时结果集放入最终结果集
            results.addAll(tmpResults);
        }
        // 返回结果集
        return results;
    }
}
