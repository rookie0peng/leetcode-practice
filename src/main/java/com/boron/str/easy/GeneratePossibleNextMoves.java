package com.boron.str.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  @description: 293. 翻转游戏 <a href="https://leetcode.cn/problems/flip-game/description/?envType=problem-list-v2&envId=string">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/10/13
 * </pre>
 */
public class GeneratePossibleNextMoves {
}

class Solution {
    public List<String> generatePossibleNextMoves(String currentState) {
        List<String> results = new ArrayList<>();
        char[] stateArr = currentState.toCharArray();
        for (int i = 1; i < stateArr.length; i++) {
            if (stateArr[i] == '+' && stateArr[i - 1] == '+') {
                stateArr[i - 1] = '-';
                stateArr[i] = '-';
                results.add(new String(stateArr));
                stateArr[i - 1] = '+';
                stateArr[i] = '+';
            }
        }
        return results;
    }
}
