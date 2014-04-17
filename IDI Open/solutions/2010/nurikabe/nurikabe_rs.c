/* solution by ruben spaans
   checks if a board satisfies every rule. bfs from a water cell,
   bfs from each numbered cell, and check for 2x2 water.
   runtime: O(N*M). */
#include <stdio.h>
#include <ctype.h>
char b[64][64];
int q[8192],qs,qe,dx[]={1,0,-1,0},dy[]={0,1,0,-1};
int main() {
  int cases,i,j,k,x,y,ok,x2,y2,x1,y1,d;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%d %d",&x,&y);
    for(i=0;i<x;i++) scanf("%s",b[i]);
    ok=0;
    /* check for 2x2 */
    for(i=0;i<x-1;i++) for(j=0;j<y-1;j++)
      if(b[i][j]=='#' && b[i+1][j]=='#' && b[i][j+1]=='#' && b[i+1][j+1]=='#') goto theend;
    /* check if all # are connected */
    for(i=0;i<x;i++) for(j=0;j<y;j++) if(b[i][j]=='#') {
      qe=qs=0;
      q[qe++]=i; q[qe++]=j;
      b[i][j]=0;
      while(qs-qe) {
        x1=q[qs++],y1=q[qs++];
        for(k=0;k<4;k++) {
          x2=x1+dx[k],y2=y1+dy[k];
          if(x2<0 || y2<0 || x2>=x || y2>=y || b[x2][y2]!='#') continue; 
          b[x2][y2]=0;
          q[qe++]=x2; q[qe++]=y2;
        }
      }
      for(i=0;i<x;i++) for(j=0;j<y;j++) if(b[i][j]=='#') goto theend;
      goto next;
    }
  next:
    /* check each island: it must have correct size and exactly one number */
    for(i=0;i<x;i++) for(j=0;j<y;j++) if(isdigit(b[i][j])) {
      d=b[i][j]-'1';
      qe=qs=0;
      q[qe++]=i; q[qe++]=j;
      b[i][j]=0;
      while(qs-qe) {
        x1=q[qs++],y1=q[qs++];
        for(k=0;k<4;k++) {
          x2=x1+dx[k],y2=y1+dy[k];
          if(x2<0 || y2<0 || x2>=x || y2>=y) continue; 
          if(isdigit(b[x2][y2])) goto theend; /* two digits in same island */
          if(b[x2][y2]!='.') continue;
          b[x2][y2]=0;
          q[qe++]=x2; q[qe++]=y2;
          d--;
        }
      }
      /* check if island has correct size */
      if(d) goto theend;
    }
    /* check for island with no numbers */
    for(i=0;i<x;i++) for(j=0;j<y;j++) if(b[i][j]=='.') goto theend;
    ok=1;
  theend:
    puts(ok?"YES":"NO");
  }
  return 0;
}
