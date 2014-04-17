//
// Solution for Problem J - Combat Odds
//
// Define P(N) as the probability of observing L losses in a row 
// when simulating N combats, and pLosses(X) = pow(p(loss), X) as the
// probability of losing X times in a row. p(loss) + p(win) = 1.
//
// P(N) can be calculated as
//   sum(k = 1..L, pLosses(k - 1) * p(win) * P(N - k)) +
//   pLosses(L)
//
// P(N - 1) =
//   sum(k = 1..L, pLosses(k - 1) * p(win) * P(N - 1 - k)) +
//   pLosses(L)
//
// P(N) =
//   p(win) * P(N - 1) +
//   sum(k = 2..L, pLosses(k - 1) * p(win) * P(N - k)) +
//   pLosses(L)
// =
//   p(win) * P(N - 1) +
//   sum(k = 1..(L-1), pLosses(k) * p(win) * P(N - 1 - k)) +
//   pLosses(L)
// =
//   p(win) * P(N - 1) +
//   sum(k = 1..(L-1), pLosses(k - 1) * p(win) * P(N - 1 - k)) * p(loss) +
//   pLosses(L - 1) * p(win) * P(N - L - 1) * p(loss) +
//   -1 * pLosses(L - 1) * p(win) * P(N - L - 1)) * p(loss) +
//   pLosses(L)
// =
//   p(win) * P(N - 1) +
//   sum(k = 1..L, pLosses(k - 1) * p(win) * P(N - 1 - k)) * p(loss) +
//   pLosses(L) * p(loss) + 
//   -1 * pLosses(L) * p(loss) +
//   -1 * pLosses(L - 1) * p(win) * P(N - 1 - L) * p(loss) +
//   pLosses(L)
// =
//   p(win) * P(N - 1) +
//   p(loss) * P(N - 1) +
//   -1 * pLosses(L) * p(loss) +
//   -1 * pLosses(L - 1) * p(win) * P(N - 1 - L) * p(loss) +
//   pLosses(L) * (p(loss) + p(win))
// =
//   P(N - 1) + pLosses(L) * p(win) -
//   pLosses(L) * p(win) * P(N - 1 - L)
//
// Which can be computed in O(1) when the lower values of P(N)
// are already known. Doing this N times results in a running time
// of O(N), using O(L) memory by using a circular buffer to store
// the previous calculations.
//
// Tor Gunnar Houeland
//

#include <iostream>
#include <vector>
#include <bitset>
#include <cassert>
#include <cstdlib>
#include <ctime>
#include <cmath>
#include <map>

using namespace std;

//~ v[n-1 - L]
//~ v[n-1 - L+1]
//~ ...
//~ v[n-1 - 1]
//~ v[n-1]

int main() {
	int T;
	cin >> T;
	cout.precision(50);
	for (int t = 0; t < T; ++t) {
		int N, L;
		long double p;
		cin >> N >> L >> p;
		int L1 = 1;
		while (L1 <= L+1) L1 *= 2;
		L1--;
		vector<long double> v(L1+1);
		const long double d1pL = pow(1 - p, L);
		for (int n = 0; n < N + 1; ++n) {
			if (n < L) v[n&L1] = 0;
			else if (n == L) v[n&L1] = d1pL;
			else v[n&L1] = v[(n - 1)&L1] + d1pL*p - d1pL*p * v[(n - L - 1)&L1];
		}
		cout << fixed << v[N&L1] << endl;
	}
}
