/*
 * Solution for Problem G - Cookie Monster
 * 
 * Calculating ceil(N/C) using integer arithmetics.
 *  
 * O(1)
 * 
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;
public class cookie_er {
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(stdin.readLine());
		int T = Integer.parseInt(st.nextToken());
		while(T-->0) {
			st = new StringTokenizer(stdin.readLine());
			int N = Integer.parseInt(st.nextToken()), C = Integer.parseInt(st.nextToken());
			System.out.println((N+C-1)/C);
		}
	}
}
