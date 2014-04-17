/* Sample solution for The Traveling Orienteerer
 * IDI Open 2008
 * Author: Nils Grimsmo
 */
 
#include <cassert>
#include <cmath>
#include <cstdio>

using namespace std;

const int MAX_n = 1000;

int main() {
  int n; scanf("%d", &n);
  double X[MAX_n], Y[MAX_n];
  for (int i = 0; i < n; ++i) scanf("%lf %lf", &X[i], &Y[i]);
  int m; scanf("%d", &m);
  while (m--) {
    int p; scanf("%d\n", &p);
    double d = 0;
    int i, j; scanf("%d\n", &i);
    while (--p) {
      scanf("%d\n", &j); 
      d += sqrt((X[j]-X[i]) * (X[j]-X[i]) + (Y[j]-Y[i]) * (Y[j]-Y[i]));
      i = j; 
    }
    printf("%.0lf\n", d);
    assert(round(d) == round(d + 1E-6));
    assert(round(d) == round(d - 1E-6));
  }
}
