public class penguin_jmv {
	static final int[] dx = {1, 0, -1, 0}, dy = {0, -1, 0, 1};
	int X, Y, px, py, gx, gy;
	int[][] L;
	int[] P;
	
	penguin_jmv(java.util.Scanner in) {
		X = in.nextInt();
		Y = in.nextInt();
		px = in.nextInt();
		py = in.nextInt();
		gx = in.nextInt();
		gy = in.nextInt();
		in.nextLine();
		L = new int[Y][X];
		for (int y = 0; y < Y; y++) {
			String l = in.nextLine();
			for (int x = 0; x < X; x++) {
				char c = l.charAt(x);
				L[y][x] = c - (c < 'A' ? '0' : 'A' - 10);
			}
		}
		P = new int[X * Y];
	}

	boolean dfs(int x, int y, int d, int r, int c) {
//		System.err.printf("x=%d   y=%d  d=%d  r=%d  c=%d\n", x, y, d, r, c);
		if (x == gx && y == gy) {
			P[c] = '!';
			return true;
		}
		if ((L[y][x] & (1 << 4)) != 0) r = (r + 1) % 4;
		for (int i = 0; i < 4; i++) {
			if ((L[y][x] & (1 << i)) != 0 && (d - i + 4) % 4 != 2 && dfs(x + dx[i], y + dy[i], i, r, c + 1)) {
				P[c] = (i + r) % 4;
				return true;
			}
		}
		return false;
	}
	
	void go() {
		dfs(px, py, -10, 0, 0);
		for (int i = 0; ; i++) {
			if (P[i] == '!') break;
			System.out.print(P[i] < 2 ? P[i] == 0 ? 'E' : 'N' : P[i] == 2 ? 'W' : 'S');
		}
		System.out.println();
	}

	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		for (int T = in.nextInt(); T > 0; T--) {
			new penguin_jmv(in).go();
		}
	}
}