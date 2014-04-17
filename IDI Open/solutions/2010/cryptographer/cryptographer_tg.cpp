#include <iostream>
#include <vector>
#include <bitset>
#include <cassert>
#include <cstdlib>
#include <ctime>
#include <cmath>
#include <map>

using namespace std;

bool solve(const string& str) {
	bitset<26> used;
	for (int l = 1; l <= str.size(); ++l) {
		int idx = str[l-1] - 'A';
		if (used[idx]) return false;
		used.set(idx);
		bool good = true;
		for (int j = 0; j < l; ++j) {
			if (!used[j]) good = false;
		}
		for (int i = 0; i < str.size(); ++i) {
			if (str[i] != str[i%l]) {
				good = false;
			}
		}
		if (good) {
			return true;
		}
	}
	return true;
}

int main() {
	int T;
	cin >> T;
	for (int t = 0; t < T; ++t) {
		string str;
		cin >> str;
		if (solve(str)) cout << "unknown" << endl;
		else cout << "new" << endl;
	}
}
