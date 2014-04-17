// Author: Børge Nordli
//
// Very space and time efficient -- only stores one input line at a time
// and uses Disjoint-set data structure to track flocks.
//
// Yes, there is a memory leak here, but the objects are small and it should
// not matter much.

#include <stdio.h>

int num_sets;

class set {
 public:
  set() {
    parent = this;
    rank = 0;
    ++num_sets;
  }
  set* root() {
    if (parent == this) return this;
    parent = parent->root();
    return parent;
  }
  void merge(set* s) {
    set* tr = this->root();
    set* sr = s->root();
    if (sr->rank > tr->rank) {
      tr->parent = sr;
    } else if (sr->rank < tr->rank) {
      sr->parent = tr;
    } else if (tr != sr) {
      sr->parent = tr;
      tr->rank += 1;
    }
    --num_sets;
  }
  set* parent;
  int rank;
};

int main(int argv, char** argc) {
  int N, x, y;
  scanf("%d", &N);
  while (N --> 0) {
    scanf("%d %d", &x, &y);
    char line[y+1];
    set* blank = new set();  // This set is special.
    num_sets = 0;

    set* data[y+1];
    for (int i = 0; i <= y; ++i) data[i] = blank;

    for (int i = 0; i < x; ++i) {
      scanf("%s", line);
      for (int j = 0; j < y; ++j) {
        if (line[j] == '.') {
          data[j+1] = blank;
        } else if (line[j] == '#') {
          set* left = data[j];
          set* up = data[j+1];
          if (left == blank) {
            if (up == blank) {
              data[j+1] = new set();
            } else {
              data[j+1] = up;
            }
          } else {
            if (up != blank && up->root() != left->root()) {
              up->merge(left);
            }
            data[j+1] = left;
          }
        }
      }
    }
    printf("%d\n", num_sets);
  }
}
