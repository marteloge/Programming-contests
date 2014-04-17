public class candy_jmv {
	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		for (int T = in.nextInt(); T-- > 0; ) {
			int s = 0;
			int[][] D = new int[201][10000];
			D[0][0] = 1;
			int[] c = new int[200];
			int N = in.nextInt(), C = in.nextInt();
			for (int i = 0; i < N; i++) c[i] = in.nextInt();
			for (int n = 0; n < N; n++) {
				for (int i = 0; i < C; i++) {
					D[n + 1][i] = (D[n + 1][i] + D[n][i]) % 65537;
					if (i + c[n] < C) D[n + 1][i + c[n]] = (D[n + 1][i + c[n]] + D[n][i]) % 65537;
				}
			}
			for (int i = 0; i < C; i++) s = (s + D[N][i]) % 65537;
			System.out.println(((1 << (N % 16)) * ((N / 16) % 2 == 0 ? 1 : -1) - s + 2 * 65537) % 65537);
		}
	}
}