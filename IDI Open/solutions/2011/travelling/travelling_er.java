import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class travelling_er {
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
			for(int k = 0;k<N;k++)for(int i = 0;i<N;i++)for(int j = 0;j<N;j++)
				dists[i][j] =Math.min(dists[i][j], dists[i][k]+dists[k][j]);
			
			long distance = 0;
			for(int i = 0;i<N;i++)distance += dists[order[i]][order[(i+1)%N]];
			if(distance>=Integer.MAX_VALUE / 3)System.out.println("impossible");
			else System.out.println(distance);
			
		}
	}
}
