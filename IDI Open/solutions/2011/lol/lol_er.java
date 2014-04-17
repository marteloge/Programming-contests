import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class lol_er {
	public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public static StringTokenizer st = null;
	public static String TOKEN() throws IOException {
		while(st==null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	public static void main(String[] args) throws IOException {
		int cases = Integer.parseInt(TOKEN());
		while(cases-->0) {
			System.out.println(solve(TOKEN()));
		}
	}
	
	public static int solve(String word) {
		if(word.contains("lol"))return 0;
		if(word.contains("lo") || word.contains("ol") || word.contains("ll") || word.matches(".*l.l.*"))return 1;
		if(word.contains("l") || word.contains("o"))return 2;
		return 3;
	}
	
}
