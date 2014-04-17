#include <iostream>
#include <vector>
#include <string>

using namespace std;

vector<int> boxes;
int Bbig;

/*
[0 1 2 3 4 5 6 7]
[8   9   10  11]
[12      13]
[14]
*/

void put(int i, int a) {
	int j = 1;
	int b = Bbig;
	int bb = 0;
	while (b > 0) {
		boxes[bb + i] += a;
		bb += b;
		b /= 2;
		i /= 2;
	}
}

int sum_below_idx(int n) {
// 	cout << "sum_below(" << n << ")" << endl;
	int sum = 0;
	int j = 1;
	int b = Bbig;
	int bb = 0;
	while (b > 0) {
// 		cout << "bb+n:" << bb+n << " | " << n << endl;
		if (n % 2 == 1) {
// 			cout << "  adding b[" << bb+n << "]: " << boxes[bb+n-1] << endl;
			sum += boxes[bb + n-1];
		}
		bb += b;
		b /= 2;
		n /= 2;
	}
// 	cout << "= " << sum << endl;
	return sum;
}

void query(int i, int j) {
// 	cout << "  ###  " << endl;
// 	int bto = B;
// 	int bcount = 0;
// 	int b = 0;
// 	while (bto > 0) {
// 		cout << boxes[b++] << " ";
// 		bcount++;
// 		if (bcount == bto) {
// 			bcount = 0;
// 			bto /= 2;
// 			cout << endl;
// 		}
// 	}
// 	cout << endl;
// 	cout << "  ###  " << endl;
	cout << sum_below_idx(j) - sum_below_idx(i) + boxes[j] << endl;
}

int main() {
	int T;
	cin >> T;
	for (int t = 0; t < T; ++t) {
		boxes.clear();
		int B, P, Q;
		cin >> B >> P >> Q;
		Bbig = 1;
		while (Bbig < B) Bbig *= 2;
		for (int b = 0; b < Bbig * 2 + 10; ++b) {
			boxes.push_back(0);
		}
		for (int pq = 0; pq < P + Q; ++pq) {
			string c;
			int a, b;
			cin >> c >> a >> b;
			if (c == "P") {
				put(a-1, b);
			} else if (c == "Q") {
				query(a-1, b-1);
			}
		}
	}
}
