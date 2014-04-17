/* Sample solution for Eight Puzzle
 * IDI Open 2008
 * Author: Nils Grimsmo
 * 
 * - BFS from goal position.
 * - Store number of moves to all reachable board configurations.
 * - Maintain configurations as integers (123456789). Nine marks blank.
 */

#include <cstdio>
#include <ext/hash_map>
#include <queue>

using namespace std;
using namespace __gnu_cxx;

typedef pair<int,int> pint;

// Board:
// 123
// 456
// 789

// If M[i][j] > 0
//   You can move from pos i to pos M[i][j]
int M[9][5] = {
  /*1*/{2,4,     0,0,0}, 
  /*2*/{1,3,5,   0,0}, 
  /*3*/{2,6,     0,0,0}, 
  /*4*/{1,5,7,   0,0}, 
  /*5*/{2,4,6,8, 0}, 
  /*6*/{3,5,9,   0,0}, 
  /*7*/{4,8,     0,0,0}, 
  /*8*/{5,7,9,   0,0}, 
  /*9*/{6,8,     0,0,0}, 
};

int main() {
  char A[10];          // String buffer
  hash_map<int,int> C; // Cost of reaching configuration
  queue<pint> Q;       // BFS queue
  // C and A contains pairs of configuration and cost
  Q.push(pint(123456789,0));
  int m = 0; 
  while (Q.size()) {
    pint p  = Q.front(); Q.pop();
    m = max(m, p.second); 
    C[p.first] = p.second; 
    sprintf(A, "%d", p.first);
    int i = 0; while (A[i] != '9') ++i; // 9 marks blank 
    for (int j = 0; M[i][j]; ++j) {
      swap(A[i], A[M[i][j] - 1]); 
      int x; sscanf(A, "%d", &x); 
      if (C.count(x) == 0) {
        Q.push(pint(x, p.second + 1));
      }
      swap(A[i], A[M[i][j] - 1]); 
    }
  }
  int n; scanf("%d", &n);
  while (n--) {
    scanf("%s", ((char*)A)+0);
    scanf("%s", ((char*)A)+3);
    scanf("%s", ((char*)A)+6);
    int i = 0; while (A[i] != '#') ++i;
    A[i] = '9'; 
    int x; sscanf(A, "%d", &x); 
    if (C.count(x)) printf("%d\n", C[x]);
    else            printf("impossible\n"); 
  }
}
