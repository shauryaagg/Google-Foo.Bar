import java.util.*;

public class Answer {
    public static int nCr(int n, int r) {
        if(r == 0)
            return 1;
        if(r < 0)
            return 0;
        int fact = 1;
        for(int i = r + 1; i <= n; i++) {
            fact = fact * i / (i - r);
        } return fact;
    }
    public static int[][] answer(int num_buns, int num_required) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < num_buns; i++)
            ans.add(new ArrayList<Integer>());
        int bitset = 0;
        for(int i = 0; i <= num_buns - num_required; i++)
            bitset |= 1<<i;
        for(int i = nCr(num_buns, num_required) * num_required / (num_buns - num_required + 1) - 1; i >= 0 ; i--) {
            for (int j = 0; j < num_buns; j++) {
                if ((bitset & (1 << j)) != 0)
                    ans.get(j).add(i);
            }
            /* Generates the next lexicographic permuation with the same number of bits set.
             * Ex: 111 -> 1011 -> 1101 -> 1110, and so on.
             */
            int bit_temp = (bitset | (bitset - 1)) + 1;
            bitset = bit_temp | ((((bit_temp & -bit_temp) / (bitset & -bitset)) >> 1) - 1);
        }
        int req_ans[][] = new int[ans.size()][ans.get(0).size()];
        for(int i = 0; i < req_ans.length; i++)
            for(int j = 0; j < req_ans[i].length; j++)
                req_ans[i][j] = ans.get(req_ans.length - i - 1).get(req_ans[i].length - j - 1);
        return req_ans;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int k = s.nextInt();
        int ans[][] = answer(n,k);
        for(int i = 0; i < ans.length; i++) {
            for(int j = 0; j < ans[i].length; j++) {
                System.out.print(ans[i][j] + " ");
            } System.out.println();
        }
    }
}