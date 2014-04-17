/*
 * Solution for Problem D - Balancing Weights
 * 
 * The sign of the sum of the input numbers define the answer.
 * 
 * O(N)
 *  
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;
public class balance_er {
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(stdin.readLine());
		int T = Integer.parseInt(st.nextToken());
		while(T-->0) {
			st = new StringTokenizer(stdin.readLine());
			int N = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(stdin.readLine());
			int sum = 0;
			while(N-->0)sum += Integer.parseInt(st.nextToken());
			System.out.println(sum==0?"Equilibrium":(sum<0?"Left":"Right"));
		}
	}
}
