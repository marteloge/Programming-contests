/*
 * Author: Eirik Reksten
 * 
 * Solution for Letter Cookies from IDI Open 2009
 * 
 * Counting the number of each letter in each word (and the cookie box),
 * comparing them to eachother.
 * 
 */

import java.io.*;
import java.util.*;

public class cookies_er {

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
			int[] letterCnt = getCount(TOKEN());
			int wrdCnt = INT();
			while(wrdCnt-->0) {
				int[] lcnt = cookies_er.getCount(TOKEN());
				boolean ok = true;
				for(int i = 0;i<26;i++)if(lcnt[i]>letterCnt[i]) {ok = false; break;}
				System.out.println(ok?"YES":"NO");
			}
		}
	}
	
	public static int[] getCount(String ltrs) {
		int[] letterCnt = new int[26];
		for(int i = 0;i<ltrs.length();i++)
			letterCnt[ltrs.charAt(i)-'A']++;
		return letterCnt;
	}

}
