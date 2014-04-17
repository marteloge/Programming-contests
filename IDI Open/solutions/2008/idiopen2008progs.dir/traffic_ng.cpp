/* Sample solution for Traffic Load
 * IDI Open 2008
 * Author: Nils Grimsmo
 */
 
#include <cstdio>
#include <set>

using namespace std;

int main() {
  int n; scanf("%d", &n);
  while (n--) {
    int m; scanf("%d", &m);
    set<int> L, R; int t;
    for (int i = 0; i < m; i++) { scanf("%d", &t); L.insert(t); } 
    for (int i = 0; i < m; i++) { scanf("%d", &t); R.insert(t); } 
    int fromleft = 0, fromright = 0;
    while (L.size() || R.size()) {
      // Consider first hit not accounted for
      int l = *L.begin(), r = *R.begin(); 
      if (l < r) {
        fromleft++;
        // Remove associated hits
        L.erase(l); L.erase(l + 500); R.erase(l + 1000); R.erase(l + 1500);
      } else {
        fromright++;
        R.erase(r); R.erase(r + 500); L.erase(r + 1000); L.erase(r + 1500);
      }
    }
    printf("%d\n", fromleft);
  }
}
