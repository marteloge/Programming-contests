/* solution by ruben spaans
   binary search over the minimized maximal distance.
   O(N log L). */
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define EPS 1e-13
double x[20001];
int n,m,l,use;
int compd(const void *A,const void *B) {
  double *a=(double *)A,*b=(double *)B;
  if(*a<*b) return -1;
  return 1;
}
int can(double v) {
  int i;
  use=0;
  for(i=0;i<n;i++) if(x[i+1]-x[i]>=v) use+=ceil((x[i+1]-x[i])/v)-1;
  return use<=m;
}
int main() {
  double t1,t2,t3;
  int cases,i;
  x[0]=0;
  scanf("%d",&cases);
  while(cases --> 0) {
    scanf("%d %d %d",&n,&m,&l);
    if(!n) {
      printf("%d %f\n",m,(double)l/m);
      continue;
    }
    for(i=0;i<n;i++) scanf("%lf",&x[i]);
    qsort(x,n,sizeof(double),compd);
    for(i=1;i<n;i++) if(x[i]==x[i-1]) { printf("feil"); exit(1); }
    for(i=1;i<n;i++) x[i]-=x[0];
    x[0]=0;
    x[n]=l;
    for(t3=l,t1=i=0;i<200;i++) {
      t2=(t1+t3)/2;
      if(can(t2)) t3=t2;
      else t1=t2;
    }
    can(t3);
    printf("%.15f\n",t3);
  }
  return 0;
}
