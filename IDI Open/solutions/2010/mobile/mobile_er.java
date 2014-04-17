/*
 * Solution for Problem C - Mobile Gaming
 * 
 * Solve indepentently for each dimension, calculating the time interval
 * of overlap in this dimension. The answer to the problem is the start
 * of the intersection of these intervals.
 * 
 * O(1)
 *  
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;

public class mobile_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	public static final double EPSILON = 1e-12;
	
	public static void main(String[] args) throws Exception {
		int T = INT();
		while(T-->0) {
			int W1 = INT(), H1 = INT(), XS1 = INT(), YS1 = INT(), XE1 = INT(), YE1 = INT();
			int W2 = INT(), H2 = INT(), XS2 = INT(), YS2 = INT(), XE2 = INT(), YE2 = INT();
			double[] xslot = findTimeSlot(XS1, XS1+W1, XE1-XS1, XS2, XS2+W2, XE2-XS2);
			double[] yslot = findTimeSlot(YS1, YS1+H1, YE1-YS1, YS2, YS2+H2, YE2-YS2);
			double start = Math.max(xslot[0],yslot[0]), end = Math.min(xslot[1],yslot[1]);
			if(start<0.0)start = 0.0;
			if(end>1.0)end = 1.0;
			if(start-EPSILON<end)System.out.println(start);
			else System.out.println("No Collision");
		}
	}
	
	public static double[] findTimeSlot(int A1, int B1, int DELTA1, int A2, int B2, int DELTA2) {
		if(DELTA1==DELTA2) {
			if(A1>=A2 && A1<=B2)return new double[] {0.0,1.0};
			if(B1>=A2 && B1<=B2)return new double[] {0.0,1.0};
			return new double[] {1.0,0.0};
		}
		double T1 = (A2-B1)/(double)(DELTA1-DELTA2);
		double T2 = (A1-B2)/(double)(DELTA2-DELTA1);
		return new double[] {Math.min(T1,T2), Math.max(T1,T2)};
	}
}
