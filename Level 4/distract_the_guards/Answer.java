/*
Distract the Guards
===================
The time for the mass escape has come, and you need to distract the guards so that the bunny prisoners can make it out! Unfortunately for you, they're watching the bunnies closely. Fortunately, this means they haven't realized yet that the space station is about to explode due to the destruction of the LAMBCHOP doomsday device. Also fortunately, all that time you spent working as first a minion and then a henchman means that you know the guards are fond of bananas. And gambling. And thumb wrestling.
The guards, being bored, readily accept your suggestion to play the Banana Games.
You will set up simultaneous thumb wrestling matches. In each match, two guards will pair off to thumb wrestle. The guard with fewer bananas will bet all their bananas, and the other guard will match the bet. The winner will receive all of the bet bananas. You don't pair off guards with the same number of bananas (you will see why, shortly). You know enough guard psychology to know that the one who has more bananas always gets over-confident and loses. Once a match begins, the pair of guards will continue to thumb wrestle and exchange bananas, until both of them have the same number of bananas. Once that happens, both of them will lose interest and go back to guarding the prisoners, and you don't want THAT to happen!
For example, if the two guards that were paired started with 3 and 5 bananas, after the first round of thumb wrestling they will have 6 and 2 (the one with 3 bananas wins and gets 3 bananas from the loser). After the second round, they will have 4 and 4 (the one with 6 bananas loses 2 bananas). At that point they stop and get back to guarding.
How is all this useful to distract the guards? Notice that if the guards had started with 1 and 4 bananas, then they keep thumb wrestling! 1, 4 -> 2, 3 -> 4, 1 -> 3, 2 -> 1, 4 and so on.
Now your plan is clear. You must pair up the guards in such a way that the maximum number of guards go into an infinite thumb wrestling loop!
Write a function answer(banana_list) which, given a list of positive integers depicting the amount of bananas the each guard starts with, returns the fewest possible number of guards that will be left to watch the prisoners. Element i of the list will be the number of bananas that guard i (counting from 0) starts with.
The number of guards will be at least 1 and not more than 100, and the number of bananas each guard starts with will be a positive integer no more than 1073741823 (i.e. 2^30 -1). Some of them stockpile a LOT of bananas.
*/
import java.util.*;

public class Answer {
    public static boolean checkPow2(int num) {
        /*Checks if there is only 1 set bit
         *Ex: 1000 & 0111 = 0
         */
        return (num & (num - 1)) == 0;
    }
    public static boolean isInfinite(int a, int b) {
        return !checkPow2((a + b) / gcd(Math.min(a, b), Math.max(a, b)));
    }
    public static int gcd(int a, int b) {
        int tmp;
        while(b % a != 0) {
            tmp = b;
            b = a;
            a = tmp % a;
        }
        return a;
    }
    public static int answer(int[] banana_list) {
        Vector<Vector<Integer> > ar = new Vector();
        boolean matched[] = new boolean[banana_list.length];
        int ans = 0, min_size;
        for(int i = 0; i < banana_list.length; ++i) {
            ar.add(new Vector<Integer>());
            matched[i] = false;
            /* Is true when ith guard is already paired */
        }
        for(int i = 0; i < banana_list.length; ++i) {
            for(int j = i + 1; j < banana_list.length; ++j) {
                if(isInfinite(banana_list[i], banana_list[j])) {
                    ar.get(i).add(j);
                    ar.get(j).add(i);
                }
            }
        }
        for(int i = 0; i < banana_list.length; ++i) {
            if(matched[i]) // Already paired
                continue;
            if(ar.get(i).size() == 0) {
                ++ans;
                continue;
            } min_size = i;
            for(int j = 0; j < ar.get(i).size(); j++) {
                if(!matched[ar.get(i).get(j)] && (min_size == i ||
                        ar.get(ar.get(i).get(j)).size() <  ar.get(min_size).size()))
                    min_size = ar.get(i).get(j);
            }
            if(min_size != i) {
                matched[min_size] = true;
                matched[i] = true;
                /* Remove node i and min_size from graph */
                for(int node = 0; node < ar.size(); node++) {
                    for(int j = 0; j < ar.get(node).size(); j++) {
                        if(ar.get(node).get(j) == i || ar.get(node).get(j) == min_size) {
                            ar.get(node).remove(j);
                        }
                    }
                }
            }
            else // No guard left to pair with i
                ++ans;
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int ar[] = new int[n];
        for(int i = 0; i < n; i++)
            ar[i] = s.nextInt();
        System.out.print(answer(ar));

    }
}