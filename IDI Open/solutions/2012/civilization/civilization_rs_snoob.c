/*	solution by ruben spaans
		using bitmasks and gosper's hack (snoob), trying each answer
		from 1 and up.
		runtime: O(n*2^n), but terminates after finding the answer.
*/
#include <stdio.h>
int n;
int w[18],c[18],f[18];
/*	return next k-selection (bitmask) */
int snoob(int x) {
	int s=x&-x, r=x+s, o=x^r;
	o=(o>>2)/s;
	return r|o;
}
int main() {
	int T,W,C,F,i,ans,lim,mask,x,y,z;
	scanf("%d",&T);
	while(T--) {
		scanf("%d",&n);
		scanf("%d %d %d",&W,&C,&F);
		for(i=0;i<n;i++) scanf("%d %d %d",&w[i],&c[i],&f[i]);
		lim=1<<n;
		for(ans=1;ans<=n;ans++) {
			mask=(1<<ans)-1;
			while(mask<lim) {
				x=y=z=0;
				for(i=0;i<n;i++) if(mask&(1<<i)) x+=w[i],y+=c[i],z+=f[i];
				if(x>=W && y>=C && z>=F) goto done;
				mask=snoob(mask);
			}
		}
	done:
		if(ans>n) puts("game over");
		else printf("%d\n",ans);
	}
	return 0;
}
