package com.boron.hash.hard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * <pre>
 *  @description: 480. 滑动窗口中位数 <a href="https://leetcode.cn/problems/sliding-window-median/solutions/588643/hua-dong-chuang-kou-zhong-wei-shu-by-lee-7ai6/?envType=problem-list-v2&envId=hash-table">跳转<a/>
 *  @author: BruceBoron
 *  @date: 2025/7/8
 * </pre>
 */
public class MedianSlidingWindow {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Param {
        int[] nums;
        int k;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Result {
        double[] results;
    }

    public static Pair<Param, Result> generate0() {
        Param param = Param.builder().nums(new int[] {1,3,-1,-3,5,3,6,7}).k(3).build();
        Result result = Result.builder().results(new double[] {1,-1,-1,3,5,6}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate1() {
        Param param = Param.builder().nums(new int[] {1,4,2,3}).k(4).build();
        Result result = Result.builder().results(new double[] {2.5}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate2() {
        Param param = Param.builder().nums(new int[] {2147483647,2147483647}).k(2).build();
        Result result = Result.builder().results(new double[] {2147483647}).build();
        return Pair.of(param, result);
    }

    public static Pair<Param, Result> generate3() {
        Param param = Param.builder().nums(new int[] {5,2,2,7,3,7,9,0,2,3}).k(9).build();
        Result result = Result.builder().results(new double[] {3.0,3.0}).build();
        return Pair.of(param, result);
    }


    public static boolean test(Pair<Param, Result> testParam) {
        Param param = testParam.getKey();
        Result result = testParam.getValue();
        double[] actualResults = MedianSlidingWindowSolution.medianSlidingWindow(param.getNums(), param.getK());
        double[] expectResults = result.getResults();
        boolean compareResult = Arrays.equals(actualResults, expectResults);
        System.out.println("actualResults vs expectResults");
//        System.out.printf("%s vs %s\n", actualResults1, expectResults1);
        System.out.println("compareResult: " + compareResult);
        return compareResult;
    }

    public static void main(String[] args) {
        test(generate0());
        test(generate1());
        test(generate2());
        test(generate3());

//        int a = 3;
//        System.out.println(a);
//        System.out.println((double) a);
    }
}

/**
 * 双堆
 */
class MedianSlidingWindowSolution {


    public static double[] medianSlidingWindow(int[] nums, int k) {
        WindowElement we = new WindowElement(k);
        for (int i = 0; i < k; i++) {
            we.add(nums[i]);
        }
        double[] results = new double[nums.length - k + 1];
        results[0] = we.getMedian();
        for (int i = k; i < nums.length; i++) {
            we.add(nums[i]);
            we.recordThenRemove(nums[i - k]);
            results[i - k + 1] = we.getMedian();
        }
        return results;
    }

    static class WindowElement {
        // 左队列降序，将大的放前面
        private PriorityQueue<Integer> leftQueue;
        // 右队列升序，将小的放前面（默认也是升序）
        private PriorityQueue<Integer> rightQueue;
        // 记录延迟删除的元素
        private Map<Integer, Integer> delayRemoveMap;
        // 记录容量
        private int k;
        // 延迟删除，所以需要记录左右队列的真实大小
        private int leftSize;
        private int rightSize;

        public WindowElement(int k) {
            leftQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });
            rightQueue = new PriorityQueue<>();
            delayRemoveMap = new HashMap<>();
            this.k = k;
            leftSize = 0;
            rightSize = 0;
        }

        public double getMedian() {
            // 如果是奇数，直接获取
            return (k & 1) == 1 ? leftQueue.peek() : ((double) leftQueue.peek() + rightQueue.peek()) / 2;
        }

        public void add(int val) {
            // 左队列为空，或者元素小于左队列的队首元素，就将元素添加进左队列，否则进入右队列
            if (leftQueue.isEmpty() || val <= leftQueue.peek()) {
                leftQueue.offer(val);
                leftSize++;
            } else {
                rightQueue.add(val);
                rightSize++;
            }
            // 维持左右队列的平衡，
            makeBalance();
        }

        public void recordThenRemove(int val) {
            //
            delayRemoveMap.put(val, delayRemoveMap.getOrDefault(val, 0) + 1);
            if (val <= leftQueue.peek()) {
                leftSize--;
                if (val == leftQueue.peek()) {
                    remove(leftQueue);
                }
            } else {
                rightSize--;
                if (val == rightQueue.peek()) {
                    remove(rightQueue);
                }
            }
            makeBalance();
        }

        private void remove(PriorityQueue<Integer> queue) {
            // 移除元素后同时检查队首元素是否需要删除
            // 遍历队列
            while (!queue.isEmpty()) {
                // 获取队首元素
                int val = queue.peek();
                // 如果队首元素在待删除集合中
                if (delayRemoveMap.containsKey(val)) {
                    // 待删除数量-1
                    delayRemoveMap.put(val, delayRemoveMap.get(val) - 1);
                    // 如果数量减为0，则移除该key
                    if (delayRemoveMap.get(val) == 0) {
                        delayRemoveMap.remove(val);
                    }
                    // 将队首元素弹出
                    queue.poll();
                } else {
                    break;
                }
            }
        }

        /**
         * 保证左右队列的平衡，左右队列的长度要么相等，要么左边比右边多一
         */
        public void makeBalance() {
            if (leftSize >= rightSize + 2) {
                rightQueue.offer(leftQueue.poll());
                --leftSize;
                ++rightSize;
                remove(leftQueue);
            } else if (leftSize < rightSize) {
                leftQueue.offer(rightQueue.poll());
                ++leftSize;
                --rightSize;
                remove(rightQueue);
            }
        }

    }
}

/**
 * 链表，行不通
 */
class MedianSlidingWindowSolution1 {

    static class Node {
        long val;
        Node pre;
        Node next;

        public Node() {

        }

        public Node(long val) {
            this.val = val;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("val=").append(val);
            sb.append('}');
            return sb.toString();
        }
    }

    static class WindowNodes {

        int capacity;
        int size = 0;
        Node midNode;
        private final Node headSentry = new Node(Long.MIN_VALUE);
        private final Node tailSentry = new Node(Long.MAX_VALUE);
        private final Map<Long, Set<Node>> db = new HashMap<>();

        public WindowNodes(int capacity) {
            this.capacity = capacity;
            headSentry.next = tailSentry;
            tailSentry.pre = headSentry;
        }

        public void add(int val) {
            Node node = new Node(val);
            Node tmpNode = headSentry;
            Set<Node> nodes = db.computeIfAbsent(node.val, k -> new HashSet<>());
            nodes.add(node);
            // 如果值相同，这插入在后边
            while (node.val >= tmpNode.val) {
                if (node.val < tmpNode.next.val) {
                    Node tmpNextNode = tmpNode.next;
                    // tmpNode <--> node
                    tmpNode.next = node;
                    node.pre = tmpNode;
                    // node <--> tmpNextNode
                    node.next = tmpNextNode;
                    tmpNextNode.pre = node;
                    break;
                } else {
                    tmpNode = tmpNode.next;
                }
            }
            if (midNode == null) {
                midNode = node;
            } else {
                reIdxMid(val, size, false, null);
            }
            size++;
        }

        public void remove(int val) {
            Long longVal = (long) val;
            Set<Node> nodes = db.get(longVal);
            Node node = nodes.iterator().next();
            // 优先删除后面的元素
            while (node.val == node.next.val) {
                node = node.next;
            }
            nodes.remove(node);
            if (nodes.isEmpty()) {
                db.remove(longVal);
            }
            Node preNode = node.pre;
            Node nextNode = node.next;

            preNode.next = nextNode;
            nextNode.pre = preNode;

            reIdxMid(val, size, true, node);
            size--;
        }

        public void reIdxMid(int val, int preSize, boolean isRemove, Node node) {
            long val1, val2;
            if (isRemove) {
                val1 = val;
                val2 = midNode.val;
            } else {
                val1 = midNode.val;
                val2 = val;
            }
            if (val1 < val2) {
                if (preSize % 2 == 1) {
                    midNode = midNode.next;
                }
            } else if (val1 > val2) {
                if (preSize % 2 == 0) {
                    midNode = midNode.pre;
                }
            } else {
                if (isRemove) {
                    if (preSize % 2 == 0) {
                        midNode = midNode.pre;
                    } else if (Objects.equals(midNode, node)) {
                        midNode = midNode.next;
                    }
                } else {
                    if (preSize % 2 == 1) {
                        midNode = midNode.next;
                    }
                }
            }
        }

    }

    public static double[] medianSlidingWindow(int[] nums, int k) {
        // 获取窗口元素
        // 针对窗口元素进行排序
        // 将元素加入链表，从链表中获取中位数
        // 如果k是奇数，则获取nums[k/2]作为中位数
        // 如果k是偶数，则获取nums[k/2-1]和nums[k/2]的平均值
        // 移除移除left对应的元素，加入right对应的元素
        // 同时计算中位数，如此循环下去
        int n = nums.length;
        WindowNodes windowNodes = new WindowNodes(k);
        for (int i = 0; i < k; i++) {
            windowNodes.add(nums[i]);
        }
        int left = 0, right = k;
        boolean isOdd = k % 2 == 1;
        double[] results = new double[n - k + 1];
        int resIdx = 0;
        // 获取中位数
        if (isOdd) {
            results[resIdx] = windowNodes.midNode.val;
        } else {
            results[resIdx] = (double) (windowNodes.midNode.val + windowNodes.midNode.pre.val) / 2;
        }
        resIdx++;
        while (right < nums.length) {
            // 移除左端点
            windowNodes.remove(nums[left]);
            // 加入右端点
            windowNodes.add(nums[right]);
            // 获取中位数
            if (isOdd) {
                results[resIdx] = windowNodes.midNode.val;
            } else {
                results[resIdx] = (double) (windowNodes.midNode.val + windowNodes.midNode.pre.val) / 2;
            }
            // 左端点、右端点、结果集索引 分别+1
            left++;
            right++;
            resIdx++;
        }
        return results;
    }
}
