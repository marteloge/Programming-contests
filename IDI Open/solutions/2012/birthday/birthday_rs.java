import java.io.*;
public class birthday_rs {
	public static void main(String[] a) throws Exception {
		new birthday_rs().go();
	}
	void go() throws Exception {
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		int T=Integer.parseInt(in.readLine());
		while(T-->0) {
			int N,K;
			String z[]=in.readLine().split(" ");
			int n=Integer.parseInt(z[0]);
			int k=Integer.parseInt(z[1]);
			double p=0,q=1;
			int s=1;
			for(int i=0;i<n;i+=k,s*=-1) {
				for(int j=0;j<k;j++) q*=(n-i-j)/(n-1.);
				q/=i+k;
				p+=q*s;
//				if(q<1e-40) break;
			}
			System.out.printf("%.16f\n",p);
		}
	}
}
