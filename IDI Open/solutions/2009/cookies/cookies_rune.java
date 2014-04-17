// Solution for Letter Cookies
// Author: Rune Fevang

import java.io.*;
import java.util.*;
public class cookies_rune{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(String s){ return Integer.parseInt(s);}
	static StringTokenizer st;
	static void ST() throws Exception { st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int n = INT(in.readLine());
		for(int t = 0; t < n; t++){
			new cookies_rune().entry();
		}
	}

	void entry() throws Exception{
		int[] box = get(in.readLine());
		int w = INT(in.readLine());
		outer:
		for(int i = 0; i < w; i++){
			int[] test = get(in.readLine());
			for(int j = 0; j < 26; j++){
				if(box[j] < test[j]){
					System.out.println("NO");
					continue outer;
				}
			}
			System.out.println("YES");
		}
	}

	int[] get(String s){
		int[] ret = new int[26];
		for(int i = 0; i < s.length(); i++){
			ret[s.charAt(i)-'A']++;
		}
		return ret;
	}
}
