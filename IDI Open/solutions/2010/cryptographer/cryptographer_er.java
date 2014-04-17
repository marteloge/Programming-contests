/*
 * Solution for Problem I - The Diligent Cryptographer
 * 
 * Try all possible permutation lengths and check whether it is valid for this String
 * 'old' will never be a valid answer.
 *  
 * O(N), where N is the length of the input string
 * 
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;

public class cryptographer_er {

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
			if(oldsystem(TOKEN().toCharArray()))System.out.println("unknown");
			else System.out.println("new");
		}
	}
	
	public static boolean oldsystem(char[] input) {
		int usedmask = 0, maxlen = Math.min(input.length,26);
outer:	for(int length = 1;length<=maxlen;length++) {
			int nextletter = input[length-1]-'a';
			if((usedmask&(1<<nextletter))!=0)return false; // Not found solution until a letter is repeated!
			usedmask |= (1<<nextletter);
			if((1<<length)-1==usedmask) { // Possible solution!
				for(int i = length;i<input.length;i++)
					if(input[i]!=input[i%length])continue outer;
				return true;
			}
		}
		if(input.length<=26)return true; // We got cut off before first permutation was finished!
		return false;
	}
}
