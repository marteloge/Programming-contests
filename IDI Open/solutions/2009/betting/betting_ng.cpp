// IDI Open Programming Contest 2009
// Sample solution for Problem D - Box Betting
// Author: Nils Grimsmo

#include <algorithm>
#include <cassert>
#include <cstdio>
using namespace std;

int main() {
  int T; scanf("%d", &T); while (T--) {
    int N; scanf("%d", &N);
    char B[N+1]; scanf("%s", B);
    for (int i = 0; i < N; ++i) {
      B[i] -= 'A';
    } 
    int L, U; scanf("%d %d", &L, &U);
    int C[N+1];
    C[0] = 0;
    for (int i = 0; i < N; ++i) C[i+1] = C[i] + B[i];
    int j = 0, k = 0;
    double P[3] = {0,0,0}; // Probability of "too few", success, and breakdown
    for (int i = 0; i < N; ++i) { // Starting point
      if (j < i) j = i;
      if (k < i) k = i;
      while (j < N && C[j+1] - C[i] < L)  ++j; // First sucessfull end-point, inclusive
      while (k < N && C[k+1] - C[i] <= U) ++k; // First breaking end-point 
      // For this starting box, the number of boxes for each case
      int F[3] = {j - i, k - j, N - k};
      // N different starting boxes, N-i different end boxes
      double f = N * (double)(N - i);
      for (int q = 0; q < 3; ++q) if (F[q] >= 0) P[q] += F[q] / f;
    }
#ifndef NDEBUG
    double p = P[0] + P[1] + P[2];
    assert(p - 1E-10 < 1.0);
    assert(p + 1E-10 > 1.0);
#endif
    printf("%.10lf %.10lf %.10lf\n", P[1], P[0], P[2]);
  }
}
