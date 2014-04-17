/* Sample solution for The Nutty Professor
 * IDI Open 2008
 * Author: Nils Grimsmo
 */

#include <cstdio>

using namespace std; 

int main() {
  int t; scanf("%d", &t);
  while (t--) {
    int d, n, s, p; scanf("%d %d %d %d", &d, &n, &s, &p);
    //      old total
    //              new total
    int c = n * s - (n * p + d);
    if (c > 0)      printf("parallelize\n");
    else if (c < 0) printf("do not parallelize\n");
    else            printf("does not matter\n");
  }
}
