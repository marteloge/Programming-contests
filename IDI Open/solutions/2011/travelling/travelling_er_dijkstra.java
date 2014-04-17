import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class travelling_er_dijkstra {
	public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public static StringTokenizer st = null;
	
	public static String LINE() throws IOException {
		return stdin.readLine();
	}
	
	public static String TOKEN() throws IOException {
		while(st==null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	
	public static int INT() throws IOException {
		return Integer.parseInt(TOKEN());
	}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			int N = INT();
			int[] order = new int[N];
			for(int i = 0;i<N;i++) {
				order[i] = INT();
			}
			int[][] dists = new int[N][N];
			for(int i = 0;i<N;i++)for(int j = 0;j<N;j++) {
				dists[i][j] = INT();
				if(dists[i][j] == -1)dists[i][j] = Integer.MAX_VALUE / 3;
			}
//			for(int k = 0;k<N;k++)for(int i = 0;i<N;i++)for(int j = 0;j<N;j++)
//				dists[i][j] =Math.min(dists[i][j], dists[i][k]+dists[k][j]);
			
			long distance = 0;
			for(int i = 0;i<N;i++)distance += dijkstra(dists,order[i])[order[(i+1)%N]];
			if(distance>=Integer.MAX_VALUE / 3)System.out.println("impossible");
			else System.out.println(distance);
			
		}
	}
	
	// Dijkstra's shortest path for dense graphs (neighbour matrix):
	public static int[] dijkstra(int[][] nm, int start) {
		int N = nm.length;
		boolean[] done = new boolean[N];
		int[] dist = new int[N];
		Arrays.fill(dist, Integer.MAX_VALUE / 3);
		dist[start] = 0;
		int next = start;
		for (int i = 0; i < N; i++) {
			done[next] = true;
			int node = -1;
			int best = Integer.MAX_VALUE / 2;
			for (int j = 0; j < N; j++) {
				dist[j] = Math.min(dist[j], dist[next] + nm[next][j]);
				if (!done[j] && dist[j] < best) {
					node = j;
					best = dist[j];
				}
			}
			next = node;
		}
		return dist;
	}
}
