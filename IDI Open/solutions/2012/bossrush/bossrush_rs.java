import java.io.*;
import java.math.*;
import java.util.*;

public class bossrush_rs {
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
		new bossrush_rs().go();
	}

	public void go() throws Exception {
		int T=INT();
		while(T-->0) solve();
	}
	int w[][];
	int ww[][][];
	HashMap<String,Integer> names[];
	int num[];
	int add(int j,String s) {
		if(!names[j].containsKey(s)) {
			names[j].put(s,num[j]);
			return num[j]++;
		}
		return names[j].get(s);
	}
	void solve() throws Exception {
		int bossn=INT();
		num=new int[3];
		names=new HashMap[3];
		w=new int[bossn][3];
		ww=new int[bossn][3][];
		for(int j=0;j<3;j++) names[j]=new HashMap<String,Integer>();
		for(int i=0;i<bossn;i++) {
			for(int j=0;j<3;j++) {
				w[i][j]=INT();
				ww[i][j]=new int[w[i][j]];
				for(int k=0;k<w[i][j];k++) {
					String s=STR();
					ww[i][j][k]=add(j,s);
				}
			}
		}
		int ans=10000;
		for(int j=0;j<3;j++) {
			int source=0;
			int left=1;
			int right=1+bossn;
			int sink=right+num[j];
			int n=sink+1;
			int cap[][]=new int[n][n];
			int cur=0;
			/*	from weapon to sink */
			for(int i=0;i<num[j];i++) cap[right+i][sink]=2;
			/*	from boss to weapon */
			for(int i=0;i<bossn;i++) for(int k=0;k<w[i][j];k++)
				cap[left+i][right+ww[i][j][k]]=1;
			for(int i=0;i<bossn;i++) cap[source][left+i]=1;
			MaxFlow flyt=new MaxFlow(cap);
			for(int i=0;i<bossn;i++) cap[source][left+i]=0;
			/*	add one boss at a time */
			for(int i=0;i<bossn;i++) {
				flyt.c[source][left+i]=1;
				int next=flyt.maxflow(source,sink);
				if(next<1) break;
				cur++;
			}
			ans=Math.min(cur,ans);
		}
		System.out.println(ans);
	}

	class MaxFlow {
		int c[][],n,f[][],prev[],INF=1000000000,flag[];
		int to[],gs[],ge[];
		boolean taken[];
		MaxFlow(int cap[][]) {
			c=cap;
			n=c.length;
			f=new int[n][n];
			prev=new int[n];
			flag=new int[n];
			taken=new boolean[n];
			gs=new int[n]; ge=new int[n];
			Arrays.fill(ge,-1); Arrays.fill(gs,-1);
			int ne=0,ix=0;
			for(int i=0;i<n;i++) for(int j=0;j<i;j++) if(c[i][j]>0 || c[j][i]>0) ne+=2;
			to=new int[ne];
			for(int i=0;i<n;i++) for(int j=0;j<n;j++) if(c[i][j]>0 || c[j][i]>0) {
				ge[i]=ix+1;
				if(gs[i]<0) gs[i]=ix;
				to[ix++]=j;
			}
		}
		int maxflow(int SOURCE,int SINK) {
			int res=0;
			while(augmentbfs(SOURCE,SINK)) {
				int flyt=INF,pos=SINK;
				while(prev[pos]>-1) {
					flyt=Math.min(flyt,c[prev[pos]][pos]-f[prev[pos]][pos]);
					pos=prev[pos];
				}
				pos=SINK;
				while(prev[pos]>-1) {
					f[prev[pos]][pos]+=flyt;
					f[pos][prev[pos]]-=flyt;
					pos=prev[pos];
				}
				pos=SINK;
				res+=flyt;

			}
			return res;
		}
		boolean augmentbfs(int SOURCE,int SINK) {
			Arrays.fill(prev,-1);
			Arrays.fill(taken,false);
			LinkedList<Integer> q=new LinkedList<Integer>();
			q.add(SOURCE);
			taken[SOURCE]=true;
			while(q.size()>0) {
				int cur=q.poll();
				for(int k=gs[cur];k<ge[cur];k++) {
					int i=to[k];
					if(c[cur][i]>f[cur][i] && !taken[i]) {
						prev[i]=cur;
						if(i==SINK) return true;
						q.add(i);
						taken[i]=true;
					}
				}
			}
			return false;
		}
		boolean augmenthack(int SOURCE,int SINK) {
			Arrays.fill(prev,-1);
			Arrays.fill(flag,0);
			flag[SOURCE]=1;
			boolean done;
			do {
				done=true;
				for(int i=0;i<n;i++) if(flag[i]==1) {
					flag[i]=2;
					for(int k=gs[i];k<ge[i];k++) {
						int j=to[k];
						if(flag[j]<1 && c[i][j]>f[i][j]) {
							prev[j]=i;
							if(j==SINK) return true;
							flag[j]=1;
							done=false;
						}
					}
				}
			} while(!done);
			return false;
		}
	}
}
