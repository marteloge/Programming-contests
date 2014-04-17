import java.io.*;
import java.text.*;
import java.util.*;


public class beads_er_fenwick {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String LINE() throws Exception { return stdin.readLine(); }
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static long LONG() throws Exception {return Long.parseLong(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}

	
	static DecimalFormat DF = new DecimalFormat("0.000",new DecimalFormatSymbols(Locale.ENGLISH));
	
	public static void main(String[] args) throws Exception {
		int games = INT();
		while(games-->0) {
			new beads_er_fenwick().go();
		}
	}
	
	public void go() throws Exception {
		int b = INT(), p = INT(), q = INT();
		int n = p+q;
		
		FenwickTree tree = new FenwickTree(b);
		
		while(n-->0) {
			StringBuilder sb = new StringBuilder();
			String action = TOKEN();
			if("P".equals(action)) {
				int box = INT();
				int amount = INT();
				tree.add(box, amount);
			} else {
				int lowerBound = INT();
				int upperBound = INT();
				long cnt = tree.cumulativeFreq(upperBound) - tree.cumulativeFreq(lowerBound-1);
				sb.append(cnt);
				sb.append("\n");
			}
			System.out.print(sb.toString());
		}
		
	}
	
	public static class FenwickTree {
		
		int[] tree;
		
		FenwickTree(int maxval) {
			tree = new int[maxval+1];
		}
		
		public int cumulativeFreq(int N) {
			if(N<0 || N>=tree.length)return 0;
			int sum = 0;
			while (N > 0){
				sum += tree[N];
				N -= (N & -N);
			}
			return sum;
		}
		
		public void add(int N, int amount) {
			if(N<0 || N>=tree.length)return;
			while (N < tree.length){
				tree[N] += amount;
				N += (N & -N);
			}
		}
		
	}
	
}
