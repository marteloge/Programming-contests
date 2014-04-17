/*	solution by ruben spaans
		solve for each weapon category separately and take the minimum across
		these.
		make a bipartite graph with the boss on the left side and weapons on
		the right (two nodes for each weapon, since we can use two). then, do
		a bipartite matching by matching from each boss node in increasing order,
		and exit whenever no augmenting path can be found.
		time complexity: O(N^2 W), where W is the maximum number of weapons that
		can be used against a boss.
*/

#include <stdio.h>
#include <string.h>

#define INF 1000000

char weapon[100][3][10][40];	/*	weapon name */
int wn[100][3];								/*	[i][j]: number of weapons for boss i, category j */
int bossn;

#define MAXVL 100
#define MAXVR 2000
char g[MAXVL][MAXVR];       /*	[i][j]: edge between i on the left and j on the right */
int n,m;                    /*	left, right */
int mate[MAXVR];            /*	mate for right node (or -1 if unmatched) */
int parent[MAXVL+MAXVR];

int bipartite() {
  int i,j,r=0,cur,qs,qe=0;
	static int q[MAXVL+MAXVR];
  memset(mate,-1,m*sizeof(int));
  memset(parent,-1,(n+m)*sizeof(int));
  for(i=0;i<n;i++) {
    qs=0;
    q[qe++]=i;
    while(qs<qe) {
      cur=q[qs++];
      if(cur<n) {
        for(j=n;j<m+n;j++) if(g[cur][j-n] && parent[j]<0) {
          parent[j]=cur;
          if(mate[j-n]<0) {
            q[qe++]=j;
            while(j>-1) {
              mate[j-n]=parent[j];
              j=parent[parent[j]];
            }
            r++;
            goto ok;
          } else q[qe++]=j;
        }
      } else {
        q[qe++]=mate[cur-n];
        parent[mate[cur-n]]=cur;
      }
    }
		return i;
  ok:
    while(qe) parent[q[--qe]]=-1;
  }
  return n;
}

char *name[1000];
int nn;

int getname(char *s) {
	int i;
	for(i=0;i<nn;i++) if(!strcmp(s,name[i])) return i;
	name[nn]=s;
	return nn++;
}

int solve(int z) {
	int i,j,no;
	n=bossn;
	nn=m=0;
	memset(g,0,sizeof(g));
	for(i=0;i<bossn;i++) for(j=0;j<wn[i][z];j++) {
		no=getname(weapon[i][z][j]);
		g[i][no*2]=g[i][no*2+1]=1;
	}
	m=nn<<1;
	return bipartite();
}

int main() {
	int T,i,j,k,cur,ans;
	scanf("%d",&T);
	while(T--) {
		scanf("%d",&bossn);
		for(i=0;i<bossn;i++) {
			for(j=0;j<3;j++) {
				scanf("%d",&wn[i][j]);
				for(k=0;k<wn[i][j];k++) scanf("%s",weapon[i][j][k]);
			}
		}
		ans=solve(0);
		for(i=1;i<3;i++) {
			cur=solve(i);
			if(ans>cur) ans=cur;
		}
		printf("%d\n",ans);
	}
	return 0;
}
