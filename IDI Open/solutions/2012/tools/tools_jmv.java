public class tools_jmv {
	
	static final long P = (1 << 31) - 1;

	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		for (int T = in.nextInt(); T > 0; T--) {
			long q = 0;
			int n = in.nextInt(), t = in.nextInt();
			Set s = new Set();
			s.n = n;
			java.util.ArrayList<Set> l = new java.util.ArrayList<Set>();
			java.util.HashMap<String, Set> M = new java.util.HashMap<String, Set>();
			for (int i = 0; i < n; i++) {
				M.put(in.next(), s);
			}
			
			for (int i = 0; i < t; i++) {
				long o = 1;
				l.clear();
				int k = in.nextInt();
				for (int j = 0; j < k; j++) {
					String x = in.next();
					Set r = M.get(x);
					if (r.split == null) {
						r.split = new Set();
						l.add(r);
					}
					M.put(x, r.split);
					r.split.n++;
				}
				for (Set r : l) {
					o = (o * ncr(r.n, r.split.n)) % P;
					r.n -= r.split.n;
					r.split = null;
				}
				q = (q + o) % P;
			}
			
			System.out.println(q);
		}
	}
	
	static long ncr(int n, int r) {
		long s = 1, t = 1;
		int m = r < n - r ? r : n - r;
		for (int i = 0; i < m; i++) {
			s = (s * (n - i)) % P;
			t = (t * (i + 1)) % P; 
		}
		return (s * inv(t)) % P;
	}
	
	static long inv(long t) {
		long q = 1;
		for (int i = 0; (1L << i) <= (P - 2); i++) {
			if (((1L << i) & (P - 2)) != 0) q = (q * t) % P;
			t = (t * t) % P;
		}
		return q;
	}
	
	static class Set {
		int n;
		Set split;
		
		Set() {
			n = 0;
			split = null;
		}
	}
}