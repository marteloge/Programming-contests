/*
	solution by ruben spaans
	backtracking with no pruning, trying all divisors blindly.
	this solution should time out.
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

#define MAX 1000000000000LL
#define MAXP 1000020
typedef long long ll;
typedef unsigned long long ull;
typedef unsigned int uint;

char sieve[MAXP];
int prime[80000];
int primes;

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

int isprime(ull n) {
	int i;
	for(i=0;i<primes && (ll)prime[i]*prime[i]<n;i++)
		if(n%prime[i]<1) return 0;
	return 1;
}

ll ans[1000000];
int nn;

void btr(int at,ll orinum,ll divsum) {
	int nevner;
	ll teller,z,p;
	if(divsum-1>=MAXP && isprime(divsum-1)) ans[nn++]=orinum*(divsum-1);
	if(divsum==1) { ans[nn++]=orinum; return; }
	teller=(ll)prime[at]*prime[at];
	nevner=prime[at]-1;
	if((teller-1)/nevner>divsum) return;
	for(;at<primes;at++) {
		teller=(ll)prime[at]*prime[at];
		nevner=prime[at]-1;
		if((teller-1)/nevner>divsum) break;
		p=prime[at];
		while((teller-1)/nevner<=MAX) {
			z=(teller-1)/nevner;
			if(divsum%z<1) btr(at+1,orinum*p,divsum/z);
			teller*=prime[at];
			p*=prime[at];
		}
	}
}

int compi(const void *A,const void *B) {
	const ll *a=A,*b=B;
	if(*a<*b) return -1;
	if(*a>*b) return 1;
	return 0;
}

int main() {
	int T,i;
	ull n;
	char s[32];
	createsieve();
	genprimes();
	scanf("%d",&T);
	while(T--) {
		scanf("%s",s);
		for(n=i=0;s[i];i++) n=n*10+s[i]-48;
		nn=0;
		btr(0,1,n);
		qsort(ans,nn,sizeof(ll),compi);
		if(!nn) puts("none!");
		else for(i=0;i<nn;i++) printf(LONGLONG"%c",ans[i],i==nn-1?'\n':' ');
	}
	return 0;
}
