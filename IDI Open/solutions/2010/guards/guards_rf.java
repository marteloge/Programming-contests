import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
@SuppressWarnings("unchecked")
class guards_rf{

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(Object o){return Integer.parseInt(o.toString());}
	static StringTokenizer st;
	static void ST() throws Exception{st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int T = INT(in.readLine());
		for(int i = 0; i < T; i++){
			System.out.println(new guards_rf().entry());
		}
	}

	String entry() throws Exception{
		ST();
		int n = INT(st.nextToken());
		int m = INT(st.nextToken());
		int L = INT(st.nextToken());
		double[] ps = new double[n];
		for(int i = 0; i < n; i++) ps[i] = Double.parseDouble(st.nextToken());
		sort(ps);
		if(n == 0)""+(L/(double)m);

		double ans = 0;
		double inc = L/2.0;
		while(ans+inc != ans){
			if(get(L,ps,ans+inc) <= m) ans += inc;
			inc /= 2;
		}
		return ""+(L-ans);
	}

	int get(int L, double[] ps, double d){
		d = L-d;
		int ret = 0;
		for(int i = 0; i < ps.length; i++){
			double D;
			if(i == ps.length-1) D = L-ps[i]+ps[0];
			else D = ps[i+1]-ps[i];
			ret += (int)(D/d);
		}
		return ret;
	}
}
