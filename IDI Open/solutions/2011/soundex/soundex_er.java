import java.io.*;
import java.util.*;


public class soundex_er {
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
			System.out.println(new soundex_er().go(TOKEN().toCharArray(), INT()));
			
		}
	}

	private static final long MOD = 1000000007L;
	
	private static final int[] alphabet
		= new int[] {7,1,2,3,7,1,2,0,7,2,2,4,5,5,7,1,2,6,2,3,7,1,0,2,7,2,8};
	
	private long[][][] memo;
	private int[] code;

	private long go(char[] code, int length) {
		this.code = new int[code.length];
		for(int i = 0;i<this.code.length;i++) {
			this.code[i] = code[i]-'0';
		}

		memo = new long[5][9][length+1];
		for(long[][] ll : memo)
			for(long[] l : ll)
				Arrays.fill(l, -1);
		
		return solve(0,8,length);
		
	}

	private long solve(int codeind, int prevtype, int lettersleft) {
		if(lettersleft == 0)return (codeind>=code.length || code[codeind]==0)?1:0;
		
		if(memo[codeind][prevtype][lettersleft]>=0)
			return memo[codeind][prevtype][lettersleft];
		
		long count = 0;
		if(codeind>=code.length || code[codeind]==0)count++;

		if(codeind==0) {
			count += solve(codeind+1, 8,lettersleft-1);
			count %= MOD;
		} else {
			

			for(char next = 'a';next<='z';next++) {
				int nexttype = alphabet[next-'a'];
				if(codeind >= code.length) {
					count += solve(codeind, nexttype,lettersleft-1);
				} else {
					if(nexttype==prevtype || nexttype==7) {
						count += solve(codeind, nexttype,lettersleft-1);
					} else if(nexttype==0) {
						count += solve(codeind, prevtype, lettersleft-1);
					} else if(nexttype==code[codeind]) {
						count += solve(codeind+1, nexttype, lettersleft-1);
					}
				}
				count %= MOD;
			}
			
		}
		
		memo[codeind][prevtype][lettersleft] = count;
		
		return count;
	}
	
	
	
}
