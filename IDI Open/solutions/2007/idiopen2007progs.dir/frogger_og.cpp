#include <iostream>
using namespace std;

int main() {
	int T, X;
	cin >> T;
	for (int i=0; i<T; ++i) {
		cin >> X;
		if (X > 4) cout << "frogger";
		else if (X == 4) cout << 20;
		else cout << (1<<X);
		cout << endl;
	}
}
