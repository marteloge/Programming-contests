/* solution by ruben spaans
   one bfs for all bacteria, one bfs for each bee. given distances from
   bacteria and bees to safe cells, build a graph which has an edge from a
   bee to a safe cell if and only if the bee can escape to that safe cell
   without being infected by bacteria. then, do bipartite matching on this
   graph (done using a general maxflow algorithm). */
#include <stdio.h>
#include <string.h>
#define MAX 50
#define MAXB 50
#define MAXV 1500
#define MAXG 600
#define M (MAXG/2)
#define INF 1000000
int bees,beex[MAXB],beey[MAXB];
int safe,safex[MAX],safey[MAX];
int bact,bactx[MAXV],bacty[MAXV];
int bd[MAXG][MAXG]; /* mark safehouses */
int beefs[MAXG][MAXG];
int dx[]={0,1, 1, 0,-1,-1},
    dy[]={1,0,-1,-1, 0, 1};
int poss[MAX][MAXB]; /* matching safehouses - bees */
int dist[MAXG][MAXG]; /* distance to closest virus */
int q[MAXG*MAXG*2],qs,qe;
int last;
void bfsbact() {
  int i,b,x,y,m,x2,y2;
  memset(dist,-1,sizeof(dist));
  last=qs=qe=0;
  for(b=0;b<safe;b++) dist[safex[b]][safey[b]]=-2;
  for(b=0;b<bact;b++) {
    q[qe++]=bactx[b]; q[qe++]=bacty[b];
    dist[bactx[b]][bacty[b]]=0;
  }
  while(qs<qe) {
    x=q[qs++],y=q[qs++];
    m=dist[x][y]+2;
    for(i=0;i<6;i++) {
      x2=x+dx[i]; y2=y+dy[i];
      if(x2<0 || y2<0 || x2>=MAXG || y2>=MAXG || dist[x2][y2]>-1) continue;
      if(dist[x2][y2]==-2) last=m;
      dist[x2][y2]=m;
      q[qe++]=x2; q[qe++]=y2;
    }
  }
  printf("dist from bact to %d,%d: %d\n",0,4,dist[0+M][4+M]);
  printf("dist from bact to %d,%d: %d\n",-1,3,dist[-1+M][3+M]);
}
void bfsbee() {
  int bee,i,x,y,x2,y2,m;
  memset(bd,-1,sizeof(bd));
  memset(beefs,-1,sizeof(beefs));
  /* mark safehouses */
  for(i=0;i<safe;i++) bd[safex[i]][safey[i]]=-1000+i;
  for(bee=0;bee<bees;bee++) {
    /* starting on virus? */
    if(dist[beex[bee]][beey[bee]]==0) continue;
    /* starting on safe square? */
    if(bd[beex[bee]][beey[bee]]<-1) {
      poss[bd[beex[bee]][beey[bee]]+1000][bee]=1; /* edge */
    }
    qs=qe=0;
    q[qe++]=beex[bee]; q[qe++]=beey[bee];
    beefs[beex[bee]][beey[bee]]=0;
    while(qs<qe) {
      x=q[qs++]; y=q[qs++];
      m=beefs[x][y]+1;
      if(m>last) break;
      for(i=0;i<6;i++) {
        x2=x+dx[i]; y2=y+dy[i];
        if(x2<0 || y2<0 || x2>=MAXG || y2>=MAXG || beefs[x2][y2]>-1) continue;
        /* check for safehouse */
        if(bd[x2][y2]<-1) {
          if(dist[x2][y2]>=m) {
            poss[bd[x2][y2]+1000][bee]=1; /* edge */
          }
        }
        /* check for bacteria */
        if(dist[x2][y2]<=m) continue;
        q[qe++]=x2; q[qe++]=y2;
        beefs[x2][y2]=m;
      }
    }
    /* clear bd, fast */
    if(bee<bees-1) for(i=0;i<qe;i+=2) beefs[q[i]][q[i+1]]=-1;
  }
}
#define MAKS 102
int f[MAKS][MAKS];
int source,sink,n;
void graf() {
  int i,j;
  n=safe+bees+2;
  source=0;
  sink=n-1;
  memset(f,0,sizeof(f));
  for(i=0;i<safe;i++) f[source][i+1]=1;
  for(i=0;i<safe;i++) for(j=0;j<bees;j++) if(poss[i][j]) f[i+1][j+1+safe]=1;
  for(i=0;i<bees;i++) f[i+safe+1][sink]=1;
}
int maxflow() {
   int i,j,done,flow=0,done2,a,r,parent[MAKS],min[MAKS];
   char t[MAKS];
   do {
      done=1;
      memset(t,0,n);
      for(i=0;i<n;i++) { parent[i]=-1; min[i]=INF; }
      t[source]=1;
      do {
         done2=1;
         for(i=0;i<n;i++) if(t[i]) {
            for(j=0;j<n;j++) { if(t[j] || !f[i][j]) continue;
               a=f[i][j];
               t[j]=1; parent[j]=i; done2=0;
               if(min[i]<a) min[j]=min[i]; else min[j]=a;
               if(j==sink) {
                  done=0; goto out;
               }
            }
         }
      } while(!done2);
      break;
   out:
      i=sink; r=min[sink];
      while(i!=-1) {
         j=parent[i]; if(j==-1) break;
         f[j][i]-=r; f[i][j]+=r;
         i=j;
      }
      flow+=r;
   } while(!done);
   return flow;
}
int main() {
  int cases,i;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%d %d %d",&bees,&safe,&bact);
    for(i=0;i<bees;i++) scanf("%d %d",&beex[i],&beey[i]),beex[i]+=M,beey[i]+=M;
    for(i=0;i<safe;i++) scanf("%d %d",&safex[i],&safey[i]),safex[i]+=M,safey[i]+=M;
    for(i=0;i<bact;i++) scanf("%d %d",&bactx[i],&bacty[i]),bactx[i]+=M,bacty[i]+=M;
    bfsbact();
    memset(poss,0,sizeof(poss));
    bfsbee();
    graf();
    printf("%d\n",maxflow());
  }
  return 0;
}
