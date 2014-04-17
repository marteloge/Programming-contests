import java.io.*;
import java.math.*;
import java.util.*;

public class inverse_rs {
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	StringTokenizer st=new StringTokenizer("");
	String LINE() throws Exception { return in.readLine(); }
	String STR() throws Exception {
		while(!st.hasMoreTokens()) st=new StringTokenizer(LINE());
		return st.nextToken();
	}
	int INT() throws Exception { return Integer.parseInt(STR()); }
	long LONG() throws Exception { return Long.parseLong(STR()); }
	double DOUBLE() throws Exception { return Double.parseDouble(STR()); }
	String PD(double d,int n) {
		return String.format("%."+n+"f",d).replace(",",".");
	}

	public static void main(String args[]) throws Exception {
		new inverse_rs().go();
	}

	boolean[] createsieve(int n) {
		boolean s[] = new boolean[n];
		for(int i=2; i*i<n; i++) if(!s[i]) for(int j=i*i; j<n; j+=i) s[j]=true;
		return s;
	}
	int[] getprimes(boolean s[]) {
		int n=0;
		for(int i=2; i<s.length; i++) if(!s[i]) n++;
		int p[]=new int[n], q=0;
		for(int i=2; i<s.length; i++) if(!s[i]) p[q++]=i;
		return p;
	}
	boolean sieve[];
	int primes[];
	int prod[]=new int[1000000];	/*	prime power */
	int sumdiv[]=new int[1000000];	/*	sum of divisors of this prime power */
	int prim[]=new int[1000000];	/*	base prime */
	int nn;
	ArrayList<Integer> ans;
	
	boolean isprime(int n) {
		if(n<1000000) return sieve[n];
		for(int i=0;primes[i]*primes[i]<n;i++) if(n%primes[i]<1) return false;
		return true;
	}
	void btr(int ix,int at,int orinum,int divsum) {
		if(divsum<2) {
			ans.add(orinum);
			return;
		}
		if(divsum-1>999999 && divsum-1>at && isprime(divsum-1))
			ans.add(orinum*(divsum-1));
		for(;ix<nn;ix++) if(prim[ix]>at && divsum%sumdiv[ix]<1)
			btr(ix+1,prim[ix],orinum*prod[ix],divsum/sumdiv[ix]);
	}
	public void go() throws Exception {
		int T=INT();
		sieve=createsieve(1000000);
		primes=getprimes(sieve);
		while(T-->0) {
			int n=INT();
			nn=0;
			/*	check all eligible divisors for each prime */
			for(int i=0;i<primes.length && primes[i]<=n;i++) {
				long teller=(long)primes[i]*primes[i];
				long nevner=primes[i]-1;
				while((teller-1)/nevner<=n) {
					long z=(teller-1)/nevner;
					if(n%z<1) {
						prod[nn]=(int)(teller/primes[i]);
						sumdiv[nn]=(int)z;
						prim[nn++]=primes[i];
					}
					teller*=primes[i];
				}
			}
			/*	ready to backtrack */
			ans=new ArrayList<Integer>();
			btr(0,0,1,n);
			if(ans.size()==0) System.out.println("none!");
			else {
				Collections.sort(ans);
				StringBuilder b=new StringBuilder(String.format("%d",ans.get(0)));
				int antall=ans.size();
				for(int i=1;i<antall;i++) b.append(String.format(" %d",ans.get(i)));
				System.out.println(b.toString());
			}
		}
	}
}
