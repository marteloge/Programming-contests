// Solution for Marble Madness
// Author Rune Fevang

import java.io.*;
import java.util.*;
public class marble_rune{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(String s){ return Integer.parseInt(s);}
	static StringTokenizer st;
	static void ST() throws Exception { st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		new marble_rune().entry();
	}

	void entry() throws Exception{
		int n = INT(in.readLine());
		for(int i = 0; i < n; i++){
			ST();
			st.nextToken();
			if((Long.parseLong(st.nextToken())&1) == 1) System.out.println("0.00 1.00");
			else System.out.println("1.00 0.00");
		}
	}
}




