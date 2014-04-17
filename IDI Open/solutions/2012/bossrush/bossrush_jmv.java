public class bossrush_jmv {
	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		for (int T = in.nextInt(); T-- > 0; ) {
			int N = in.nextInt();
			int[][][] B = new int[3][100][11];
			java.util.HashMap<String, Integer> M = new java.util.HashMap<String, Integer>();
			int c = 0, w;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < 3; j++) {
					B[j][i][10] = w = in.nextInt();
					for (int k = 0; k < w; k++) {
						String s = in.next();
						if (M.containsKey(s)) B[j][i][k] = M.get(s);
						else M.put(s, B[j][i][k] = c++);
					}
				}
			}
			int min = N;
			for (w = 0; w < 3; w++) {
				int[] bm = new int[N], wm = new int[2 * c];
				java.util.Arrays.fill(wm, -1);
				for (int i = 0; i < N; i++) {
					java.util.Arrays.fill(bm, -1);
					if (!dfs(B[w], bm, wm, i)) {
						min = Math.min(min, i);
						break;
					}
				}
			}
			System.out.println(min);
		}
	}
	
	static boolean dfs(int[][] B, int[] bm, int[] wm, int n) {
		bm[n] = 0;
		for (int i = 0; i < B[n][10]; i++) {
			for (int j = 2 * B[n][i]; j < 2 * B[n][i] + 2; j++) {
				if ((wm[j] == -1) || ((bm[wm[j]] == -1) && dfs(B, bm, wm, wm[j]))) {
					bm[n] = j;
					wm[j] = n;
					return true;
				}
			}
		}
		return false;
	}
}