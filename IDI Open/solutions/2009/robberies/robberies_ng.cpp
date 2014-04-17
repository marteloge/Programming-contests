// IDI Open Programming Contest 2009
// Sample solution for Problem J - Robberies
// Author: Nils Grimsmo

/*
  Solution: 
    Dynamic programming on the banks used, and millions earned
*/

#include <cmath>
#include <iostream>
using namespace std;

int main() {
  int t; cin >> t; 
  while (t--) {
    // Probability to stay below, and number of banks
    double p; int n; cin >> p >> n; 
    int s = 0; // The total amount of money
    int M[n];    // Millions in bank
    double P[n]; // Probability of *getting* caught
    for (int i = 0; i < n; i++) {
      cin >> M[i] >> P[i];
      s += M[i];
    }
    double R[s]; // The maximal probability of not getting caught,
                 // for each final sum
    R[0] = 1.0;  // You don't get caught if you don't do anything
    for (int j = 1; j < s; ++j) R[j] = 0.0;
    for (int i = 0; i < n; ++i) {
      // Using banks 0 to i
      for (int j = s; j > 0; --j) {
        // Earning j millions
        // (going downwards to avoid using the same bank twice)
        // Earing M[i] millions from bank i,
        int d = j - M[i]; // and d millions from other banks
        // Using probability of *not* getting caught in bank i,
        // and the probability of not getting caught in the other banks.
        if (d >= 0 && R[d] * (1-P[i]) > R[j]) 
          R[j] = R[d] * (1-P[i]);
      }
    }
    for (int j = s; j >= 0; --j) {
      if (1 - R[j] < p) {
        cout << j << "\n";
        break;
      }
    }
  }
}
