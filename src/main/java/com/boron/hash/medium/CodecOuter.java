package com.boron.hash.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <pre>
 *  @description: 535. TinyURL 的加密与解密 <a href="https://leetcode.cn/problems/encode-and-decode-tinyurl/description/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/24
 * </pre>
 */
public class CodecOuter {

    public static void test0() {
        Codec codec = new Codec();
        String encode = codec.encode("https://leetcode.com/problems/design-tinyurl");
        String decode = codec.decode(encode);

        System.out.println("encode: " + encode);
        System.out.println("decode: " + decode);
    }

    public static void main(String[] args) {
        test0();
    }
}

/**
 * 自增id
 */
class Codec {
    private Map<Integer, String> dataBase = new HashMap<Integer, String>();
    private int id;

    public String encode(String longUrl) {
        id++;
        dataBase.put(id, longUrl);
        return "http://tinyurl.com/" + id;
    }

    public String decode(String shortUrl) {
        int p = shortUrl.lastIndexOf('/') + 1;
        int key = Integer.parseInt(shortUrl.substring(p));
        return dataBase.get(key);
    }
}

/**
 * 随机数
 */
class Codec1 {

    private Map<String, String> map = new HashMap<>();
    private Random random = new Random();

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        int count = 0;
        int split = -1;
        for (int i = 0; i < longUrl.length(); i++) {
            if (longUrl.charAt(i) == '/') {
                count++;
            }
            if (count == 2) {
                split = i;
            }
        }
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(26);
        int i3 = random.nextInt(10);
        int i4 = random.nextInt(26);
        int i5 = random.nextInt(26);
        int i6 = random.nextInt(26);
        StringBuilder sb = new StringBuilder();
        sb.append(i1);
        sb.append((char) (i2 + 'a'));
        sb.append(i3);
        sb.append((char) (i4 + 'a'));
        sb.append((char) (i5 + 'A'));
        sb.append((char) (i6 + 'a'));
        String key = sb.toString();
        String value = longUrl.substring(split);
        map.put(key, value);
        return longUrl.substring(0, split) + "/" + key;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        int index = shortUrl.lastIndexOf("/");
        String key = shortUrl.substring(index + 1);
        String value = map.get(key);
        return shortUrl.substring(0, index) + value;
    }
}