import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SolutionOld {

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
//                break;
            }
        }
//        System.out.println(nonDecreasing - nonIncreaseing);
        return nonDecreasing - nonIncreaseing;
    }

    protected static int allSubRange(int[] votesArray, int start, int end) {
        int vote = 0;
        for (int srs = end; srs >= 2; srs--) {
            int sstart = start;
            int send = start + srs;
            while (send <= end) {
                vote += subVote(votesArray, sstart++, send++);
            }
        }
        return vote;
    }

    protected static void moveWindow(int allvotes, int window, int[] allVotesArray) {
        int start = 0;
        while (window <= allvotes) {
            System.out.println(allSubRange(allVotesArray, start++, window++));
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
