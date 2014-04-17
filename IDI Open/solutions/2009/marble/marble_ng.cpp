// IDI Open Programming Contest 2009
// Sample solution for Problem E - Communication Channels
// Author: Nils Grimsmo

/*
  Invariant: 
    There is an odd number of white marbles in the bag, or,
    there is an even number of white marbles in the bag.
    The number of marbles is reduced by one each round.

  Proof:
    Assume there are k white marbles.
    If you take out:
      Two white out, one black in
        k-2 white
      One white and one black out, one white in
        k white
      Two black out, one black in
        k white

  When there is only one marble left, if the number of white is
    Odd:
      There cannot be zero white, 
      and the last marble must be white.
    Even:
      There cannot be one white,
      and the last marble must be black.
*/

#include <stdio.h>
int main() {
  int n; scanf("%d", &n); while (n--) {
    long long b, w; scanf("%lld %lld", &b, &w);
    printf("%.2lf %.2lf\n", (double)((w+1)%2), (double)(w%2));
  }
}
