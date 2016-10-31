/**
 * Created by kecso on 10/29/16.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {
    public int startIdx = 0;
    public int endIdx = 0;
    public TreeNode left = null;
    public TreeNode right = null;
    public TreeNode(int s, int e) {
        this.startIdx = s;
        this.endIdx = e;
    }
    public void shift() {
        this.startIdx++;
        this.endIdx++;
        if (left != null) {
            left.shift();
        }
        if (right != null) {
            right.shift();
        }
    }
}

// Am I need this?
// https://en.wikipedia.org/wiki/Longest_increasing_subsequence

public class Solution {
    public static HashSet<String> inTree = new HashSet<String>();

    protected static int subVote(int[] votes, int start, int end) {
        if (end - start <= 1) {
            return 0;
        }
        byte nonDecreasing = 1;
        byte nonIncreaseing = 1;
        for (int idx = start + 1; idx < end; idx++) {
//            if (nonDecreasing && votes[idx] <= votes[idx - 1]) {
            if (nonDecreasing == 1 && votes[idx] < votes[idx - 1]) {
                nonDecreasing--;
            } else if (nonIncreaseing == 1 && votes[idx] > votes[idx - 1]) {
                nonIncreaseing--;
            }
            if ((nonDecreasing + nonIncreaseing) == 0) {
                return 0;
            }
        }
        return nonDecreasing - nonIncreaseing;
    }

    protected static void printSubArray(int start, int end, int[] allVotesArray) {
//        System.out.print("processing subarray: ");
        for (int i = start; i < end; i++) {
            System.out.print(allVotesArray[i] + " ");
        }
        System.out.println();
    }

    protected static int getVoteForWindow(TreeNode root, int[] allVotesArray) {
        int vote = 0;
        // level order traversal
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<TreeNode>();
            queue.add(root);
            while(!queue.isEmpty()) {
                TreeNode tempNode = queue.poll();
                int sv = subVote(allVotesArray, tempNode.startIdx, tempNode.endIdx);
//                printSubArray(tempNode.startIdx, tempNode.endIdx, allVotesArray);
//                System.out.println("vote: " + sv);
                int w = tempNode.endIdx - tempNode.startIdx;
                int subRangeCnt = w * (w - 1) / 2;
                if (sv == 1 && w > 2) {
                    return subRangeCnt;
                } else {
                    vote += sv;
                    if(tempNode.left != null) {
                        queue.add(tempNode.left);
                    }
                    if(tempNode.right != null) {
                        queue.add(tempNode.right);
                    }
                }
            }

//            printSubArray(root.startIdx, root.endIdx, allVotesArray);
//            int w = root.endIdx - root.startIdx;
//            int subRangeCnt = w * (w - 1) / 2;
//            int sv = subVote(allVotesArray, root.startIdx, root.endIdx);
//            if (sv == 1) {
//                return subRangeCnt;
//            } else {
//                vote += sv;
//                vote += getVoteForWindow(root.left, allVotesArray);
//                vote += getVoteForWindow(root.right, allVotesArray);
//            }
//            vote += subVote(allVotesArray, root.startIdx, root.endIdx);
//            vote += getVoteForWindow(root.left, allVotesArray);
//            vote += getVoteForWindow(root.right, allVotesArray);
        }
        return vote;
    }

    protected static TreeNode initTree(int start, int end) {
        if (!inTree.contains(""+start+","+end)) {
            TreeNode root = new TreeNode(start, end);
            inTree.add(""+start+","+end);
//            System.out.println(" start: " + start + " end: " + end);
            if ((end - start) > 2) {
                int rightStart = start + 1;
                int leftEnd = end - 1;
                root.left = initTree(start, leftEnd);
                root.right = initTree(rightStart, end);
            }
            return root;
        }
        return null;
    }

    protected static void moveWindow(int allvotes, int window, int[] allVotesArray) {
        int start = 0;
        TreeNode root = initTree(0, window);
        System.out.println(getVoteForWindow(root, allVotesArray));
        while (window < allvotes) {
            root.shift();
            window++;
            System.out.println(getVoteForWindow(root, allVotesArray));
        }
    }

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nk = br.readLine().split(" ");
        int n = Integer.parseInt(nk[0]);
        int k = Integer.parseInt(nk[1]);
        int[] data = new int[n];
        String[] sdata = br.readLine().split(" ");
        int i = 0;
        for (String s: sdata) {
            data[i++] = Integer.parseInt(s);
        }
        moveWindow(n, k, data);
    }
}