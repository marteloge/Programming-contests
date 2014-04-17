// Solution for Marble Madness
// Author Børge Nordli

#include <stdio.h>

int main() {
  char n, t;
  for(scanf("%s%c"); scanf("%c", &n) > 0; t = n) {
    if (n == 10) printf("%d.00 %d.00\n", ~t & 1, t & 1);
  }
}
