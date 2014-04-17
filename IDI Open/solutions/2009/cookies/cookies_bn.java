// Author: Børge Nordli
//
// Straightforward counting.

import java.util.Scanner;

public class cookies_bn {
  static int[] Letters(String s) {
    int[] l = new int[26];
    for (char c : s.toCharArray())
      ++l[c - 'A'];
    return l;
  }

  static boolean WordPossible(int[] w, int[] box) {
    for (int i = 0; i < 26; ++i) {
      if (w[i] > box[i]) return false;
    }
    return true;
  }

  public static void main(String[] args) {
    for (int N = in.nextInt(); N --> 0;) {
      int[] box = Letters(in.next());
      for (int n = in.nextInt(); n --> 0;) {
        System.out.println(WordPossible(Letters(in.next()), box) ? "YES" : "NO");
      }
    }
  }

  static Scanner in = new Scanner(System.in);
}
