/*
 * Solution for Problem B - Beehive Epidemic
 * 
 * 1. Move indices onto board without negative indices.
 * 2. Run a BFS from all bacteria in order to calculate the time each cell is infected
 * 3. Run a BFS from each bee to identify which safe zones it can reach
 * 4. Calculate the size of a maximal bipartite matching between bees and safe zones.
 * 
 * O(N^3 + N*boardsize) where boardsize can be up to 600*600
 * 
 * Sorry about calling the bacteria viruses in this code :P
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;

public class beehive_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception { return Integer.parseInt(TOKEN()); }

	public static void main(String[] args) throws Exception {
		int cases = INT();
		while (cases-- > 0) {
			
			int N = INT(), S = INT(), B = INT();

			// Read locations and determine size of beehive:
			int minx = Integer.MAX_VALUE, maxx = -Integer.MIN_VALUE;
			int miny = Integer.MAX_VALUE, maxy = -Integer.MIN_VALUE;

			int[] beex = new int[N], beey = new int[N];
			for (int i = 0; i < N; i++) {
				beex[i] = INT();
				beey[i] = INT();
				minx = Math.min(minx, beex[i]);	miny = Math.min(miny, beey[i]);
				maxx = Math.max(maxx, beex[i]);	maxy = Math.max(maxy, beey[i]);
			}

			int[] safex = new int[S], safey = new int[S];
			for (int i = 0; i < S; i++) {
				safex[i] = INT();
				safey[i] = INT();
				minx = Math.min(minx, safex[i]); miny = Math.min(miny, safey[i]);
				maxx = Math.max(maxx, safex[i]); maxy = Math.max(maxy, safey[i]);
			}

			int[] virx = new int[B], viry = new int[B];
			for (int i = 0; i < B; i++) {
				virx[i] = INT();
				viry[i] = INT();
				minx = Math.min(minx, virx[i]); miny = Math.min(miny, viry[i]);
				maxx = Math.max(maxx, virx[i]);	maxy = Math.max(maxy, viry[i]);
			}
			
			// Move onto a beehive with indices 0 .. MAX
			int buffer = maxx+maxy-minx-miny+4;
			for (int i = 0; i < N; i++) { beex[i] += buffer-minx; beey[i] += buffer-miny; }
			for (int i = 0; i < S; i++) { safex[i] += buffer-minx; safey[i] += buffer-miny; }
			for (int i = 0; i < B; i++) { virx[i] += buffer-minx; viry[i] += buffer-miny; }
			
			// Solve problem :)
			System.out.println(new beehive_er().go(beex,beey,safex,safey,virx,viry,2*buffer+maxx-minx,2*buffer+maxy-miny));
		}
	}

	static final int[] dx = new int[] {-1,0,1,0,-1,1};
	static final int[] dy = new int[] {0,1,0,-1,1,-1};
	
	public int go(int[] bx, int[] by, int[] sx, int[] sy, int[] vx, int[] vy, int sizex, int sizey) {
		
		int[][] infecttimes = getInfectionTimes(vx, vy, sizex, sizey);
//		updateInfectionTimes(infecttimes, sx, sy);
		
		int[][] safezones = new int[sizex][sizey];
		int maxsteps = 0; // Time the last safe zone is infected!
		for(int i = 0;i<sx.length;i++) {
			safezones[sx[i]][sy[i]] = i+1;
			maxsteps = Math.max(maxsteps,infecttimes[sx[i]][sy[i]]);
		}
		maxsteps++;
		// maxsteps = Integer.MAX_VALUE;
		boolean[][] bipartite = new boolean[bx.length][];
		for(int i = 0;i<bx.length;i++)bipartite[i] = flee(bx[i],by[i],infecttimes,safezones,sx.length, maxsteps);
		
		return matchBees(bipartite);
		
		
	}
	
	// Calculates at what time slot each cell becomes infected
	public int[][] getInfectionTimes(int[] vx, int[] vy, int sx, int sy) {

		int[][] infecttimes = new int[sx][sy];
		for(int[] times : infecttimes)Arrays.fill(times,Integer.MAX_VALUE / 3);
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		for(int i = 0;i<vx.length;i++) {
			infecttimes[vx[i]][vy[i]] = 0;
			queue.add(vx[i]);
			queue.add(vy[i]);
		}
		
		while(!queue.isEmpty()) {
			int x = queue.poll(), y = queue.poll();
			for(int m = 0;m<dx.length;m++) {
				int xx = x+dx[m], yy = y+dy[m];
				if(xx<0 || xx>=sx || yy<0 || yy>=sy)continue;
				if(infecttimes[xx][yy]<Integer.MAX_VALUE/3)continue;
				infecttimes[xx][yy] = infecttimes[x][y]+2;
				queue.add(xx); queue.add(yy);
			}
		}
		
		return infecttimes;
		
	}
	
	// Find which safe zones the bee can reach without risking infection.
	public boolean[] flee(int bx, int by, int[][] infection, int[][] safe, int S, int maxsteps) {
		boolean[] reaches = new boolean[S];
		if(infection[bx][by]<=0)return reaches;

		LinkedList<Integer> queue = new LinkedList<Integer>();
		int[][] vis = new int[infection.length][infection[0].length];
		for(int[] vv : vis)Arrays.fill(vv,Integer.MAX_VALUE/3);
		
		queue.add(bx); queue.add(by);
		vis[bx][by] = 0;
		
		while(!queue.isEmpty()) {
			int x = queue.poll();int y = queue.poll();
			int t = vis[x][y];
			
			if(t>maxsteps)break; // We can no longer reach a safe zone :(
			if(safe[x][y]>0)reaches[safe[x][y]-1] = true;
			
			if(t>=infection[x][y])continue;
			
			for(int m = 0;m<dx.length;m++) {
				int xx = x+dx[m], yy = y+dy[m];
				if(xx<0 || xx>=vis.length || yy<0 || yy>=vis[xx].length)continue;
				if(vis[xx][yy]<=t+1 || infection[xx][yy]<t+1)continue;
				vis[xx][yy] = t+1;
				queue.add(xx); queue.add(yy);
			}
		}

		return reaches;
		
	}
	
	
	// Match bees to their corresponding safe zones
	public static int matchBees(boolean[][] possmatch) {
		int N = possmatch.length, M = possmatch[0].length, cnt = 0;
		int[] mat = new int[M], par = new int[N+M];
		Arrays.fill(mat, -1);
		for(int s = 0;s<N;s++) {
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(s);
			Arrays.fill(par, -1);
			out:while(!queue.isEmpty()) {
				int nxt = queue.poll();
				if(nxt<N) {
					for(int t = 0;t<M;t++) {
						if(possmatch[nxt][t] && par[N+t]==-1) {
							par[N+t] = nxt;
							if (mat[t]==-1) {
								cnt++;
								nxt = N+t;
								while(nxt!=s) {
									if(nxt>=N)mat[nxt-N] = par[nxt];
									nxt = par[nxt];
								}
								break out;
							}
							queue.add(N+t);
						}
					}
				} else {
					par[mat[nxt-N]] = nxt;
					queue.add(mat[nxt-N]);
				}
			}
		}
		return cnt;
	}

}
