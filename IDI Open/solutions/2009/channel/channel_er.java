/*
 * Author: Eirik Reksten
 * 
 * Solution for Communication Channels from IDI Open 2009
 * 
 */

import java.util.*;
import java.io.*;

public class channel_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0)System.out.println(TOKEN().equals(TOKEN())?"OK":"ERROR");
	}
	
}
