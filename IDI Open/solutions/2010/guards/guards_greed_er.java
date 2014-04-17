/*
 * Solution for Problem A - Guarding the Border
 * 
 * Greedy Solution
 * Always add the next tower to the segment with the highest distance!
 * Using a heap (PriorityQueue) to extract this segment in log(N) time.
 * 
 * O(M*log(N))
 *  
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;

public class guards_greed_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String LINE() throws Exception { return stdin.readLine(); }
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}

	public static final double EPSILON = 1E-14;
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			int N = INT(), M = INT(), L = INT();
			double[] tows = new double[N];
			for(int i = 0;i<N;i++)tows[i] = DOUBLE();
			new guards_greed_er().go(tows, L, M);
		}
	}
	
	public void go(double[] towers, double length, int maxnew) {
		
		if(towers.length==0) {
			System.out.println((length/maxnew));
		} else {
			Arrays.sort(towers);
			PriorityQueue<Segment> segs = new PriorityQueue<Segment>();
			segs.add(new Segment(towers[0]-towers[towers.length-1]+length));
			for(int i = 1;i<towers.length;i++)segs.add(new Segment(towers[i]-towers[i-1]));
			
			double best = segs.peek().size();
			for(int splits = 1;splits<=maxnew;splits++) {
				Segment longest = segs.poll();
				longest.splits++;
				segs.add(longest);
				double nsize = segs.peek().size();
				if(nsize<best-EPSILON) {
					best = nsize;
				}
			}
			System.out.println(best);
		}
	}
	
	private static class Segment implements Comparable<Segment> {
		double origsize;
		int splits = 0;
		Segment(double size) {this.origsize = size;}
		public double size() {return origsize/(splits+1); }
		public int compareTo(Segment seg) {
			if(this.size()>seg.size())return -1;
			if(this.size()<seg.size())return 1;
			return 0;
		}
	}

}
