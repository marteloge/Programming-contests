/*
	solution by ruben spaans, regular dp
*/

import java.io.*;

public class candy_rs {
	public static void main(String[] a) throws Exception {
		new candy_rs().go();
	}
	int MOD=65537;
	void go() throws Exception {
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		int T=Integer.parseInt(in.readLine());
		while(T-->0) {
			int N,C;
			String s[]=in.readLine().split(" ");
			N=Integer.parseInt(s[0]);
			C=Integer.parseInt(s[1]);
			int a[]=new int[N];
			int sum=0;
			s=in.readLine().split(" ");
			for(int i=0;i<N;i++) {
				a[i]=Integer.parseInt(s[i]);
				sum+=a[i];
			}
			int prev[],cur[]=new int[sum+1];
			cur[0]=1;
			for(int i=0;i<N;i++) {
				prev=cur;
				cur=new int[sum+1];
				for(int j=0;j<=sum;j++) if(prev[j]>0) {
					cur[j]=(cur[j]+prev[j])%MOD;
					cur[j+a[i]]=(cur[j+a[i]]+prev[j])%MOD;
				}
			}
			int ans=0;
			while(C<=sum) ans=(ans+cur[C++])%MOD;
			System.out.println(ans);
		}
	}
}
