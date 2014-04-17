// Solution for Train Tickets
// Author Rune Fevang

import java.io.*;
import java.util.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;
public class tickets_rune{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(String s){ return Integer.parseInt(s);}
	static StringTokenizer st;
	static void ST() throws Exception { st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int n = INT(in.readLine());
		for(int t = 0; t < n; t++) new tickets_rune().entry();
	}

	void entry() throws Exception{
		ST();
		n = INT(st.nextToken());
		int max = INT(st.nextToken());
		prices = get();
		demand = get();
		offs = modify(get());

		N = 2+n+n*(n-1)/2;
		int[][] cost = new int[N][N];
		int[][] cap = new int[N][N];
		for(int i = 0; i < n; i++){
			for(int j = i+1; j < n; j++){
				cap[0][ticket(i,j)] = demand[i][j];
				cap[ticket(i,j)][station(i)] = demand[i][j];
				cost[ticket(i,j)][station(i)] = -prices[i][j];
				cost[station(i)][ticket(i,j)] = prices[i][j];
				cap[station(j)][N-1] += demand[i][j];
				cap[ticket(i,j)][station(j)] = demand[i][j];
			}
		}
		for(int i = 0; i < n-1; i++){
			cap[station(i)][station(i+1)] = max - offs[i];
		}
		MaxFlow mf = new MaxFlow(cost, cap);
		mf.go();
		System.out.println(-mf.totcost);

	}
	int n;
	int N;

	int station(int i){
		return 1+i;
	}
	int ticket(int a, int b){
		return 1+n+n*(n-1)/2 - (n-a)*(n-a-1)/2 + b-1-a;
	}

	int[][] prices;
	int[][] demand;
	int[] offs;

	int[][] get() throws Exception{
		int[][] ret = new int[n][n];
		for(int i = 0; i < n-1; i++){
			ST();
			for(int j = 0; j < n-1-i; j++) ret[i][i+1+j] = INT(st.nextToken());
		}
		return ret;
	}

	int[] modify(int[][] in){
		int[][] ret = new int[n][n];
		int[] ats = new int[n];
		for(int i = 0; i < n; i++){
			for(int j = i+1; j < n; j++){
				for(int k = i; k < j; k++){
					ats[k] += in[i][j];
				}
			}
		}
		return ats;
	}
	class MaxFlow{
		/* Constructor for normal flow */
		MaxFlow(int[][] cost, int[][] cap){
			this.cost = cost;
			this.cap = cap;
			n = cost.length;
			f = new int[n][n];
			c = new int[n];
			d = new int[n];
		}
		int n, inf = Integer.MAX_VALUE/2;
		int[][] cost, cap, f;
		int totcost, totflow;
		void go(){
			while(findPath()) makePath();
		}
		void makePath(){

			int flow = inf;
			int pos = n-1;
			while(d[pos] != -1){
				flow = min(flow, cap[d[pos]][pos]-f[d[pos]][pos]);
				pos = d[pos];
			}
			pos = n-1;
			totflow += flow;
			while(d[pos] != -1){
				f[d[pos]][pos] += flow;
				f[pos][d[pos]] -= flow;
				totcost += flow*cost[d[pos]][pos];
				pos = d[pos];
			}
		}
		/* Improved Bellman-Ford, homemade, will go into infinite loop
		 * (instead of giving a useful error message) if a negative cycle is present */
		int[] c, d;
		boolean findPath(){
			fill(c, inf);
			fill(d, -1);
			c[0] = 0;
			boolean[] inq = new boolean[n]; // true for the nodes already enqueued
			LinkedList<Integer> que = new LinkedList<Integer>();
			que.add(0);
			while(que.size() > 0){
				int at = que.removeFirst();
				inq[at] = false;
				for(int i = 0; i < n; i++){
					if(cap[at][i]-f[at][i] <= 0)continue;
					if(c[at]+cost[at][i] < c[i]){
						c[i] = c[at]+cost[at][i];
						d[i] = at;
						if(!inq[i]){
							que.add(i);
							inq[i] = true;
						}
					}
				}
			}
			return c[n-1] != inf;
		}
	}

}



