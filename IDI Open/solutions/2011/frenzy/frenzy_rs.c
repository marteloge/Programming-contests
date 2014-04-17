/*
  O(2^n n^2):
  - first find all pairwise distances between starting position and sheep
    positions
  - then try all paths, memoizing with a bitmask of visited positions and
    the current position
*/

#include <stdio.h>
#include <string.h>
#define INF 1000000
#define SHEEP 16
#define H 50
#define W 50
int x,y;
int posx[SHEEP+1],posy[SHEEP+1];
int dist[SHEEP+1][SHEEP+1];
int id[H][W];
int n;
char m[H][W+1];

int dx[]={1,0,-1,0},dy[]={0,1,0,-1};
int q[H*W*2],qs,qe;
int vis[H][W];
void bfs(int no,int startx,int starty) {
  int i,curx,cury,move,x2,y2;
  for(i=0;i<n;i++) dist[no][i]=INF;
  qs=qe=0;
  q[qe++]=startx; q[qe++]=starty;
  memset(vis,-1,sizeof(vis));
  vis[startx][starty]=0;
  while(qs<qe) {
    curx=q[qs++]; cury=q[qs++];
    move=vis[curx][cury]+1;
    for(i=0;i<4;i++) {
      x2=curx+dx[i]; y2=cury+dy[i];
      if(x2<0 || y2<0 || x2>=x || y2>=y || vis[x2][y2]>-1 || m[x2][y2]=='X')
        continue;
      if(m[x2][y2]!='.') dist[no][id[x2][y2]]=move;
      vis[x2][y2]=move;
      q[qe++]=x2; q[qe++]=y2;
    }
  }
}

int pairwise() {
  int i,j;
  for(i=0;i<n;i++) bfs(i,posx[i],posy[i]);
  for(i=1;i<n;i++) for(j=0;j<i;j++) if(dist[i][j]==INF) return 0;
  return 1;
}

int dp[SHEEP+1][1<<SHEEP];
int go(int at,int mask) {
  int i,cur;
  if(dp[at][mask]>-1) return dp[at][mask];
  int res=INF;
  if(at && mask==(1<<(n-1))-1) return dp[at][mask]=0;
  for(i=1;i<n;i++) if(!(mask&(1<<(i-1)))) {
    cur=dist[at][i]+go(i,mask|(1<<(i-1)));
    if(res>cur) res=cur;
  }
  return dp[at][mask]=res;
}

void solve() {
  int i,j;
  n=1;
  scanf("%d %d",&x,&y);
  for(i=0;i<x;i++) scanf("%s",m[i]);
  memset(id,-1,sizeof(id));
  for(i=0;i<x;i++) for(j=0;j<y;j++) {
    if(m[i][j]=='U') posx[0]=i,posy[0]=j,id[i][j]=0;
    else if(m[i][j]=='#') posx[n]=i,posy[n]=j,id[i][j]=n++;
  }
  if(!pairwise()) puts("impossible");
  else {
    memset(dp,-1,sizeof(dp));
    printf("%d\n",go(0,0)+n-1);
  }
}

int main() {
  int cases;
  scanf("%d",&cases);
  while(cases--) solve();
  return 0;
}
