// Author: Børge Nordli
// Running time: O(n*log(n)) (binary search)

#include <algorithm>
#include <stdio.h>

double prob(int n, int l, int* d) {
  if (l < 0) return 0.0;
  double r = 0;
  for (int i = 0; i < n; ++i) {
    r += (double) (std::upper_bound(d+i, d+n+1, d[i] + l) - d - i - 1)/(n - i);
  }
  return r;
}

int main() {
  int N;
  for (scanf("%d", &N); N --> 0;) {
    int n, l, u;
    scanf("%d", &n);
    char c[n];
    int b[n+1];
    scanf("%s", c);
    b[0] = 0;
    for (int i = 0; i < n; ++i) {
      b[i+1] = b[i] + c[i] - 'A';
    }
    scanf("%d %d", &l, &u);
    double l1 = prob(n, l-1, b)/n;
    double l2 = prob(n, u, b)/n;
    printf("%.10f %.10f %.10f\n", l2 - l1, l1, 1.0 - l2);
  }
}
