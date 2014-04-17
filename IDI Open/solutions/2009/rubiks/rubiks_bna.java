// Author: Børge Nordli
//
// Two-sided BFS from the start state and the one possible solved state.
// Keep a hash table of seen states.

import java.util.*;

class Cube {
  public byte s[] = new byte[24];

  // Positions that are changed with the 3 different moves.
  private static int moves[][][] =
  {{{ 0, 14, 20,  4}, {13, 23,  7,  3}, {19, 18, 17, 16}},
   {{ 7, 19, 15, 11}, {18, 14, 10,  6}, {23, 22, 21, 20}},
   {{ 0,  9, 22, 19}, { 1, 10, 23, 16}, {15, 14, 13, 12}}};

  // Mapping from string input to positions.
  private static int map[] =
  {0,  1,
   3,  2,
   4,  5,  8,  9,  12, 13, 16, 17,
   7,  6,  11, 10, 15, 14, 19, 18,
   20, 21,
   23, 22,
  };

  Cube(CharSequence r) {
    for (int i = 0; i < r.length(); ++i) {
      s[map[i]] = (byte) ("GROBYW".indexOf(r.charAt(i)));
    }
  }

  Cube(Cube o) {
    s = Arrays.copyOf(o.s, 24);
  }

  Cube(byte[] f) {
    for (int i = 0; i < 6; ++i) {
      s[4*i] = s[4*i+1] = s[4*i+2] = s[4*i+3] = f[i];
    }
  }

  private void rotate(int a, int b, int c, int d) {
    byte t = s[a]; s[a] = s[b]; s[b] = s[c]; s[c] = s[d]; s[d] = t;
  }

  public void move(int mm) {
    int p[][] = moves[mm % 3];
    if (mm < 3) {
      // Clockwise rotation.
      for (int i = 0; i < p.length; ++i) {
        rotate(p[i][0], p[i][1], p[i][2], p[i][3]);
      }
    } else {
      // Counter-clockwise rotation.
      for (int i = 0; i < p.length; ++i) {
        rotate(p[i][3], p[i][2], p[i][1], p[i][0]);
      }
    }
  }

  private static int[][] f =
  {{1, 9, 12}, {0, 13, 16}, {3, 17, 4}, {21, 11, 6}, {22, 15, 10},
   {23, 19, 14}, {20, 7, 18}};

  public byte findOtherFace(byte a, byte b) {
    for (int i = 0; i < f.length; ++i) {
      if ((s[f[i][0]] == a || s[f[i][1]] == a || s[f[i][2]] == a) &&
          (s[f[i][0]] == b || s[f[i][1]] == b || s[f[i][2]] == b)) {
        for (int j = 0; j < 3; ++j) {
          if (s[f[i][j]] != a && s[f[i][j]] != b) return s[f[i][j]];
        }
      }
    }
    return -1;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || !(o instanceof Cube)) return false;
    return Arrays.equals(s, ((Cube) o).s);
  }

  public int hashCode() {
    return Arrays.hashCode(s);
  }
};

public class rubiks_bna {
  void Run() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < 12; ++i) {
      s.append(in.next());
    }
    System.out.println(solve(new Cube(s)));
  }

  boolean next(ArrayList<Cube> old, ArrayList<Cube> next,
               HashSet<Cube> seen, HashSet<Cube> target) {
    for (Cube p : old) {
      for (int i = 0; i < 6; ++i) {
        Cube n = new Cube(p);
        n.move(i);
        if (target.contains(n)) {
          return true;
        } else if (!seen.contains(n)) {
          next.add(n);
          seen.add(n);
        }
      }
    }
    return false;
  }

  // Generate all possible solved version of cubes.
  void addSolved(List<Cube> l, Cube c) {
    byte f[] = new byte[6];

    // Faces 0, 1, 2 are fixed (given from corner).
    f[0] = c.s[2];
    f[1] = c.s[5];
    f[2] = c.s[8];
    f[3] = c.findOtherFace(c.s[2], c.s[8]);
    f[4] = c.findOtherFace(c.s[5], c.s[2]);
    f[5] = c.findOtherFace(c.s[8], c.s[5]);
    l.add(new Cube(f));
  }

  int solve(Cube c) {
    ArrayList<Cube> s1, s2 = new ArrayList<Cube>(), e1, e2 = new ArrayList<Cube>();
    HashSet<Cube> start = new HashSet<Cube>(), end = new HashSet<Cube>();

    // Add starting cube.
    s2.add(c);
    start.addAll(s2);

    // Add possible solved cubes.
    addSolved(e2, c);
    end.addAll(e2);

    if (end.contains(c)) return 0;
    int g = 0;
    while (true) {
      // BFS from starting cube.
      ++g;
      s1 = s2; s2 = new ArrayList<Cube>();
      if (next(s1, s2, start, end)) {
        return g;
      }

      // BFS from solved cubes.
      ++g;
      e1 = e2; e2 = new ArrayList<Cube>();
      if (next(e1, e2, end, start)) {
        return g;
      }
    }
  }

  public static void main(String[] args) {
    for (int N = in.nextInt(); N --> 0;) {
      new rubiks_bna().Run();
    }
  }

  static Scanner in = new Scanner(System.in);
}
