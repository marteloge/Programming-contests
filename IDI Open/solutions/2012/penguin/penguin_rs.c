/*
		warning, only tested on sample input
*/

#include <stdio.h>
#include <string.h>

typedef unsigned char uchar;

uchar maze[1024][1024];
uchar press[1024][1024];
int q[2097152],qs,qe;
int parent[1024][1024];
char vis[1024][1024];
char took[1024][1024];

int x,y;

char s[1024];

int dx[]={1,0,-1,0};
int dy[]={0,-1,0,1};

char sol[1048576];
char dirs[]="ENWS";

void solve(int sx,int sy,int gx,int gy) {
	int curx,cury,x2,y2,d,v;
	memset(vis,0,sizeof(vis));
	memset(parent,-1,sizeof(parent));
	qs=qe=0;
	q[qe++]=sx,q[qe++]=sy;
	vis[sx][sy]=1;
	press[sx][sy]=0;
	took[sx][sy]=48;
	if(sx==gx && sy==gy) goto done;
	while(qs<qe) {
		curx=q[qs++]; cury=q[qs++];
		for(d=0;d<4;d++) if(maze[curx][cury]&(1<<d)) {
			x2=curx+dx[d];
			y2=cury+dy[d];
			if(vis[x2][y2]) continue;
			vis[x2][y2]=1;
			parent[x2][y2]=curx+(cury<<10);
			took[x2][y2]=dirs[(press[curx][cury]+d)&3];
			if(x2==gx && y2==gy) goto done;
			press[x2][y2]=(press[curx][cury]+((maze[x2][y2]&16)>0))&3;
			q[qe++]=x2; q[qe++]=y2;
		}
	}
done:
	d=0;
	while(parent[gx][gy]>-1) {
		sol[d++]=took[gx][gy];
		v=parent[gx][gy];
		gx=v&1023;
		gy=v>>10;
	}
	while(d) putchar(sol[--d]);
	putchar('\n');
}

uchar convert(char c) {
	if(c>='0' && c<='9') return c-'0';
	return c+10-'A';
}

int main() {
	int T,sx,sy,gx,gy,i,j;
	scanf("%d",&T);
	while(T--) {
		scanf("%d %d %d %d %d %d",&x,&y,&sx,&sy,&gx,&gy);
		for(i=0;i<y;i++) {
			scanf("%s",s);
			for(j=0;j<x;j++) maze[j][i]=convert(s[j]);
		}
		solve(sx,sy,gx,gy);
	}
	return 0;
}
