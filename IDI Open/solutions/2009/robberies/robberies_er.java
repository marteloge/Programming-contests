/*
 * Author: Eirik Reksten
 * 
 * Solution for Robberies from IDI Open 2009
 * 
 * DP to find best probability when getting i millions from the j first banks.
 * 
 * The solution has been verified as giving the same answers when adding/removing an EPSILON
 * on the compared double values.
 * 
 */

import java.io.*;
import java.util.*;

public class robberies_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String LINE() throws Exception { return stdin.readLine(); }
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			double p = DOUBLE();
			int N = INT();
			int[] millions = new int[N];
			double[] probs = new double[N];
			for(int i = 0;i<N;i++) {
				millions[i] = INT();
				probs[i] = DOUBLE();
			}
			new robberies_er().go(probs,millions,p);
		}
	}
	
	public void go(double[] probs, int[] millions, double limit) throws Exception {
		
		double[] best = new double[10001];
		Arrays.fill(best, 1.0);
		best[0] = 0.0;
		for(int i = 0;i<millions.length;i++) {
			for(int j = 10000;j>=millions[i];j--) {
				best[j] = Math.min(best[j], 1-(1-best[j-millions[i]])*(1-probs[i]));
			}
		}
		for(int i = 10000;i>=0;i--) {
			if(limit<best[i])continue;
			System.out.println(i);
			break;
		}
		
	}

}
