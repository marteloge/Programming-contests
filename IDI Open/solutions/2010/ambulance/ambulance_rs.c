/* solution by ruben spaans
   try all combinations of picking up 1, 2 or 3 patients for each state, where
   a state is a bitmask over all nodes, each bit indicates whether the patient
   as this position has been picked up or not. with memoization, and by always
   taking the first available patient, the runtime is O(2^n n^2). */
#include <stdio.h>
#include <string.h>

#define INF 1000000000

int f[32][32];
int dp[1048576];
int n,m;

int btr(int mask) {
   int i,j,k,res=INF,cur,r;

   if(dp[mask]>-1) return dp[mask];
   if(mask==0) return dp[mask]=0;
   for(i=0;!(mask&(1<<i));i++);
   /* carry one patient */
   res=f[n][i]*2+btr(mask-(1<<i));
   /* carry two patients */
   for(j=i+1;j<n;j++) if(mask&(1<<j)) {
      cur=f[n][i]+f[i][j]+f[j][n]+btr(mask-(1<<i)-(1<<j));
      if(res>cur) res=cur;
      /* carry three patients */
      for(k=j+1;k<n;k++) if(mask&(1<<k)) {
         /* try all permutations of pickup order up to mirror: ijk ikj jik */
         r=btr(mask-(1<<i)-(1<<j)-(1<<k));
         cur=f[n][i]+f[i][j]+f[j][k]+f[k][n]+r;
         if(res>cur) res=cur;
         cur=f[n][i]+f[i][k]+f[k][j]+f[j][n]+r;
         if(res>cur) res=cur;
         cur=f[n][j]+f[j][i]+f[i][k]+f[k][n]+r;
         if(res>cur) res=cur;
      }
   }
   return dp[mask]=res;
}

int main() {
   int i,j,k,a,b,c,cases;

   scanf("%d",&cases);
   while(cases--) {
      scanf("%d %d",&n,&m);
      for(i=0;i<=n;i++) for(j=0;j<=n;j++) f[i][j]=INF;
      for(i=0;i<m;i++) {
         scanf("%d %d %d",&a,&b,&c);
         f[a][b]=f[b][a]=c;
      }
      for(k=0;k<=n;k++) for(i=0;i<=n;i++) for(j=0;j<=n;j++) if(f[i][j]>f[i][k]+f[k][j]) f[i][j]=f[i][k]+f[k][j];
      memset(dp,-1,sizeof(int)*(1<<n));
      printf("%d\n",btr((1<<n)-1));
   }
   return 0;
}
