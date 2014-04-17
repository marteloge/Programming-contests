/* Sample solution for TV Battle
 * IDI Open 2008
 * Author: Nils Grimsmo
 */

#include <algorithm>
#include <iostream>
#include <vector>
 
using namespace std; 

const int T = 10080; 
const int MAX_n = 100000; 

class A {
  public:
  int s, // Start 
      d, // Duration
      p; // Points
};
  
// Sort on ending time
bool operator<(A x, A y) {
  return (x.s + x.d) < (y.s + y.d);
}

int main() {
  int t; scanf("%d\n", &t);
  while (t--) {
    int n; scanf("%d\n", &n);
    A V[MAX_n]; 
    for (int i = 0; i < n; ++i) {
      scanf("%d %d %d", &V[i].s, &V[i].d, &V[i].p);
    }
    sort(V, V + n); 
    int C[T+1]; C[0] = 0; // Maximum number of points up to time
    for (int t = 1, i = 0; t <= T; ++t) {
      C[t] = C[t - 1];
      // Programs before V[i] end before t
      while (i < n && V[i].s + V[i].d == t) {
        C[t] = max(C[t], C[V[i].s] + V[i].p);
        ++i;
      }
    }
    printf("%d\n", C[T]);
  }
}
