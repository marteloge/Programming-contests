// Solution for Robberies
// Author Børge Nordli

#include <stdio.h>

int main() {
  int N;
  for (scanf("%d", &N); N --> 0;) {
    double P;
    int n, s = 0;
    scanf("%lf %d", &P, &n);
    int m[n];
    double p[n];
    for (int i = 0; i < n; ++i) {
      scanf("%d %lf", m+i, p+i);
      s += m[i];
      p[i] = 1 - p[i];
    }
    double D[s+1];
    D[0] = 1.0;
    for (int j = 1; j <= s; ++j) D[j] = 0.0;
    for (int i = 0; i < n; ++i) {
      for (int j = s; j > 0; --j) {
        if (j - m[i] >= 0 && D[j-m[i]]*p[i] > D[j]) {
          D[j] = D[j-m[i]]*p[i];
        }
      }
    }
    int i = s;
    while (D[i--] + 1E-9 <= 1 - P);
    printf("%d\n", i+1);
  }
}
