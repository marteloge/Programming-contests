/*
 * Solution for Problem E - Ambulance Antics
 * 
 * Uses DP to find the shortest path through the DAG 
 * where nodes are all combinations of patients delivered.
 * 
 * Precomputes distances using Floyd-Warshall
 * 
 * O(N^2 * 2^N)
 * 
 * Eirik Reksten
 */

import java.io.*;
import java.text.*;
import java.util.*;

public class ambulance_er {

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
			int inters = INT()+1, streets = INT();
			int[][] dists = new int[inters][inters];
			for(int i = 0;i<inters;i++) {
				Arrays.fill(dists[i],Integer.MAX_VALUE/3);
				dists[i][i] = 0;
			}
			for(int i = 0;i<streets;i++) {
				int a = INT(), b = INT(), c = INT();
				dists[a][b] = Math.min(dists[a][b],c);
				dists[b][a] = Math.min(dists[b][a],c);
			}
			new ambulance_er().go(dists);
		}
	}
	
	public void go(int[][] distsTable) {
		N = distsTable.length;
		this.dists = distsTable;
		// Floyd-Warshall
		for(int k = 0;k<N;k++)
			for(int i = 0;i<N;i++)
				for(int j = 0;j<N;j++)
					dists[i][j] = Math.min(dists[i][j], dists[i][k]+dists[k][j]);
		
		memo = new int[1<<(N-1)];
		Arrays.fill(memo,-1);
		
		System.out.println(solve(0));
		
	}
	
	int N;
	int[] memo;
	int[][] dists;
	
	public int solve(int mask) {
		if(mask==(1<<(N-1))-1)return 0;
		if(memo[mask]>=0)return memo[mask];
		
		int first = 0;
		while((mask&(1<<first))!=0)first++;
		int amove = 1<<first;
		int best = 2*dists[N-1][first]+solve(mask|amove);
		for(int second = first+1;second<N-1;second++) {
			if((mask&(1<<second))!=0)continue;
			int bmove = amove|(1<<second);
			best = Math.min(best,dists[N-1][first]+dists[first][second]+dists[second][N-1]+solve(mask|bmove));
			for(int third = second+1;third<N-1;third++) {
				if((mask&(1<<third))!=0)continue;
				int cst = dists[N-1][first]+dists[first][second]+dists[second][third]+dists[third][N-1];
				cst = Math.min(cst,dists[N-1][first]+dists[first][third]+dists[third][second]+dists[second][N-1]);
				cst = Math.min(cst,dists[N-1][second]+dists[second][first]+dists[first][third]+dists[third][N-1]);
				best = Math.min(best,cst+solve(mask|bmove|(1<<third)));
			}
			
		}
		
		memo[mask] = best;
		return best;
	}

}
