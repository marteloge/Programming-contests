/*  solution by ruben spaans.
    uses binary index (fenwick) trees.
    O((p+q) log n) time complexity.
*/
#include <stdio.h>
#include <string.h>
int p,q;
/*  binary index tree code, 1-indexed tree[] */
int tree[100001],n;
/*  initialize structure with n elements */
void deltree() {
  memset(tree,0,sizeof(int)*(n+1));
}
/*  add a to frequency with index ix */
void update(int a,int ix) {
  while(ix<=n) tree[ix]+=a,ix+=(ix&-ix);
}
/*  read cumulative frequency from 1..ix */
int read(int ix) {
  int sum=0;
  while(ix) sum+=tree[ix],ix-=(ix&-ix);
  return sum;
}
int main() {
  int cases,a,b,r;
  char s[2];
  scanf("%d",&cases);
  while(cases--) {
    scanf("%d %d %d",&n,&p,&q);
    r=p+q;
    deltree();
    while(r--) {
      scanf("%s %d %d",s,&a,&b);
      if(s[0]=='P') update(b,a);
      else printf("%d\n",read(b)-read(a-1));
    }
  }
  return 0;
}
