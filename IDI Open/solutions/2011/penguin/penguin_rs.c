/*  solution by ruben spaans.
    binary search over the answer: for each answer tested,
    traverse track in both directions and greedily fill water
    whenever descent is higher than answer. greedy returns
    false if water needed > w.
    O(N log P) where P is the required precision of the answer.
*/
#include <stdio.h>
#define EPS 1e-13
int a[10002],n,w;
double volume[10002]; /*  volume[i]: water volume between indices i and i+1 */
int nn;
/*  calculate the area of a segment with water level "level", width 1,
    and height a and b on each side */
double area(double level,double a,double b) {
  double t,ans;
  if(level>=a && level>=b) ans=level-(a+b)*.5;
  else if(level<=a && level<=b) ans=0;
  else { 
    if(a>b) t=a,a=b,b=t;
    ans=0.5*(level-a)*(level-a)/(b-a);
  }
  return ans;
}

/*  traverse "backwards", "climb" is the maximum allowed climb */
void traverse(double climb) {
  double fill;
  int top=a[0];    /*  height from where we are currently sliding */
  int i;
  for(i=1;i<n;i++) {
    /*  process segment between a[i] and a[i+1] */
    /*  have we slid far enough? */
    if(top-climb>a[i]) {
      fill=area(top-climb,a[i-1],a[i]);
      if(volume[i-1]<fill) volume[i-1]=fill;
      fill=area(top-climb,a[i],a[i+1]);
      if(volume[i]<fill) volume[i]=fill;
    }
    if(a[i+1]>=a[i] && a[i+1]>a[i+2] && a[i+1]>top-climb) top=a[i+1];
  }
}

void traverse2(double climb) {
  double fill;
  int top=a[0];    /*  height from where we are currently sliding */
  int i;
  for(i=n-1;i;i--) {
    /*  process segment between a[i] and a[i+1] */
    /*  have we slid far enough? */
    if(top-climb>a[i]) {
      fill=area(top-climb,a[i-1],a[i]);
      if(volume[i-1]<fill) volume[i-1]=fill;
      fill=area(top-climb,a[i],a[i+1]);
      if(volume[i]<fill) volume[i]=fill;
    }
    if(i>1 && a[i-1]>=a[i] && a[i-1]>a[i-2] && a[i-1]>top-climb) top=a[i-1];
  }
}

/*  return 1 if it is possible to achieve a maximal climb of "climb" without
    using more than w water */
int check(double climb) {
  double d;
  int i;
  for(i=0;i<=n;i++) volume[i]=0;
  traverse(climb);
  traverse2(climb);
  for(i=d=0;i<n;i++) d+=volume[i];
  return d<=w;
}

double solve() {
  /*  binary search for the maximum climb */
  double left=0,right=100,mid;
  int i;
  for(i=0;i<100;i++) {
    mid=(left+right)*0.5;
    if(check(mid)) right=mid;
    else left=mid;
  }
  return left;
}

int main() {
  int cases,i;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%d %d",&n,&w);
    for(i=0;i<=n;i++) scanf("%d",&a[i]);
    a[n+1]=a[0];
    printf("%.10f\n",solve());
  }
  return 0;
}
