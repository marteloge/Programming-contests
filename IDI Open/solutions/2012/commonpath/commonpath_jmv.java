public class commonpath_jmv {
	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		for (int t = in.nextInt(); t-- > 0; ) {
			int N = in.nextInt(), M = in.nextInt(), S = in.nextInt(), P = in.nextInt(), Q = in.nextInt();
			int[][] G = new int[2000][2000], T = new int[2000][2000];
			int[] A = new int[2000], B = new int[1], C = new int[2000], D = new int[2000];
			java.util.Arrays.fill(A, -1);
			java.util.Arrays.fill(D, Integer.MAX_VALUE);
			D[S] = 0;
			for (int i = 0; i < M; i++) {
				int a = in.nextInt(), b = in.nextInt();
				G[a][C[a]] = b; 
				G[b][C[b]] = a;
				T[a][C[a]++] = T[b][C[b]++] = in.nextInt();
			}
			
			java.util.PriorityQueue<X> q = new java.util.PriorityQueue<X>();
			q.add(new X(S, 0));
			while (!q.isEmpty()) {
				int n = q.poll().n;
				if (D[n] >= 0) 
					D[n] = -D[n];
				else continue;
				for (int i = 0; i < C[n]; i++) {
					if (-D[n] + T[n][i] < D[G[n][i]]) 
						q.add(new X(G[n][i], D[G[n][i]] = -D[n] + T[n][i]));
				}
			}
			dfs(D, G, T, S, P, Q, C, A, B);
			System.out.println(B[0]);
		}
	}
	
	static int dfs(int[] D, int[][] G, int[][] T, int n, int P, int Q, int[] C, int[] A, int[] B) {
		if (A[n] != -1) return A[n];
		int r = 0;
		if (n == P) r |= 1;
		if (n == Q) r |= 2;
		for (int i = 0; i < C[n]; i++) {
			if (D[n] - T[n][i] == D[G[n][i]])
				r |= dfs(D, G, T, G[n][i], P, Q, C, A, B);
		}
		if (r == 3) B[0] = Math.max(B[0], -D[n]);
		return A[n] = r;
	}
	
	static class X implements Comparable<X> {
		int p, n;
		X(int n, int p) {this.n = n; this.p = p;}
		public int compareTo(X Y) {return p - Y.p;}
	}
}