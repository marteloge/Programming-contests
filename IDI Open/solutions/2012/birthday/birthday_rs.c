#include <stdio.h>

int main() {
	int T;
	int n,k,i,j,s;
	double p,q;
	scanf("%d",&T);
	while(T--) {
		scanf("%d %d",&n,&k);
		p=0;
		q=s=1;
		for(i=0;i<n;i+=k,s*=-1) {
			if(q<1e-40) break;	/* prune before encountering subnormal numbers */
			for(j=0;j<k;j++) q*=(n-i-j)/(n-1.);
			q/=i+k;
			p+=q*s;
		}
		printf("%.16f\n",p);
	}
	return 0;
}
