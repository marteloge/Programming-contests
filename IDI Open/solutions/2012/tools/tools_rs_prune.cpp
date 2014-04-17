/*
	solution by ruben spaans, slow handling of partitions
	O(N^2*K), but uses pruning
*/

#include <cstdio>
#include <cstring>
#include <map>
#include <string>
using namespace std;

#define MAX 1024
#define MOD 2147483647
typedef long long ll;
int pascal[MAX+1][MAX+1];

void genpascal() {
	int i,j;
	for(i=0;i<=MAX;i++) {
		pascal[i][0]=pascal[i][i]=1;
		for(j=1;j<i;j++) pascal[i][j]=(int)(((ll)pascal[i-1][j-1]+pascal[i-1][j])%MOD);
	}
}

int renumber(int *set,int n) {
	int i,j,k=0;
	static char exist[2*MAX+2];
	memset(exist,0,sizeof(exist));
	for(i=0;i<n;i++) exist[set[i]]=1;
	for(i=0;i<2*MAX+2;i++) if(exist[i]) {
		for(j=0;j<n;j++) if(set[j]==i) set[j]=k;
		k++;
	}
	return k;
}

map<string,int> name;
int n;

int set[MAX];
int tell[MAX+1];
int pick[MAX+1];
int subset[MAX];

int main() {
	int T,k,i,j,nn,changed,oldnn,m;
	ll ans,mul;
	char s[64];
	genpascal();
	scanf("%d",&T);
	while(T--) {
		scanf("%d %d",&n,&k);
		name.clear();
		for(i=0;i<n;i++) {
			scanf("%s",s);
			name[s]=i;
			set[i]=0;
		}
		ans=0; nn=1;
		while(k--) {
			scanf("%d",&m);
			for(i=0;i<m;i++) {
				scanf("%s",s);
				subset[i]=name[s];
			}
			if(nn==n) {
				ans++;
				continue;
			}
			memset(tell,0,(n+1)*sizeof(int));
			memset(pick,0,n*sizeof(int));
			for(i=0;i<n;i++) tell[set[i]]++;
			for(i=0;i<m;i++) pick[set[subset[i]]]++;
			mul=1;
			for(i=0;i<=n;i++) if(pick[i]) mul=((ll)mul*pascal[tell[i]][pick[i]])%MOD;
			oldnn=nn;
			for(i=0;i<oldnn;i++) {
				changed=0;
				for(j=0;j<m;j++) if(set[subset[j]]==i) changed=1,set[subset[j]]=nn;
				if(changed) nn++;
			}
			nn=renumber(set,n);
			ans=(ans+mul)%MOD;
		}
		printf("%d\n",(int)ans);
	}
}
