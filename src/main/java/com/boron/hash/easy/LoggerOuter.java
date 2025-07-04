package com.boron.hash.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  @description: 359. 日志速率限制器 <a href="https://leetcode.cn/problems/logger-rate-limiter/submissions/641202996/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/4
 * </pre>
 */
public class LoggerOuter {

    public static void test() {
        Logger logger = new Logger();
        boolean print1 = logger.shouldPrintMessage(1, "foo");  // 返回 true ，下一次 "foo" 可以打印的时间戳是 1 + 10 = 11
        boolean print2 = logger.shouldPrintMessage(2, "bar");  // 返回 true ，下一次 "bar" 可以打印的时间戳是 2 + 10 = 12
        boolean print3 = logger.shouldPrintMessage(3, "foo");  // 3 < 11 ，返回 false
        boolean print4 = logger.shouldPrintMessage(8, "bar");  // 8 < 12 ，返回 false
        boolean print5 = logger.shouldPrintMessage(10, "foo"); // 10 < 11 ，返回 false
        boolean print6 = logger.shouldPrintMessage(11, "foo"); // 11 >= 11 ，返回 true ，下一次 "foo" 可以打印的时间戳是 11 + 10 = 21
        System.out.println(print1);
        System.out.println(print2);
        System.out.println(print3);
        System.out.println(print4);
        System.out.println(print5);
        System.out.println(print6);
    }

    public static void main(String[] args) {
        test();
    }
}

class Logger {

    private final Map<String, Integer> messageTimeMap = new HashMap<>();

    public Logger() {

    }

    /**
     * 哈希
     */
    public boolean shouldPrintMessage(int timestamp, String message) {
        Integer lastTime = messageTimeMap.get(message);
        if (lastTime != null) {
            if (timestamp >= lastTime + 10) {
                messageTimeMap.put(message, timestamp);
                return true;
            } else {
                return false;
            }
        } else {
            messageTimeMap.put(message, timestamp);
        }
        return true;
    }
}

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */