/*
 * Author: Eirik Reksten
 * 
 * Solution for Train Tickets from IDI Open 2009
 * 
 * Min-Cost Max-Flow in order to enforce the optimal configuration of tickets sold.
 * See the solution description document for a description of the graph.
 * 
 * This solution runs a lot faster if you implement it with a sparse matrix instead of a full
 * neighbour matrix, but that was not necessary in order to pass the problem.
 * 
 */

import java.io.*;
import java.util.*;

public class tickets_er {

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
			
			int N = INT();
			int capacity = INT();
			
			int[][] prices = new int[N][N];
			for(int i = 0;i<N;i++)for(int j = i+1;j<N;j++)prices[i][j] = INT();
			
			int[][] demand = new int[N][N];
			for(int i = 0;i<N;i++)for(int j = i+1;j<N;j++)demand[i][j] = INT();
			
			int[][] government = new int[N][N];
			for(int i = 0;i<N;i++)for(int j = i+1;j<N;j++)government[i][j] = INT();
			
			new tickets_er().go(capacity, demand, prices, government);
			
		}
		
	}
	
	public void go(int P, int[][] dem, int[][] cst, int[][] gov) throws Exception {
		
		N = dem.length;
		
		long[][] capacities = new long[2+N+N*(N-1)/2][2+N+N*(N-1)/2];
		long[][] costs = new long[2+N+N*(N-1)/2][2+N+N*(N-1)/2];
		
//		 Source edges (demand from source to ticket):
		for(int i = 0;i<N;i++)
			for(int j = i+1;j<N;j++)
				capacities[source()][ticket(i,j)] = dem[i][j];
		
//		Sold tickets
		for(int i = 0;i<N;i++)for(int j = i+1;j<N;j++) {
			capacities[ticket(i,j)][station(i)] = dem[i][j];
			costs[ticket(i,j)][station(i)] = -cst[i][j];
			costs[station(i)][ticket(i,j)] = cst[i][j];
		}
		
//		People travelling by other means
		for(int i = 0;i<N;i++)
			for(int j = i+1;j<N;j++)
				capacities[ticket(i,j)][station(j)] = dem[i][j];
		
//		Travelling on the train
		for(int i = 0;i<N-1;i++)
			capacities[station(i)][station(i+1)] = P;
		
//		Making place for government officials!
		for(int i = 0;i<N;i++)
			for(int j = i+1;j<N;j++)
				for(int k = i;k<j;k++)
					capacities[station(k)][station(k+1)] -= gov[i][j];
		
//		Sink edges (total demand from destinations to sink):
		for(int i = 0;i<N;i++) {
			int totaldemand = 0;
			for(int j = 0;j<i;j++)totaldemand += dem[j][i];
			capacities[station(i)][sink()] = totaldemand;
		}
		
		
		// max-cost max-flow is the negative of min-cost max-flow with negative costs. 
		long bestCost = -mcmaxflow(costs, capacities, 0, 1);
		System.out.println(bestCost);
		
		
	}
	
	int N = 0;
	
	public int source() { return 0; }
	public int sink() { return 1; }
	public int station(int location) { return 2+location; }
	public int ticket(int from, int to) { return 2 + N + N*(N-1)/2 - (N-from)*(N-from-1)/2 + to-1-from; }

	//	Finding min-cost max-flow
	public static long mcmaxflow(long[][] cst, long[][] cap, int src, int snk) {
		int N = cap.length;
		long totalCost = 0;
		
		long[] mincst = new long[N];
		int[] par = new int[N];
		long[][] flw = new long[N][N];
		
		while (true) {
			// Running bellman-ford to find the augmenting path.
			Arrays.fill(mincst, Long.MAX_VALUE / 3);
			mincst[src] = 0;
			Arrays.fill(par, -1);
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(src);
			boolean[] updated = new boolean[N];
			while(!queue.isEmpty()) {
				int at = queue.poll();
				updated[at] = false;
				for(int i = 0; i < cst.length; i++){
					if(cap[at][i]-flw[at][i] <= 0) continue;
					if(mincst[at]+cst[at][i] < mincst[i]){
						mincst[i] = mincst[at]+cst[at][i];
						par[i] = at;
						if(!updated[i]){
							queue.add(i);
							updated[i] = true;
						}
					}
				}
			}
			
			// No augmenting path? Done!
			if (mincst[snk]>=Long.MAX_VALUE / 3)break;
			
			// Finding max flow increase along augmenting path.
			long pathFlow = Long.MAX_VALUE / 3;
			int pos = snk;
			while(par[pos] != -1){
				pathFlow = Math.min(pathFlow, cap[par[pos]][pos] - flw[par[pos]][pos]);
				pos = par[pos];
			}
			
			// Updating the flow along the augmenting path.
			pos = snk;
			while(par[pos] != -1){
				flw[par[pos]][pos] += pathFlow;
				flw[pos][par[pos]] -= pathFlow;
				totalCost += pathFlow*cst[par[pos]][pos];
				pos = par[pos];
			}
		}
		return totalCost;
	}

}
