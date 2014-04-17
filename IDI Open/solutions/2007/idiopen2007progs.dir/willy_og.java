import java.util.*;
import java.io.*;

public class willy_og {

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(in.readLine());
		for (int i=0; i<T; ++i)
			System.out.println(new willy_og().go());
	}

	int N, P, L;
	int[][][] perm;

	String apply(char[] w, int[] p) {
		char[] c = new char[N];
		for (int i=0; i<N; ++i) {
			c[i] = w[p[i]];
		}
		return new String(c);
	}

	int[] reverse(int[] p) {
		int[] a = new int[N];
		for (int i=0; i<N; ++i) {
			a[p[i]] = i;
		}
		return a;
	}

	Set<String> advance(Set<String> set, int[][] ps) {
		Set<String> ans = new HashSet<String>();
		for (String s : set) {
			char[] w = s.toCharArray();
			for (int[] p : ps) {
				ans.add(apply(w, p));
			}
		}
		return ans;
	}

	String go() throws Exception {
		String[] ss = in.readLine().split(" ");
		N = Integer.parseInt(ss[0]);
		P = Integer.parseInt(ss[1]);
		L = Integer.parseInt(ss[2]);
		String[] words = in.readLine().split(" ");
		perm = new int[2][P][N];
		for (int i=0; i<P; ++i) {
			char[] c = in.readLine().toCharArray();
			for (int j=0; j<N; ++j) perm[0][i][j] = c[j]-'a';
			perm[1][i] = reverse(perm[0][i]);
		}

		Set<String>[] sets = new Set[2];
		for (int i=0; i<2; ++i) {
			sets[i] = new HashSet<String>();
			sets[i].add(words[i]);
		}

		int ans = 0;
		while (true) {
			for (String s : sets[0]) {
				if (sets[1].contains(s))
					return ""+ans;
			}
			++ans;
			if (ans > L) return "whalemeat";
			int ind = ans % 2;
			sets[ind] = advance(sets[ind], perm[ind]);
		}
	}


}
