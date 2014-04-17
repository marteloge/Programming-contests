import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
@SuppressWarnings("unchecked")
class cookie_rf{

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(Object o){return Integer.parseInt(o.toString());}
	static StringTokenizer st;
	static void ST() throws Exception{st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int T = INT(in.readLine());
		for(int i = 0; i < T; i++){
			System.out.println(new cookie_rf().entry());
		}
	}

	int entry() throws Exception{
		ST();
		int n = INT(st.nextToken());
		int c = INT(st.nextToken());
		return (n+c-1)/c;
	}
}