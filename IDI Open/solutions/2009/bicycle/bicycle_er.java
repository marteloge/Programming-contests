/*
 * Author: Eirik Reksten
 * 
 * Solution for Bicycle Puzzle from IDI Open 2009
 * 
 * Iterating through all partitions of cycle sizes. And adding up all that give a good enough score.
 * 
 * For an alternative (and probably simpler) solution, take a look at
 * Børge Nordlis solution. He used DP.
 * 
 */

import java.util.*;
import java.io.*;

public class bicycle_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0)new bicycle_er().go(INT()*INT(),INT());
	}
	
	public void go(int N, int K) {
		
		partition = new int[21];
		this.N = N;
		tobeat = K;
		
		long good = iteratePartitions(this.N, this.N);
		long total = factorial(N);
		
		long ggg = GCD(good, total);
		good /= ggg; 
		total /= ggg;
		
		if(good==0)System.out.println(0);
		else if(total==1)System.out.println(1); 
		else System.out.println(good+"/"+total);
	}
	
	public int[] partition;
	public int N, tobeat;
	
	
	// Iterating through all possible partition of the puzzle into cycle sizes.
	public long iteratePartitions(int last, int left) {
		if(left==0) {
			int score = 0;
			for(int i = 1;i<=N;i++)score += partition[i]*(i-1);
			return score<tobeat?amount(N):0;
		}
		long good = 0;
		for(int i = last;i>=1;i--) {
			if(i>left)continue;
			partition[i]++;
			good += iteratePartitions(i, left-i);
			partition[i]--;
		}
		return good;
	}
	
	// This method looked extremely ugly before I sat down and simplified the equation...
	// Counting the amount of permutations mapping to the current partition.
	public long amount(int N) {
		long res = 1;
		for(int i = 1;i<partition.length;N -= i*partition[i], i++) {
			if(partition[i]==0)continue;
			res *= factorial(N) / factorial(N-partition[i]*i) / factorial(partition[i]);
			for(int j = 0;j<partition[i];j++)res /= i;
		}
		return res;
	}
	
	
	// Help methods
	public static long[] factorial;
	public static long factorial(int N) {
		if(factorial==null) {
			factorial = new long[21];
			factorial[0] = 1;
			for(int j = 1;j<factorial.length;j++)factorial[j] = factorial[j-1]*j;
		}
		return factorial[N];
	}
	
	public static long GCD(long a, long b) {
	   if (b==0) return a;
	   return GCD(b,a%b);
	}
	
}