/*  solution by ruben spaans
    dynamic programming, O(N M R)
*/

#include <stdio.h>

/* dp: number of segments slept, number of segments slept in a row,
   elements considered so far */
int dp[128][64][1024];
int r[1024];

int main()
{
   int cases,n,m,t,i,j,k,cur;

   scanf("%d",&cases);
   while(cases--) {
      scanf("%d %d %d",&n,&m,&t);
      for(i=0;i<n;i++) scanf("%d",&r[i]);
      for(i=0;i<=m;i++) for(j=0;j<=t;j++) for(k=0;k<=n;k++) dp[i][j][k]=-1;
      dp[0][0][0]=0;
      for(i=0;i<n;i++) for(j=0;j<=m;j++) for(k=0;k<=t;k++) if(dp[j][k][i]>-1) {
         /* two decisions: sleep or not sleep through segment i */
         if(j<m && k<t) {
            /* can only sleep if we need more sleep and if the boss won't notice */
            cur=dp[j][k][i]+(k+1)*r[i];
            if(dp[j+1][k+1][i+1]<cur) dp[j+1][k+1][i+1]=cur;
         }
         if(dp[j][0][i+1]<dp[j][k][i]) dp[j][0][i+1]=dp[j][k][i];
      }
      for(j=-1,i=0;i<=t;i++) if(j<dp[m][i][n]) j=dp[m][i][n];
      if(j<0) puts("impossible");
      else printf("%d\n",j);
   }
   return 0;
}
