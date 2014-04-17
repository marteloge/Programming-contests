// Author: Børge Nordli
// Running time: O(n) (two passes)

#include <stdio.h>

double prob(int n, int l, int* d) {
  int a = 0, b = 0, c = d[0];
  double r = 0;
  while (b < n) {
    if (c < l) {
      r += (double)(b-a+1)/(b+1);
      c += d[++b];
    } else if (a < b) {
      c -= d[a++];
    } else {
      c = d[++a];
      b = a;
    }
  }
  return r;
}

int main() {
  int N;
  for (scanf("%d", &N); N --> 0;) {
    int n, l, u;
    scanf("%d", &n);
    char c[n];
    int b[n];
    scanf("%s", c);
    for (int i = 0; i < n; ++i) {
      b[i] = c[n-i-1] - 'A';
    }
    scanf("%d %d", &l, &u);
    double l1 = prob(n, l, b)/n;
    double l2 = prob(n, u+1, b)/n;
    printf("%.10f %.10f %.10f\n", l2 - l1, l1, 1.0 - l2);
  }
}
