#include <iostream>
#include <vector>
#include <iomanip>

using namespace std;

int N, W;
vector<int> a;

bool possible(long double maxclimb) {
	vector<long double> water_upto;
	for (int i = 0; i < a.size(); ++i) {
		water_upto.push_back(-1);
	}
	{
		int last_level = 100;
		for (int i = 0; i < a.size()-1; ++i) {
			if (a[i+1] >= a[i]) {
				last_level = a[i+1];
			} else {
				water_upto[i] = max(water_upto[i], last_level - maxclimb);
			}
		}
	}
	{
		int last_level = 100;
		for (int i = a.size() - 1; i >= 0; --i) {
			if (a[i] >= a[i+1]) {
				last_level = a[i];
			} else {
				if (last_level - a[i] > maxclimb) {
					water_upto[i] = max(water_upto[i], last_level - maxclimb);
				}
			}
		}
	}

	for (int i = water_upto.size() - 2; i >= 0; --i) {
		if (water_upto[i] < water_upto[i+1] && a[i+1] < water_upto[i+1]) {
			water_upto[i] = water_upto[i+1];
		}
	}
	for (int i = 0; i < water_upto.size() - 1; ++i) {
		if (water_upto[i+1] < water_upto[i] && a[i+1] < water_upto[i]) {
			water_upto[i+1] = water_upto[i];
		}
	}

	long double needsum = 0;
	for (int i = 0; i < water_upto.size() - 1; ++i) {
		int hiend = max(a[i], a[i+1]);
		int loend = min(a[i], a[i+1]);
		if (loend >= water_upto[i]) {
// 			cout << a[i] << "|" << a[i+1] << " -> " << water_upto[i] << " ok." << endl;
		} else if (hiend < water_upto[i]) {
			long double tofill = 1 * (hiend - loend) / 2.0;
			long double above = (water_upto[i] - hiend) * 1;
			long double needed = tofill + above;
// 			cout << a[i] << "|" << a[i+1] << " -> " << water_upto[i] << " raiseall: " << needed << endl;
			needsum += needed;
		} else {
			long double dy = water_upto[i] - loend;
			long double dx = dy / (hiend - loend);
			long double needed = dx * dy / 2.0;
// 			cout << a[i] << "|" << a[i+1] << " -> " << water_upto[i] << " raise " << loend << ": " << needed << endl;
			needsum += needed;
		}
	}
// 	cout << "needsum: " << needsum << endl;
	bool poss = (needsum <= W);
// 	cout << "possible(" << maxclimb << "): " << poss << endl;
	return poss;
// 	return true;
}

long double bs(long double lower, long double upper) {
	if (upper - lower < 1e-10) return (lower + upper)/2.0;
	long double mid = (lower + upper) / 2.0;
	if (possible(mid)) {
		return bs(lower, mid);
	} else {
		return bs(mid, upper);
	}
}

void solve() {
// 	for (int i = 0; i < 100; ++i) {
// 		cout << "  " << i << ": " << possible(i) << endl;
// 	}
	cout << setiosflags(ios::fixed) << setprecision(20) << bs(0.0, 100.0) << endl;
}

int main() {
	int T;
	cin >> T;
	for (int t = 0; t < T; ++t) {
		cin >> N >> W;
		a.clear();
		for (int n = 0; n < N+1; ++n) {
			int a_;
			cin >> a_;
			a.push_back(a_);
		}
// 		if (t == 0) continue;
		solve();
	}
}
