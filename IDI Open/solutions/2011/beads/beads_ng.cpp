#include <algorithm>
#include <cassert>
#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

#define OUT(EXPR) \
  cerr << __STRING(EXPR) << "=" << EXPR << "\n";

#define OUT2(E1, E2) \
  cerr << __STRING(E1) << "=" << E1 << " "; \
  cerr << __STRING(E2) << "=" << E2 << "\n";

class IntervalCounter {
private:  
  int m;      // The size of the interval
  int l;      // Levels in the tree
  int n;      // The number of nodes in the tree
  int s, e;   // Node number for first and last leaf
  int *D;     // The sum for the subtrees in the binary tree
  int *L, *R; // The left-most and right-most leaf number covered

public:
  IntervalCounter(int _m)  {
    m = _m;
    l = (int)ceil(log(m) / log(2));
    n = 2 * (1 << l) - 1;
    s = n >> 1;
    e = n - 1;
    D = new int[n]; 
    L = new int[n];
    R = new int[n];
    fill(D, D + n, 0);
    for (int i = s; i <= e; ++i) {
      L[i] = R[i] = i - s;
    }
    for (int i = s - 1; i >= 0; --i) {
      L[i] = L[left_child(i)];
      R[i] = R[right_child(i)];
    }
  }

  ~IntervalCounter() {
    delete[] D;
    delete[] L;
    delete[] R;
  }

  int parent(int i) { return i > 0 ?(i - 1) >> 1 : 0; }

  int left_child (int i) { return (i << 1) + 1; }
  int right_child(int i) { return (i << 1) + 2; }

  int left_brother (int i) { return i - 1; }
  int right_brother(int i) { return i + 1; }

  void put(int i, int c) {
    assert(i < m);
    for (int k = i + s; k != parent(k); k = parent(k)) {
      D[k] += c;
    }
  }

  int get(int i, int j) {
    int x = 0;
    int u = i + s, v = j + s;
    while (u != parent(u)) {
      if (L[parent(u)] < L[u]) {
        if (i <= L[u] && R[u] <= j) x += D[u];
        u = right_brother(parent(u));
      } else {
        u = parent(u);
      }
      if (R[v] < R[parent(v)]) {
        if (i <= L[v] && R[v] <= j) x += D[v];
        v = left_brother(parent(v));
      } else {
        v = parent(v);
      }
    }
    /*
    OUT(x);
    int x2 = 0; 
    for (int k = i + s; k <= j + s; ++k) {
      x2 += D[k];
    }
    OUT(x2);
    assert(x == x2);
    */
    return x;
  }
};

/*
int main() {
  srand(13);
  for (int i = 0; i < 100; ++i) {
    int m = rand() % 20;
    IntervalCounter ic(m);
    fprintf(stderr, "[");
    for (int j = 0; j < m; ++j) {
      int x = rand() % 10;
      fprintf(stderr, "%d ", x);
      ic.put(j, x);
    }
    fprintf(stderr, "]\n");
    for (int j = 0; j < m - 1; ++j) {
      for (int k = j + 1; k < m; ++k) {
        ic.get(j, k);
      }
    }
    ic.get(0, m - 1);
  }
}
*/

int main() {
  int n; scanf("%d", &n);
  while (n--) {
    int m, p, q; scanf("%d %d %d", &m, &p, &q);
    IntervalCounter ic(m);
    for (int i = 0; i < p + q; ++i) {
      char r[2]; int a, b;
      scanf("%s %d %d", r, &a, &b);
      if (r[0] == 'P') {
        ic.put(a - 1, b);
      } else {
        assert(r[0] == 'Q');
        printf("%d\n", ic.get(a - 1, b - 1));
      }
    }
  }
}
