/*
	solution by ruben spaans
	calculate 2^N - sum(i=0..c-1) dp[i] using dp
*/

#include <stdio.h>
#include <string.h>
#define MOD 65537
int dp[10000];
int n,c;
int a[200];

int main() {
	int T,p2,i,j,maks,v;
	scanf("%d",&T);
	while(T--) {
		scanf("%d %d",&n,&c);
		for(p2=1,i=0;i<n;i++) {
			scanf("%d",&a[i]);
			p2=p2*2%65537;
		}
		memset(dp,0,c*sizeof(int));
		dp[0]=1;
		for(maks=i=0;i<n;i++) {
			for(j=maks>=c?c-1:maks;j>=0;j--) if((v=dp[j])) {
				if(j+a[i]<c) dp[j+a[i]]=(dp[j+a[i]]+v)%MOD;
			}
			maks+=a[i];
		}
		for(v=i=0;i<c;i++) v=(v+dp[i])%MOD;
		printf("%d\n",(p2+MOD-v)%MOD);
	}
	return 0;
}
