/*
 * Author: Eirik Reksten
 * 
 * Solution for Marble Madness from IDI Open 2009
 * 
 * The answer is determined by looking at the number of white marbles alone.
 * 
 */


import java.io.*;
import java.util.*;

public class marble_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			INT();
			System.out.println(INT()%2==0?"1 0":"0 1");
		}
	}

}
