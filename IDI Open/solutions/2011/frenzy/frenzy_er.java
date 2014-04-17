import java.io.*;
import java.text.*;
import java.util.*;

public class frenzy_er {

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
			int H = INT(), W = INT();
			char[][] grid = new char[H][];
			for(int i = 0;i<H;i++)grid[i] = TOKEN().toCharArray();
			int sol = new frenzy_er().go(grid);
			if(sol==-1)System.out.println("impossible");
			else System.out.println(sol);
		}
	}
	
	public int go(char[][] level) throws Exception {
		int W = level.length, H = level[0].length;
		
		// 1. Add information on the grid:
		int N = 0;
		int[] ulgr = null;
		int[][] grid = new int[W][H];
		for(int i = 0;i<W;i++) {
			for(int j = 0;j<H;j++) {
				grid[i][j] = -1;
				if(level[i][j]=='U')ulgr = new int[] {i,j};
				if(level[i][j]=='X')grid[i][j] = -2;
				if(level[i][j]=='#')grid[i][j] = N++;
			}
		}
		grid[ulgr[0]][ulgr[1]] = N++;
		
		// 2. Find distances on the grid:
		dists = new int[N][];
		for(int i = 0;i<W;i++) {
			for(int j = 0;j<H;j++) {
				if(grid[i][j]>=0) {
					dists[grid[i][j]] = findDistances(grid,i,j,N);
				}
			}
		}
		
		// 3. Check for reachability:
		for(int i = 0; i<N;i++)for(int j = 0;j<N;j++)
			if(dists[i][j]>=Integer.MAX_VALUE/2)return -1;
		
		// 4. Find optimal way of travelling!
		memo = new int[N][1<<N];
		return solve(N-1,0);
		
	}
	int[][] memo;
	int[][] dists;
	public int solve(int last, int eaten) {
		eaten |= 1<<last;
		if(eaten == (1<<dists.length)-1)return 0;
		if(memo[last][eaten]>0)return memo[last][eaten]-1;
		int best = Integer.MAX_VALUE/2;
		
		for(int i = 0;i<dists.length;i++)
			if((eaten&(1<<i))==0)
				best = Math.min(best, dists[last][i] + solve(i, eaten));
		
		memo[last][eaten] = best+1;
		return best;
	}
	
	public int[] findDistances(int[][] grid, int x, int y, int N) {
		
		int[] distances = new int[N];
		Arrays.fill(distances, Integer.MAX_VALUE / 2);
		int[][] vis = new int[grid.length][grid[0].length];
		for(int i = 0;i<vis.length;i++)Arrays.fill(vis[i],-1);
		LinkedList<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] {x,y});
		vis[x][y] = 0;
		
		while(!queue.isEmpty()) {
			int[] next = queue.poll();
			int xx = next[0], yy = next[1];
			if(grid[xx][yy]>=0)distances[grid[xx][yy]] = vis[xx][yy];
			
			if(grid[xx][yy]==-2)continue;
			
			if(xx>0 && vis[xx-1][yy]<0) {
				vis[xx-1][yy] = vis[xx][yy]+1;
				queue.add(new int[] {xx-1,yy});
			}
			if(xx<grid.length-1 && vis[xx+1][yy]<0) {
				vis[xx+1][yy] = vis[xx][yy]+1;
				queue.add(new int[] {xx+1,yy});
			}
			if(yy>0 && vis[xx][yy-1]<0) {
				vis[xx][yy-1] = vis[xx][yy]+1;
				queue.add(new int[] {xx,yy-1});
			}
			if(yy<grid[0].length-1 && vis[xx][yy+1]<0) {
				vis[xx][yy+1] = vis[xx][yy]+1;
				queue.add(new int[] {xx,yy+1});
			}
			
		}
		for(int i = 0;i<N-1;i++)distances[i]++; // Time required for eating it!
		return distances;
		
	}

}
