//
// Solution for Problem J - Combat Odds
//
// Define P(N, L) as the probability of observing L losses in a row 
// when simulating N combats.
//
// Then e.g. P(N, 3) =
//    p(win) * P(N - 1, 3) +
//    p(loss) * p(win) * P(N - 2, 3) +
//    p(loss) * p(loss) * p(win) * P(N - 3, 3) + 
//    p(loss) * p(loss) * p(loss) * 1
//
// And in general P(N, L) =
//   sum(k = 1..L, pow(p(loss), k - 1) * p(win) * P(N - k, L)) +
//   pow(p(loss), L)
//
// Which is easy to calculate when P is already known for smaller
// values of N. The iteratively calculates P(k, L) for all values
// of k from 0 to N in a vector<long double> v, and the answer to
// the problem is P(N, L) = v[N].
//
// Tor Gunnar Houeland
//

#include <iostream>
#include <vector>
#include <bitset>
#include <cassert>
#include <cstdlib>
#include <ctime>
#include <map>

using namespace std;

int main() {
	int T;
	cin >> T;
	cout.precision(50);
	for (int t = 0; t < T; ++t) {
		int N, L;
		long double p;
		cin >> N >> L >> p;
		vector<long double> v;
		for (int n = 0; n < N + 1; ++n) {
			if (n < L) v.push_back(0);
			else {
				long double hit = 0;
				long double c = 1;
				for (int l = 1; l <= L; ++l) {
					hit += c * p * v[n - l];
					c *= (1 - p);
				}
				hit += c;
				v.push_back(hit);
			}
		}
		cout << fixed << v[N] << endl;
	}
}


/*

f(abcdefghi...)
=00000 * 1
=00001 * f(fghi...)
=0001 * f(efghi...)
=001 * f(defghi...)
=01 * f(cdefghi...)
=1 * f(bcdefghi...)


*/
