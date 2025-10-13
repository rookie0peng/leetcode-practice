package com.boron.str.medium;

import com.alibaba.fastjson2.JSON;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <pre>
 *  @description: 271. 字符串的编码与解码 <a href="https://leetcode.cn/problems/encode-and-decode-strings/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/13
 * </pre>
 */
public class CodecOuter {

    public static void test1() {
        // 1 号机：
        List<String> params = List.of("Hello","World");
        Codec encoder = new Codec();
        String msg = encoder.encode(params);
        // Machine 1 ---msg---> Machine 2

        // 2 号机：
        Codec decoder = new Codec();
        List<String> results = decoder.decode(msg);
        System.out.println(Objects.equals(params, results));

        boolean compareResult = Objects.equals(params, results);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(params), JSON.toJSONString(results));
        System.out.println("compareResult: " + compareResult);
    }

    public static void test2() {
        // 1 号机：
        List<String> params = List.of("");
        Codec encoder = new Codec();
        String msg = encoder.encode(params);
        // Machine 1 ---msg---> Machine 2

        // 2 号机：
        Codec decoder = new Codec();
        List<String> results = decoder.decode(msg);
        System.out.println(Objects.equals(params, results));

        boolean compareResult = Objects.equals(params, results);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(params), JSON.toJSONString(results));
        System.out.println("compareResult: " + compareResult);
    }

    public static void test3() {
        // 1 号机：
        List<String> params = List.of(",");
        Codec encoder = new Codec();
        String msg = encoder.encode(params);
        // Machine 1 ---msg---> Machine 2

        // 2 号机：
        Codec decoder = new Codec();
        List<String> results = decoder.decode(msg);
        System.out.println(Objects.equals(params, results));

        boolean compareResult = Objects.equals(params, results);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(params), JSON.toJSONString(results));
        System.out.println("compareResult: " + compareResult);
    }

    public static void test4() {
        // 1 号机：
        List<String> params = List.of("\"");
        Codec encoder = new Codec();
        String msg = encoder.encode(params);
        // Machine 1 ---msg---> Machine 2

        // 2 号机：
        Codec decoder = new Codec();
        List<String> results = decoder.decode(msg);
        System.out.println(Objects.equals(params, results));

        boolean compareResult = Objects.equals(params, results);
        System.out.println("actualResult vs expectResult");
        System.out.printf("%s vs %s\n", JSON.toJSONString(params), JSON.toJSONString(results));
        System.out.println("compareResult: " + compareResult);
    }

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

}

/**
 * 分块编码，参考HTTP1.1
 */
class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(intToHexStr(str.length()));
            sb.append(str);
        }
        String result = sb.toString();
//        System.out.println(result);
        return result;
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        int i = 0;
        List<String> results = new ArrayList<>();
        while (i < s.length()) {
            String lenStr = s.substring(i, i + 4);
            int len = hexToInt(lenStr);

            results.add(s.substring(i + 4, i + 4 + len));
            i += 4 + len;
        }
        return results;
    }


    private static String intToHexStr(int num) {
        char[] chars = new char[4];
        int tmp = num;
        int mod = 0;
        for (int i = 3; i > -1; i--) {
            mod = tmp % 16;
            char c = mod > 9 ? (char) ('A' + (mod - 10)) : (char) ('0' + mod);
            chars[i] = c;
            tmp = tmp / 16;
        }
        return new String(chars);
    }

    private static int hexToInt(String hex) {
        int res = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            if (c >= '0' && c <= '9') {
                res = res * 16 + (c - '0');
            } else {
                res = res * 16 + (c - 'A' + 10);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String string = intToHexStr(15);
        System.out.println(string);
        System.out.println((char) (16 >> 1 & 0xff));
        System.out.println( 16 >> 1 & 0xff);
    }
}

/**
 * 序列化思路
 */
class Codec1 {

//    private static Set<Character> ENCODE_CHARS = Set.of('"', ',', '[', ']', '\\');
    private static Set<Character> ENCODE_CHARS = Set.of(',', '"', '\\');
//    private static Set<Character> ENCODE_CHARS = Set.of(',');

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.size(); i++) {
            sb.append('"');
            for (int j = 0; j < strs.get(i).length(); j++) {
                char c = strs.get(i).charAt(j);
                if (ENCODE_CHARS.contains(c)) {
                    sb.append('\\');
                }
                sb.append(c);
            }
            sb.append('"');
            if (i != strs.size() - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> results = new ArrayList<>();
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < s.length();) {
            char c = s.charAt(i);
            if (c == '\\') {
                if (ENCODE_CHARS.contains(s.charAt(i + 1))) {
                    sb1.append(s.charAt(i + 1));
                    i++;
                } else {
                    sb1.append(c);
                }
            } else if (c == '"') {
                if (i == s.length() - 1 || s.charAt(i + 1) == ',') {
                    // skip
                    results.add(sb1.toString());
                    i++;
                    sb1 = new StringBuilder();
                }
            } else {
                sb1.append(c);
            }
            i++;
        }
        return results;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));
