// Author: Børge Nordli
//
// Finding the probability of a uniformly random permutation of w*h objects
// having exactly s cycles using dynamic programming.

import java.util.Scanner;

public class bicycle_bn {
  String format(long n, long d) {
    long g = gcd(n, d);
    long ng = n/g, dg = d/g;
    if (dg == 1) return "" + ng;
    return "" + ng + "/" + dg;
  }

  long gcd(long a, long b) {
    while (true) {
      a %= b;
      if (a == 0) return b;
      b %= a;
      if (b == 0) return a;
    }
  }

  void Run(int t, int m) {
    long s = 0;
    for (int i = t-m+1; i <= t; ++i) {
      s += d[t][i];
    }
    System.out.println(format(s,f[t]));
  }

  static int MAX = 20;
  static long[][] d;
  static long[] f;

  public static void main(String[] args) {
    d = new long[MAX+1][];
    f = new long[MAX+1];
    d[0] = new long[] {0, 0};
    d[1] = new long[] {0, 1, 0};
    f[0] = 0;
    f[1] = 1;

    for (int i = 2; i <= MAX; ++i) {
      d[i] = new long[i+2];
      d[i][0] = 0;
      for (int j = 1; j <= i; ++j) {
        d[i][j] = d[i-1][j-1] + (i-1)*d[i-1][j];
      }
      d[i][i+1] = 0;

      f[i] = f[i-1]*i;
    }

    for (int i = in.nextInt(); i --> 0;) {
      new bicycle_bn().Run(in.nextInt()*in.nextInt(), in.nextInt());
    }
  }

  static Scanner in = new Scanner(System.in);
}
