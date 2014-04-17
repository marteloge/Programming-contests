// IDI Open Programming Contest 2009
// Sample solution for Problem G - Counting Sheep
// Author: Nils Grimsmo

#include <iostream>
using namespace std;

char D[101][101]; // The landscape
bool S[101][101]; // Sheep already counted in a flock
int H, W;

// Count the flock at (i,j), if there is one
int count(int i, int j) {
  // Check that we are not outside the field, 
  if (i < 0 || H <= i || j < 0 || W <= j) return 0;
  if (D[i][j] == '#') {
    // Check that the sheep has not been counted already
    if (S[i][j]) return 0;
    S[i][j] = true;
    // Count neighbouring sheep to be in this flock
    count(i - 1, j); count(i + 1, j);
    count(i, j - 1); count(i, j + 1);
    return 1; // A flock here
  } else {
    return 0; // No sheep here!
  }
}

int main() {
  int T; cin >> T;
  while (T--) {
    cin >> H >> W;
    // Mark all sheep as unseen
    for (int i = 0; i < H; ++i) 
      for (int j = 0; j < W; ++j) 
        S[i][j] = false;
    // Read the field into D
    for (int i = 0; i < H; ++i) cin >> D[i];
    // Count the number of flocks
    int c = 0;
    for (int i = 0; i < H; ++i) 
      for (int j = 0; j < W; ++j) 
        c += count(i, j);
    cout << c << "\n";
  }
}
