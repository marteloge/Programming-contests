// IDI Open Programming Contest 2009
// Sample solution for Problem A - Letter Cookies
// Author: Nils Grimsmo

#include <iostream>
#include <vector>
using namespace std;

int main() {
  int T; cin >> T;      // The number of cookie boxes
  while (T--) {
    string U; cin >> U; // The letters in the box
    vector<int> A(26);  // Count the number of each letter
    for (int i = 0; i < (int)U.size(); ++i) ++A[U[i] - 'A'];
    int w; cin >> w;    // The number of words to spell
    while (w--) {
      string V; cin >> V; // The word
      vector<int> B(26);  // Count the number of each letter
      for (int i = 0; i < (int)V.size(); ++i) ++B[V[i] - 'A'];
      bool good = true;
      // Check if we have enough of each letter
      for (int j = 0 ; j < 26; ++j) if (A[j] < B[j]) good = false;
      if (good) cout << "YES\n";
      else      cout << "NO\n";
    }
  } 
}
