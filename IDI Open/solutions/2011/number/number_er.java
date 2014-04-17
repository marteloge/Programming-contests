import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;


public class number_er {
	
	public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public static StringTokenizer st = null;
	
	public static String LINE() throws IOException {
		return stdin.readLine();
	}
	
	public static String TOKEN() throws IOException {
		while(st==null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	
	public static int INT() throws IOException {
		return Integer.parseInt(TOKEN());
	}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			String myLine = LINE().trim();
			System.out.println(myLine.matches("\\A\\d+\\z")?new BigInteger(myLine):"invalid input");
		}
	}
}
