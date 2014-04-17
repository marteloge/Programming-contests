import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
@SuppressWarnings("unchecked")
class balance_rf{

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(Object o){return Integer.parseInt(o.toString());}
	static StringTokenizer st;
	static void ST() throws Exception{st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int T = INT(in.readLine());
		for(int i = 0; i < T; i++){
			System.out.println(new balance_rf().entry());
		}
	}

	String entry() throws Exception{
		int n = INT(in.readLine());
		ST();
		int sum = 0;
		for(int i = 0; i < n; i++) sum += INT(st.nextToken());
		if(sum == 0) return "Equilibrium";
		if(sum < 0) return "Left";
		return "Right";
	}
}