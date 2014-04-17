import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class sleeping_er {
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
		
		int T = INT();
		
		while(T-->0) {
			int N = INT(), M = INT(), R = INT();
			
			int[] energy = new int[N];
			for(int i = 0;i<N;i++) {
				energy[i] = INT();
			}
			
			new sleeping_er().go(energy, M, R);
			
		}
	}

	private void go(int[] energy, int m, int r) {
		this.energy = energy;
//		System.out.println(energy.length);
		this.R = r;
		this.memo = new int[energy.length][m+1];
		for(int[] mm : memo)Arrays.fill(mm, -1);
		
		int sol = solve(0,m);
		
		System.out.println(sol>=0?sol:"impossible");
		
	}
	
	int[][] memo;
	int[] energy;
	int R;
	
	public int solve(int at, int mleft) {
		if(mleft == 0)return 0;
//		System.out.println(at+" "+mleft+" "+energy.length);
		if(at>=energy.length)
			return -1;
		if(memo[at][mleft]>=0)
			return memo[at][mleft]-1;
		
		int best = -1;
		
		int now = solve(at+1, mleft);
		if(now >= 0) {
			best = Math.max(best, now);
		}
		
		int energygained = 0;
		for(int sleep = 0;sleep<R && sleep<mleft && at+sleep<energy.length;sleep++) {

			energygained += (sleep+1)*energy[at+sleep];
			
			now = solve(at+sleep+2, mleft-sleep-1);
			
			if(now >= 0) {
				best = Math.max(best, now+energygained);
			}
			
			
		}
		
		memo[at][mleft] = best+1;
		
		return best;
		
		
		
	}
	
}
