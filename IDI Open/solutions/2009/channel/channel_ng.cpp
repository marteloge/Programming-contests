// IDI Open Programming Contest 2009
// Sample solution for Problem E - Communication Channels
// Author: Nils Grimsmo

#include <iostream>
using namespace std;

int main() {
  int T; cin >> T;                 // The number of test cases
  while (T--) {
    string a, b; cin >> a >> b;    // Read the two binary strings
    if (a == b) cout << "OK\n";    // Check if they are equal
    else        cout << "ERROR\n";
  }
} 
