/* Sample solution for The Still Embarrassed Cryptographer
 * IDI Open 2008
 * Author: Nils Grimsmo
 *
 * Find LCM of all cycle lengths.
 */

#include <cstdio>
#include <cstring>

using namespace std; 

const int MAX_m = 10000;

int gcd(int a, int b) { return b != 0 ? gcd(b, a % b) : a; }
int lcm(int a, int b) { return a * b / gcd(a, b); }

int main() {
  int n; scanf("%d", &n);
  while (n--) {
    char S[MAX_m+1], T[MAX_m+1]; scanf("%s%s", S, T);
    int m = strlen(S);
    // Mapping
    char C[128]; for (int i = 0; i < 128; ++i) C[i] = -1;
    for (int i = 0; i < m; ++i) C[(int)S[i]] = T[i];
    int q = 1; // LCM of all cycles 
    for (int i = 0; i < 128; i++) if (C[i] != -1) { // Cycle not inspected
      int k = 1, j, t;
      // Run through cycle (i, C[i], C[C[i]], ...), and set C[j] = -1 
      for (j = C[i], C[i] = -1; j != i; t = C[j], C[j] = -1, j = t, ++k)
        if (j == -1) { k = -1; break; } // Incomplete map
      if (k == -1) { q = -1; break; }
      q = lcm(q, k);
    }
    if (q == -1) printf("mjau\n");
    else         printf("%d\n", q - 1);
  }
}
