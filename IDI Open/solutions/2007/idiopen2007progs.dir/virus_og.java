import java.io.*;

public class virus_og {

	static int l;

	public static long[][] mul(long[][] a, long[][] b) {
		int n = a.length;
		long[][] c = new long[n][n];
		for (int i=0; i<n; ++i) {
			for (int j=0; j<n; ++j) {
				for (int k=0; k<n; ++k) {
					c[i][k] += a[i][j]*b[j][k];
					if (c[i][k] > l) c[i][k] = l;
				}
			}
		}
		return c;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(in.readLine());
		while(t-->0){
			String[] inp = in.readLine().split(" ");
			int n = Integer.parseInt(inp[0]);
			l = Integer.parseInt(inp[1]);
			long[][][] as = new long[99][n+1][n+1];
			for(int i=0;i<n;i++){
				inp = in.readLine().split(" ");
				for(int j=0; j<=n ; j++) as[0][i][j]=Integer.parseInt(inp[j]);
			}
			as[0][n][n]=1;

			for (int i=1; i<as.length; ++i) {
				as[i] = mul(as[i-1], as[i-1]);
			}

			long inf = 100000000000l;
			long le = 0;
			long ri = inf;
			boolean quickStart = true;

			while(le<ri){
				long m= quickStart ? 8 : (le+ri)/2;
				quickStart = false;
				long mu = m;
				int ind = 0;
				long[][] A = new long[n+1][n+1];
				for (int i=0; i<=n; ++i) A[i][i] = 1;
				while (mu > 0) {
					if ((1&mu)!=0) {
						A = mul(A, as[ind]);
					}
					++ind;
					mu /= 2;
				}
				if(A[0][n]<l) le=m+1;
				else ri=m;
			}

			if(le == inf) System.out.println("lucky");
			else System.out.println(le);
		}
	}
}
