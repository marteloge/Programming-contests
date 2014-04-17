/* solution by ruben spaans
   immediate calculation of answer. */
#include <stdio.h>

int main() {
  int cases,n,c;
  scanf("%d",&cases);
  while(cases--) scanf("%d %d",&n,&c),printf("%d\n",(n+c-1)/c);
  return 0;
}
