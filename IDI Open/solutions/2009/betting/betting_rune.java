// Solution for Box Betting
// Author: Rune Fevang

import java.io.*;
import java.util.*;
public class betting_rune{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(String s){ return Integer.parseInt(s);}
	static StringTokenizer st;
	static void ST() throws Exception { st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int n = INT(in.readLine());
		for(int t = 0; t < n; t++) new betting_rune().entry();
	}

	void entry() throws Exception{
		int b = INT(in.readLine());
		int[] bs = new int[b];
		String s = in.readLine();
		for(int i = 0; i < s.length(); i++) bs[i] = s.charAt(i)-'A';
		ST();
		int L = INT(st.nextToken());
		int U = INT(st.nextToken());
		double pa = 0;
		double pb = 0;
		double pc = 0;
		int lacc = 0;
		int uacc = 0;
		int lpos = 0;
		int upos = 0;
		double p = 1.0/bs.length;
		for(int i = 0; i < b; i++){
			while(lpos < i || lpos < b && lacc+bs[lpos] < L) lacc += bs[lpos++];
			while(upos < i || upos < b && uacc+bs[upos] <= U) uacc += bs[upos++];
			double low = (lpos-i)/(double)(b-i);
			double mid = (upos-i)/(double)(b-i) - low;
			pa += p*low;
			pb += p*mid;
			pc += p*(1-low-mid);
			lacc -= bs[i];
			uacc -= bs[i];
		}
		System.out.println(String.format("%f %f %f",pb,pa,pc).replace(',','.'));
	}
}




