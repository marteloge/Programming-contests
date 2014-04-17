/*
	solution by ruben spaans
	precalculating, smart backtracking
	slower isprime()
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

#if defined(__MINGW32__) || defined(__MINGW64__)
	#define LONGLONG "%I64d"
#else
	#define LONGLONG "%lld"
#endif
#define MAXP 1000020
typedef long long ll;
typedef unsigned long long ull;
typedef unsigned int uint;

char sieve[MAXP];
int prime[80000];
int primes;

#define MAXF 90000
int prod[MAXF],sumdiv[MAXF],prim[MAXF];
int sn;

void createsieve() {
  int i,j,q;
  memset(sieve,1,sizeof(sieve));
  q=sqrt(MAXP);
  for(sieve[0]=sieve[1]=0,i=2;i<q;i++) if(sieve[i])
    for(j=i*i;j<MAXP;j+=i) sieve[j]=0;
}

void genprimes() {
	int i;
	for(primes=i=0;i<MAXP;i++) if(sieve[i]) prime[primes++]=i;
}

#define MAXN 1000020
void genfactors() {
	int i,nevner;
	ll teller;
	for(sn=i=0;i<primes;i++) {
		teller=(ll)prime[i]*prime[i];
		nevner=prime[i]-1;
		while((teller-1)/nevner<=MAXN) {
			prod[sn]=teller/prime[i];
			sumdiv[sn]=(teller-1)/nevner;
			prim[sn++]=prime[i];
			teller*=prime[i];
		}
	}
}

int isprime(ull n) {
	int i;
	for(i=0;i<primes && (ll)prime[i]*prime[i]<n;i++)
		if(n%prime[i]<1) return 0;
	return 1;
}

int list[MAXF],listf[MAXF],listp[MAXF];
int ln;

ll ans[1000000];
int nn;

int compi(const void *A,const void *B) {
	const ll *a=A,*b=B;
	if(*a<*b) return -1;
	if(*a>*b) return 1;
	return 0;
}

void btr(int ix,int at,ll orinum,ll divsum) {
	if(divsum<2) { ans[nn++]=orinum; return; }
	if(divsum-1>=MAXP && divsum-1>at && isprime(divsum-1))
		ans[nn++]=orinum*(divsum-1);
	for(;ix<ln;ix++) if(listp[ix]>at && divsum%listf[ix]<1)
		btr(ix+1,listp[ix],orinum*list[ix],divsum/listf[ix]);
}

int main() {
	int T,i;
	ull n;
	char s[32];
	createsieve();
	genprimes();
	genfactors();
	scanf("%d",&T);
	while(T--) {
		scanf("%s",s);
		for(n=i=0;s[i];i++) n=n*10+s[i]-48;
		for(ln=i=0;i<sn;i++) if(n%sumdiv[i]<1)
			list[ln]=prod[i],listf[ln]=sumdiv[i],listp[ln++]=prim[i];
		nn=0;
		btr(0,0,1,n);
		qsort(ans,nn,sizeof(ll),compi);
		if(!nn) puts("none!");
		else for(i=0;i<nn;i++) printf(LONGLONG"%c",ans[i],i==nn-1?'\n':' ');
	}
	return 0;
}
