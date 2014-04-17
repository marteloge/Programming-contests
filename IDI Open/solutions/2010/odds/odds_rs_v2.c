/* solution by ruben spaans
   dynamic programming:
   a state is (a,b) where a is the number of battles done so far,
   and b is the current losing streak.
   used a "forward" recurrence:
   given a state (a,b):
   - add DP(a,b) * (1-p) to DP(a+1,b+1)  (loss)
   - add DP(a,b) * p     to DP(a+1,0)    (win)
   and keep a running sum of all states representing the desired losing streak
   (L,L), (L+1,L), ..., (N,L), where L is the losing streak we want. */
#include <stdio.h>
#include <string.h>
double dp[2001][2001]; /* state: battles done, current losing streak */
int main() {
  double p,a;
  int cases,N,L,i,j;
  scanf("%d",&cases);
  while(cases --> 0) {
    scanf("%d %d %lf",&N,&L,&p);
    for(i=0;i<=N;i++) memset(dp[i],0,(N+1)*sizeof(double));
    dp[0][0]=1;
    for(a=i=0;i<N;i++) for(j=0;j<N;j++) if(dp[i][j]>0) {
      dp[i+1][0]+=dp[i][j]*p;
      if(j+2>L) a+=dp[i][j]*(1-p);
      else dp[i+1][j+1]+=dp[i][j]*(1-p);
    }
    printf("%.14f\n",a);
  }
  return 0;
}
