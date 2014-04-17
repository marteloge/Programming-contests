/* solution by ruben spaans
   sum all input values and check the sign of the sum. */
#include <stdio.h>
int main() {
  int cases,n,w,a;
  scanf("%d",&cases);
  while(cases--) {
    a=0;
    scanf("%d",&n);
    while(n--) scanf("%d",&w),a+=w;
    puts(a<0?"Left":(a?"Right":"Equilibrium"));
  }
  return 0;
}
