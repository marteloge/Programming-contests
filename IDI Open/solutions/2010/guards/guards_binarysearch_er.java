/*
 * Solution for Problem A - Guarding the Border
 * 
 * Binary Search for the maximal inter-tower distance.
 * For each distance, count the number of towers needed, and compare
 * to the number of available ones.
 * 
 * O(N*log(L))
 *  
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;

public class guards_binarysearch_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String LINE() throws Exception { return stdin.readLine(); }
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}
	
	public static final double EPSILON = 1E-9;
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			int N = INT(), M = INT(), L = INT();
			double[] tows = new double[N];
			for(int i = 0;i<N;i++)tows[i] = DOUBLE();
			new guards_binarysearch_er().go(tows, L, M);
		}
	}
	
	public void go(double[] towers, double length, int maxnew) {
		
		if(towers.length==0) {
			System.out.println((length/maxnew));
		} else {
			Arrays.sort(towers);
			double[] gaps = new double[towers.length];
			gaps[0] = towers[0] + (length-towers[towers.length-1]);
			for(int i = 1;i<towers.length;i++)gaps[i] = towers[i]-towers[i-1];
			double min = 0, max = length;
			int iterations = 100;
			while(iterations-->0) {
				double mid = (min+max)/2.0;
				if(count(gaps,mid)>maxnew)min = mid;
				else max = mid;
			}
			System.out.println(max);
		}
	}
	
	public int count(double[] gaps, double maxdist) {
		int cnt = 0;
		for(double gp : gaps)cnt += Math.ceil(gp/maxdist) - 1;
		return cnt;
	}

}
