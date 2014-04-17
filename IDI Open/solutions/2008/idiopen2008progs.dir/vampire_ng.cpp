/* Sample solution for Vampire
 * IDI Open 2008
 * Author: Nils Grimsmo
 */

#include <cassert>
#include <cmath>
#include <cstdio>

using namespace std; 

const int MAX_x = 1000;
const int MAX_y = 1000;

// Memoization...
double _P[MAX_x+1][MAX_y+1];

// x: Number of rolls
// y: Needed successes
// return: Probability of success
double P(int x, int y) {
  if (_P[x][y] >= 0.0) return _P[x][y];
  double p; 
  if (y == 0) p = 1.0; 
  else if (x == 0) p = 0.0; 
  //       10                 8-9                    1-7
  else p = .1 * P(x, y - 1) + .2 * P(x - 1, y - 1) + .7 * P(x - 1, y);
  return _P[x][y] = p; 
}

int main() {
  for (int x = 0; x <= MAX_x; ++x) 
    for (int y = 0; y <= MAX_y; ++y) 
      _P[x][y] = -1.0;
  int n; scanf("%d", &n);
  while (n--) {
    int x, y; scanf("%d%d", &x, &y);
    double p = P(x, y);
    assert(round(1E3 * p) == round(1E3 * (p - 1E-6)));
    assert(round(1E3 * p) == round(1E3 * (p + 1E-6)));
    printf("%.3lf\n", p);
  }
}
