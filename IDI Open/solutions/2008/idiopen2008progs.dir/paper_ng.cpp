/* Sample solution for Paper
 * IDI Open 2008
 * Author: Nils Grimsmo
 */
 
#define NODEBUG

const int VARS = 3;
const int INPS = 1 << VARS; // Bits in an expression signature
const int SIGS = 1 << INPS; // Number of signatures
const char FIRST_VAR = 'z' - VARS + 1;
const int MASK = SIGS - 1;
const int INF  = 1 << 30;

typedef int sig_t; 

#include <cassert>
#include <iostream>
#include <set>
#include <string>
#include <sstream>
#include <vector>

using namespace std;
using namespace __gnu_cxx;

#define ITER(i,A) for (typeof((A).begin()) i = (A).begin(); i != (A).end(); ++i)

// Signature for simple expressions "x", "y", ...
sig_t Simple(int v) {
  assert(v < VARS);
  sig_t e = 0;
  for (int i = 0; i < INPS; ++i) e |= ((bool)(i & 1 << v)) << i;
  return e;
}

int M[SIGS]; // Shortest found with signature
vector<set<sig_t> > Q; // One set for each lenght

void enqueue(sig_t e, int len) {
  if (len < M[e]) {
    if (M[e] != INF) Q[M[e]].erase(e);
    Q[len].insert(e);
    M[e] = len;
  }
} 
  
void precompute() {
  fill_n(M, SIGS, INF);
  Q.push_back(set<sig_t>());
  Q.push_back(set<sig_t>());
  for (int v = 0; v < VARS; v++) enqueue(Simple(v), 1);
  for (int len = 2; ; len++) {
    Q.push_back(set<sig_t>());
    int p = len - 1;
    ITER(i, Q[p]) {
      enqueue(~*i & MASK, len);
    }
    for (p = 1; p <= len - 4; p++) {
      int q = len - p - 3;
      assert(1 + p + 1 + q + 1 == len);
      ITER(i, Q[p]) {
        ITER(j, Q[q]) { 
          enqueue(*i & *j, len);
          enqueue(*i | *j, len);
        }
      }
    }
    int count = 0;
    for (int s = 0; s < SIGS; ++s) if ((int)M[s] < INF) count++;
//     cerr << "wanted " << len << endl;
//     cerr << "count: " << count << " / " << SIGS << endl;
    if (count == SIGS) return;
  }    
}

string B; 
size_t p; 

char ne() {
  while (p < B.size() && B[p] == ' ') p++; 
  if (p == B.size()) { cerr << "empty buffer" << endl; exit(17); }
  return B[p++];
}

sig_t parse() {
  char a = ne();
  if (isalpha(a)) {
    return Simple(a - FIRST_VAR);
  } else if (a == '(') {
    sig_t f = parse();
    char b = ne();
    sig_t g = parse();
    char c = ne();
    assert(b == '&' || b == '|'); 
    assert(c == ')');
    if (b == '&') return f & g;
    else return f | g;
  } else if (a == '!') {
    sig_t f = parse();
    return ~f & MASK;
  } else { 
    exit(14); 
  }
}

int main() {
  precompute();
//   cerr << "x=" << hex << Simple(0) << "\n";
//   cerr << "y=" << hex << Simple(1) << "\n";
//   cerr << "z=" << hex << Simple(2) << "\n";
//   cerr << "M = ["; 
//   for (int i = 0; i < SIGS; ++i) cerr << M[i] << ",";
//   cerr << "]\n";
  getline(cin, B); istringstream is(B);
  int n; is >> n; 
  while (n--) {
    getline(cin, B); p = 0;
    sig_t e = parse();
    cout << M[e] << endl;
  }
}
