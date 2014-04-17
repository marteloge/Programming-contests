/*	solution by ruben spaans
		standard backtracking over every possibility, no pruning.
		runtime: O(n*2^n)
*/
#include <stdio.h>
#define INF 1000
int n;
int w[18],c[18],f[18];
int btr(int wleft,int cleft,int fleft,int at) {
	int r,s;
	if(at==n) return wleft>0||cleft>0||fleft>0?INF:0;
	r=btr(wleft,cleft,fleft,at+1);
	s=1+btr(wleft-w[at],cleft-c[at],fleft-f[at],at+1);
	return r<s?r:s;
}
int main() {
	int T,W,C,F,i,r;
	scanf("%d",&T);
	while(T--) {
		scanf("%d",&n);
		scanf("%d %d %d",&W,&C,&F);
		for(i=0;i<n;i++) scanf("%d %d %d",&w[i],&c[i],&f[i]);
		r=btr(W,C,F,0);
		if(r>=INF) puts("game over");
		else printf("%d\n",r);
	}
	return 0;
}
