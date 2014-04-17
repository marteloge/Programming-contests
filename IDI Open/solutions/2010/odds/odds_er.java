/*
 * Solution for Problem J - Combat Odds
 * 
 * Dynamic Programming Solution
 * Calculating the probability of observing an L long loss sequence in a
 * simulation of N combats with k losses prepended.
 * 
 * P(N,k)
 * P(i,j) = winprob*P(i-1,0)+loseprob*P(i-1,j+1)
 * P(i,L) = 1.0
 * P(i,j) = 0.0 if i+j<L
 * 
 * The answer is found by calculating P(N,0)
 * 
 * O(M*L)
 * 
 * Take a look at odds_tg_fast.cpp for an efficient solution to this
 * problem (obviously not necessary with these constraints).
 *  
 * Eirik Reksten
 */

import java.io.*;
import java.text.*;
import java.util.*;

public class odds_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String LINE() throws Exception { return stdin.readLine(); }
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static long LONG() throws Exception {return Long.parseLong(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}

	
	static DecimalFormat DF = new DecimalFormat("0.000",new DecimalFormatSymbols(Locale.ENGLISH));
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			int N = INT(), L = INT();
			double p = DOUBLE();
			new odds_er().go(N,L,p);
		}
	}
	
	public void go(int N, int L, double p) {
		
		memo = new double[N+1][L+1];
		for(double[] me : memo)Arrays.fill(me, -1);
		System.out.println(calculate(N, 0, L, 1.0-p));
		
	}
	
	double[][] memo;
	public double calculate(int left, int prev, int minstreak, double loseprob) {
		
		if(prev>=minstreak)return 1.0;
		if(left==0)return 0.0;
		if(left+prev<minstreak)return 0.0;
		
		if(memo[left][prev]>-0.5)return memo[left][prev];
		
		double pr = 0.0;
		// Winning the next battle!
		pr += loseprob*calculate(left-1,prev+1,minstreak,loseprob);
		
		//Losing next battle!
		pr += (1.0-loseprob)*calculate(left-1,0,minstreak,loseprob);
		
		memo[left][prev] = pr;
		return pr;
		
	}

}
