/*
 * Author: Eirik Reksten
 * 
 * Solution for Box Betting.
 * 
 * The answer is found by taking the average of all answers for the different starting points.
 * 
 * Methods for both the O(N) and the O(N*log(N)) solution. Memory usage in both are O(N).
 * 
 */

import java.util.*;
import java.io.*;

public class betting_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	

	public static void main(String[] args) throws Exception {
		
		int cases = INT();
		
		while(cases-->0) {
			
			int b = INT();
			String str = TOKEN();
			
//			Used in O(N) solution
			int[] boxes = new int[b];
			for(int i = 0;i<b;i++)boxes[i] = str.charAt(i)-'A';
			int lower = INT(), upper = INT();
			count(boxes, lower, upper);
			
//			Used in O(N*log(N)) solution
//			int[] box2 = new int[b+1];
//			for(int i = 1;i<=b;i++)box2[i] = str.charAt(i-1)-'A';
//			int lower = INT(), upper = INT();
//			countBS(box2,lower,upper);
			
		}
		
	}
	
	// Solution in O(N) (amortized)
	public static void count(int[] list, int lower, int upper) {
		double A = 0, B = 0, C = 0;
		
		// Variables keeping record of where I am in the linear search
		int llim = -1, ulim = -1, lsum = 0, usum = 0;
		
		// Linear Search (amortized O(N) running time)
		for(int start = 0;start<list.length;lsum -= list[start],usum -= usum>0?list[start]:0,start++) {
			
			if(ulim<start-1) { ulim = start-1; usum = 0; }
			if(llim<start) { llim = start; lsum = list[start]; }
			while(llim<list.length-1 && lsum<lower)lsum += list[++llim];
			while(ulim<list.length-1 && usum+list[ulim+1]<=upper)usum += list[++ulim];
			if(lsum<lower)llim = list.length;
			
			double nn = list.length-start;
			A += (ulim-llim+1)/nn;
			B += (llim-start)/nn;
			C += (list.length-1-ulim)/nn;
		}
		
		System.out.println((A/list.length)+" "+(B/list.length)+" "+(C/list.length));
	}
	
	// O(N*log(N)) solution using binary search:
	public static void countBS(int[] cumu, int lower, int upper) {
		int N = cumu.length-1;
		for(int i = 1;i<=N;i++)cumu[i] += cumu[i-1];
		double A = 0.0, B = 0.0, C = 0.0;
		for(int start = 0;start<N;start++) {
			
			// First where the sum is above or equal to lower
			int lmin = start, lmax = N-1;
			if(cumu[N]-cumu[start]<lower)lmin = lmax = N;
			while(lmin<lmax) {
				int lmid = (lmin+lmax)/2;
				if(cumu[lmid+1]-cumu[start]>=lower)lmax = lmid;
				else lmin = lmid+1;
			}
			
			// First where the sum is above upper
			int umin = start, umax = N-1;
			if(cumu[N]-cumu[start]<=upper)umin = umax = N;
			while(umin<umax) {
				int umid = (umin+umax)/2;
				if(cumu[umid+1]-cumu[start]>upper)umax = umid;
				else umin = umid+1;
			}
			
			double nn = N-start;
			A += (umin-lmin)/nn;
			B += (lmin-start)/nn;
			C += (N-umin)/nn;
			
		}
		System.out.println((A/N)+" "+(B/N)+" "+(C/N));
	}
	
}
