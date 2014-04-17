#include <stdio.h>
#define INF 700000000
int a[201],f[200][200];
int main() {
  int cases,n,i,j,k,z;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%d",&n);
    for(i=0;i<n;i++) scanf("%d",&a[i]);
    for(i=0;i<n;i++) for(j=0;j<n;j++) {
      scanf("%d",&f[i][j]);
      if(f[i][j]<0) f[i][j]=INF;
    }
    for(k=0;k<n;k++) for(i=0;i<n;i++) for(j=0;j<n;j++)
      if(f[i][j]>f[i][k]+f[k][j]) f[i][j]=f[i][k]+f[k][j];
    for(a[n]=a[0],z=i=0;i<n;i++) {
      if(f[a[i]][a[i+1]]==INF) { z=INF; break; }
      z+=f[a[i]][a[i+1]];
    }
    if(z<INF) printf("%d\n",z);
    else puts("impossible");
  }
  return 0;
}
