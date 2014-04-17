/* solution by ruben spaans
   ternary search over the minimal collision time, using an (almost) convex
   function f representing the euclidean distance between the two rectangles.
   there can exist several t such that f(t)=0, our search finds the lowest
   such t.
*/
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int y[2],x[2],x1[2],y1_[2],x2[2],y2[2],dx[2],dy[2];
#define EPS 1e-9 /* this might need to be increased for hard test cases */
#define MIN(a,b) ((a)<(b)?(a):(b))
#define MAX(a,b) ((a)>(b)?(a):(b))
double d2(double x1,double y1,double x2,double y2) {
  return (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1);
}
/* return the distance between the rectangles at time t */
double dist(double t) {
  double xl,yl,xr,yr,xpos[2],ypos[2],a,b,c,d;
  int i;
  for(i=0;i<2;i++) xpos[i]=x1[i]+dx[i]*t,ypos[i]=y1_[i]+dy[i]*t;
  xl=MAX(xpos[0],xpos[1]);
  xr=MIN(xpos[0]+x[0],xpos[1]+x[1]);
  yl=MAX(ypos[0],ypos[1]);
  yr=MIN(ypos[0]+y[0],ypos[1]+y[1]);
  if(xl<=xr && yl<=yr) return 0;
  if(xpos[0]>xpos[1]+x[1] && ypos[0]>ypos[1]+y[1]) return d2(xpos[0],ypos[0],xpos[1]+x[1],ypos[1]+y[1]);
  if(xpos[0]+x[0]<xpos[1] && ypos[0]>ypos[1]+y[1]) return d2(xpos[0]+x[0],ypos[0],xpos[1],ypos[1]+y[1]);
  if(xpos[0]>xpos[1]+x[1] && ypos[0]+y[0]<ypos[1]) return d2(xpos[0],ypos[0]+y[0],xpos[1]+x[1],ypos[1]);
  if(xpos[0]+x[0]<xpos[1] && ypos[0]+y[0]<ypos[1]) return d2(xpos[0]+x[0],ypos[0]+y[0],xpos[1],ypos[1]);
  a=ypos[0]; b=ypos[0]+y[0]; c=ypos[1]; d=ypos[1]+y[1];
  if((xpos[1]>=xpos[0] && xpos[1]<=xpos[0]+x[0]) ||
    (xpos[1]+x[1]>=xpos[0] && xpos[1]+x[1]<=xpos[0]+x[0]) ||
    (xpos[0]>=xpos[1] && xpos[0]<=xpos[1]+x[1]) ||
    (xpos[0]+x[0]>=xpos[1] && xpos[0]+x[0]<=xpos[1]+x[1])) 
      return MIN(MIN(fabs(a-c),fabs(a-d)),MIN(fabs(b-c),fabs(b-d)));
  a=xpos[0]; b=xpos[0]+x[0]; c=xpos[1]; d=xpos[1]+x[1];
  if((ypos[1]>=ypos[0] && ypos[1]<=ypos[0]+y[0]) ||
    (ypos[1]+y[1]>=ypos[0] && ypos[1]+y[1]<=ypos[0]+y[0]) ||
    (ypos[0]>=ypos[1] && ypos[0]<=ypos[1]+y[1]) ||
    (ypos[0]+y[0]>=ypos[1] && ypos[0]+y[0]<=ypos[1]+y[1])) 
      return MIN(MIN(fabs(a-c),fabs(a-d)),MIN(fabs(b-c),fabs(b-d)));
  /* panic (debug) */
  printf("error 1: (%f %f)-(%f %f), 2: (%f %f)-(%f %f)\n",
    xpos[0],ypos[0],xpos[0]+x[0],ypos[0]+y[0],
    xpos[1],ypos[1],xpos[1]+x[1],ypos[1]+y[1]);
  exit(1);
}
int main() {
  int cases,i;
  double t1,t2,t3,t4;
  scanf("%d",&cases);
  while(cases--) {
    for(i=0;i<2;i++) scanf("%d %d %d %d %d %d",&x[i],&y[i],
      &x1[i],&y1_[i],&x2[i],&y2[i]),dx[i]=x2[i]-x1[i],dy[i]=y2[i]-y1_[i];
    /* ternary search to find the time t where the distance between
       the rectangles is minimal */
    for(t1=i=0,t4=1;i<200;i++) {
      t2=(t4-t1)/3+t1;
      t3=(t4-t1)/1.5+t1;
      if(dist(t2)<=dist(t3)) t4=t3;
      else t1=t2;
    }
    if(dist(t1)<EPS) {
      printf("%.15f\n",t1);
    } else printf("No Collision\n");
  }
  return 0;
}
