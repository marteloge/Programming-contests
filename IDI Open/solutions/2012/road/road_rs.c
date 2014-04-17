/*
		solution by ruben spaans
		dijkstra in the transformed graph where each node consists of
		the tuplet (first node,second node,side_first,side_second).
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

#define MAX 102
#define EPS 1e-7

double pi;

double dist2d(double x1,double y1,double x2,double y2) {
	double dx=x1-x2,dy=y1-y2;
	return sqrt(dx*dx+dy*dy);
}

void twocircletangentsub(double x1,double y1,int r1,double x2,double y2,int r2,double *tx1,double *ty1,double *tx2,double *ty2) {
	double a,b,d=dist2d(x1,y1,x2,y2),x=x2-x1,y=y2-y1,R,X,Y;
	int r=r2-r1;
	X=x/d; Y=y/d; R=r/d;
	/*	calculate a,b,c so that line is ax+by+c=0 */
	a=R*X-Y*sqrt(1-R*R);
	b=R*Y+X*sqrt(1-R*R);
	/*	follow the line's normal from the circle centres */
//	printf("(r %d %d) line is %f %f\n",r1,r2,a,b);
	*tx1=x1-a*r1;
	*ty1=y1-b*r1;
	*tx2=x2-a*r2;
	*ty2=y2-b*r2;
}

/*	given two circles and sides (0:left, 1:right, side1:first circle,
		side 2:last circle), find their tangent and return the two points
		where the tangent touches the circle in (x1,y1) and (x2,y2) */
/*	http://en.wikipedia.org/wiki/Tangent_lines_to_circles */
void twocircletangent(double x1,double y1,int r1,double x2,double y2,int r2,int side1,int side2,double *tx1,double *ty1,double *tx2,double *ty2) {
	if(side1+side2==2) return twocircletangentsub(x1,y1,r1,x2,y2,r2,tx1,ty1,tx2,ty2);
	else if(side1+side2==0) twocircletangentsub(x2,y2,r2,x1,y1,r1,tx2,ty2,tx1,ty1);
	else if(side2==1) twocircletangentsub(x1,y1,-r1,x2,y2,r2,tx1,ty1,tx2,ty2);
	else if(side1==1) twocircletangentsub(x1,y1,r1,x2,y2,-r2,tx1,ty1,tx2,ty2);
}
/*	return 1 if line intersects or touches circle, 0 otherwise */
/*	algorithm shamelessly borrowed from http://stackoverflow.com/questions/1073336/circle-line-collision-detection */
int linecircleintersect(double x1,double y1,double x2,double y2,double cx,double cy,int r) {
	double dx=x2-x1,dy=y2-y1;
	double fx=x1-cx,fy=y1-cy;
	double a=dx*dx+dy*dy;
	double b=2*(fx*dx+fy*dy);
	double c=fx*fx+fy*fy-r*r;
	double D=b*b-4*a*c;
	double t1,t2;
	/*	no collision */
	if(D<0) return 0;
	/*	check if line segment actually intersects or touches */
	D=sqrt(D);
	t1=(-b+D)/(2*a);
	t2=(-b-D)/(2*a);
	return (t1>=0 && t1<=1) || (t2>=0 && t2<=1);
}

/*	distance of tangents from circle i to circle j, along with their
		tangent points, or -1 if direct path obstructed */
/*	node 0 and 1 is start and goal! */
/*	[i][j][k][l]: i:side1 (left/right), j:side2, k=source circle, l=dest circle */
double dist[2][2][MAX][MAX];
double tx1[2][2][MAX][MAX];
double ty1[2][2][MAX][MAX];
double tx2[2][2][MAX][MAX];
double ty2[2][2][MAX][MAX];
/*	angles of touching points with regard to the circle center */
double theta1[2][2][MAX][MAX];
double theta2[2][2][MAX][MAX];

double circlex[MAX],circley[MAX];
int circler[MAX];
int L,W,n;

double getangle(double x,double y,double cx,double cy) {
	if(fabs(x-cx)<EPS && fabs(y-cy)<EPS) return 0;
	return atan2(y-cy,x-cx);
}

/*	given two points on a circle (given by angles), find shortest path
		on circle */
double arclength(int r,double a1,double a2) {
	double diff=a2-a1;
	while(diff<0) diff+=2*pi;
	while(diff>=2*pi) diff-=2*pi;
	if(diff>=pi) diff=2*pi-diff;
	return diff*r;
}

/*	heap for dijkstra */
#define MAXH 65536*200
double heap[MAXH];   /*  heap value (on which it is sorted)  */
int heapid[MAXH]; /*  heap id */
int heapn;        /*  (number of elements in heap)+1 */

/*  insert a new value to the heap */
/*  warning, function will choke if heap is full */
void heapinsert(double val,int id) {
  int pos=heapn++;
  while(pos>1 && val<heap[pos>>1]) {
    heap[pos]=heap[pos>>1];
    heapid[pos]=heapid[pos>>1];
    pos>>=1;
  }
  heap[pos]=val; heapid[pos]=id;
}

/*  remove a value from the heap and put it in *val,*id */
void heapremove(double *val,int *id) {
  int pos=1,next=1,newid;
	double newval;
  *val=heap[1]; *id=heapid[1];
  newval=heap[--heapn];
  newid=heapid[heapn];
  while((pos<<1)<=heapn) {
    /*  take lowest of left and right children */
    if((pos<<1)==heapn) next=pos<<1;
    else next=(pos<<1)+(heap[pos<<1]>heap[(pos<<1)+1]);
    if(heap[next]>=newval) break;
    heap[pos]=heap[next];
    heapid[pos]=heapid[next];
    pos=next;
  }
  heap[pos]=newval;
  heapid[pos]=newid;
}

#define MAXV 65536
#define INF 1e100
/*	node id:	bit 0-6:from, bit 7-13: to, bit 13: side from, bit 14: side to */
double dijkstra() {
  static double D[MAXV];    /*  array of distances */
  static char vis[MAXV];    /*  array indicating visited (processed) nodes */
	double val,w;
  int cur,i,x2;
	int start=0;
	int curfrom,curto,next;
	int sidefrom,sideto,side;
  memset(vis,0,MAXV);
	for(i=0;i<MAXV;i++) D[i]=INF;
  D[start]=0;              /*  set distance to start to 0 */
  heapn=1;
  heapinsert(0,start);        /*  insert start node to heap */
  while(heapn>1) {
    heapremove(&val,&cur);
    if(vis[cur]) continue;    /*  if current node is already done: continue */
		curfrom=cur&127; curto=(cur>>7)&127;
		sidefrom=(cur>>14)&1; sideto=cur>>15;
//		printf("at %d %d %d %d: %f %f\n",sidefrom,sideto,curfrom,curto,D[cur],val);
		if(curto==1) return val;	/*	done! */
    vis[cur]=1;
		for(side=0;side<2;side++) for(next=1;next<n;next++) if(dist[sideto][side][curto][next]>-.5) {
			if(side==1 && next==1) continue;
			x2=curto+(next<<7)+(sideto<<14)+(side<<15);
			if(vis[x2]) continue;
			w=val+dist[sideto][side][curto][next];
			w+=arclength(circler[curto],theta2[sidefrom][sideto][curfrom][curto],
				theta1[sideto][side][curto][next]);
//			printf("go to %d %d %d %d, cur %f + arc %f + line %f = %f\n",sideto,side,curto,next,val,arclength(circler[curto],theta2[sidefrom][sideto][curfrom][curto],theta1[sideto][side][curto][next]),dist[sideto][side][curto][next],w);
			if(D[x2]>w) {
				D[x2]=w;
				if(heapn==MAXH) puts("ERROR");
				heapinsert(w,x2);
			}
		}
  }
	puts("error, no path");
  return 0;
}

double solve() {
	int i,j,k,l,m;
	double x1,y1,x2,y2;
	/*	calculate distances and tangent points for each circle pair */
	for(i=0;i<2;i++) for(j=0;j<2;j++) for(k=0;k<n;k++) for(l=0;l<n;l++)
		dist[i][j][k][l]=-1;
	memset(theta1,0,sizeof(theta1));
	memset(theta2,0,sizeof(theta2));
	for(i=0;i<2;i++) for(j=0;j<2;j++) for(k=0;k<n;k++) for(l=0;l<n;l++) if(k!=l) {
		if(k==1) continue;
		if((k<2 && i) || (l<2 && j)) continue;
		twocircletangent(circlex[k],circley[k],circler[k],
			circlex[l],circley[l],circler[l],i,j,&x1,&y1,&x2,&y2);
		/*	for each other circle: check if it collides */
		for(m=0;m<n;m++)
			if(m!=k && m!=l && linecircleintersect(x1,y1,x2,y2,
				circlex[m],circley[m],circler[m])) goto fail;
//		printf("ix (%d %d) %d %d\n",i,j,k,l);printf("from circle (%.1f %.1f %d) to (%.1f %.1f %d): (%f %f) (%f %f)\n",circlex[k],circley[k],circler[k],circlex[l],circley[l],circler[l],x1,y1,x2,y2);printf("dist %.10f\n",dist2d(x1,y1,x2,y2));
		dist[i][j][k][l]=dist2d(x1,y1,x2,y2);
		tx1[i][j][k][l]=x1;
		ty1[i][j][k][l]=y1;
		tx2[i][j][k][l]=x2;
		ty2[i][j][k][l]=y2;
		theta1[i][j][k][l]=getangle(x1,y1,circlex[k],circley[k]);
		theta2[i][j][k][l]=getangle(x2,y2,circlex[l],circley[l]);
	fail:;
	}
//	for(i=0;i<2;i++) for(j=0;j<2;j++) for(k=0;k<n;k++) for(l=0;l<n;l++) if(dist[i][j][k][l]>-.5) {printf("%d %d %d %d: %f (%.3f %.3f) (%.3f %.3f) (%.2f %.2f)\n",i,j,k,l,dist[i][j][k][l],tx1[i][j][k][l],ty1[i][j][k][l],tx2[i][j][k][l],ty2[i][j][k][l],theta1[i][j][k][l],theta2[i][j][k][l]);}
	return dijkstra();
}

int main() {
	int T,i;
	pi=2*acos(0);
	scanf("%d",&T);
	while(T--) {
		scanf("%d %d %d",&n,&W,&L);
		n+=2;
		circlex[0]=circlex[1]=W*.5;
		circley[0]=0;
		circley[1]=L;
		circler[0]=circler[1]=0;
		for(i=2;i<n;i++) scanf("%lf %lf %d",&circlex[i],&circley[i],&circler[i]);
		printf("%.15f\n",solve());
	}
	return 0;
}
