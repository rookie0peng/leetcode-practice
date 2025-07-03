package com.boron.hash.medium;

import java.util.*;

/**
 * <pre>
 *  @description: 355. 设计推特 <a href="https://leetcode.cn/problems/design-twitter/solutions/199331/she-ji-tui-te-by-leetcode-solution/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: qingpeng
 *  @date: 2025/7/3
 * </pre>
 */
public class TwitterOuter {

    public static void test() {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5); // 用户 1 发送了一条新推文 (用户 id = 1, 推文 id = 5)
        System.out.println(twitter.getNewsFeed(1));  // 用户 1 的获取推文应当返回一个列表，其中包含一个 id 为 5 的推文
        twitter.follow(1, 2);    // 用户 1 关注了用户 2
        twitter.postTweet(2, 6); // 用户 2 发送了一个新推文 (推文 id = 6)
        System.out.println(twitter.getNewsFeed(1));  // 用户 1 的获取推文应当返回一个列表，其中包含两个推文，id 分别为 -> [6, 5] 。推文 id 6 应当在推文 id 5 之前，因为它是在 5 之后发送的
        twitter.unfollow(1, 2);  // 用户 1 取消关注了用户 2
        System.out.println(twitter.getNewsFeed(1));  // 用户 1 获取推文应当返回一个列表，其中包含一个 id 为 5 的推文。因为用户 1 已经不再关注用户 2
    }

    public static void test2() {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 4); // 用户 1 发送了一条新推文 (用户 id = 1, 推文 id = 5)
        twitter.postTweet(2, 5); // 用户 2 发送了一个新推文 (推文 id = 6)
        twitter.unfollow(1, 2);  // 用户 1 取消关注了用户 2
        twitter.follow(1, 2);  // 用户 1 取消关注了用户 2
        System.out.println(twitter.getNewsFeed(1));  // 用户 1 获取推文应当返回一个列表，其中包含一个 id 为 5 的推文。因为用户 1 已经不再关注用户 2
    }

    public static void main(String[] args) {
//        test();
        test2();
    }
}

class Twitter {

    static class Content {
        int twitterId;
        long date;

        public Content(int twitterId, long date) {
            this.twitterId = twitterId;
            this.date = date;
        }

        public int getTwitterId() {
            return twitterId;
        }

        public void setTwitterId(int twitterId) {
            this.twitterId = twitterId;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }
    }

    private final Map<Integer, Set<Integer>> userFollowMap = new HashMap<>();

    private final Map<Integer, List<Content>> userTwitterMap = new HashMap<>();

    public Twitter() {

    }

    public void postTweet(int userId, int tweetId) {
        userTwitterMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(0, new Content(tweetId, System.nanoTime()));
    }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> follows = userFollowMap.get(userId);
        List<Content> results = new ArrayList<>();
        List<Content> selfContents = userTwitterMap.getOrDefault(userId, List.of());
        results.addAll(selfContents.stream().limit(10).toList());
        if (follows != null) {
            for (final Integer followId : follows) {
                List<Content> followContents = userTwitterMap.getOrDefault(followId, List.of());
                results.addAll(followContents.stream().limit(10).toList());
            }
        }
        List<Integer> finalResults = results.stream().sorted(Comparator.comparing(Content::getDate).reversed()).limit(10).map(Content::getTwitterId).toList();
        return finalResults;
    }

    public void follow(int followerId, int followeeId) {
        userFollowMap.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        userFollowMap.computeIfAbsent(followerId, k -> new HashSet<>()).remove(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
