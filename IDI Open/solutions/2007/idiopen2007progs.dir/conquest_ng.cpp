/* Sample solution for Conquistador
 * IDI Open 2007
 * Author: Nils Grimsmo
 */

#include <algorithm>
#include <iostream>
#include <map>
#include <string>
#include <vector>

using namespace std;

#define ITER(I,V) for (typeof((V).begin()) I = (V).begin(); I != (V).end(); I++)

void split(string str, string sep, vector<string>& V) {
    size_t i, j;
    for (i = 0, j = str.find(sep, 0); j < str.size(); 
            i = j + sep.size(), j = str.find(sep, i)) {
        V.push_back(str.substr(i, j - i));
    }
    V.push_back(str.substr(i));
}

int main() {
    int n; cin >> n;
    for (int c = 0; c < n; c++) {
        map<string,int> M;
        string S; cin >> S;
        vector<string> A; split(S, ",", A);
        ITER(i, A) {
            vector<string> B; split(*i, ":", B);
            M[B[0]] = atoi(B[1].c_str());
        }
        string T; cin >> T;
        vector<string> C;
        split(T, "|", C);
        int mi = INT_MAX;
        ITER(i, C) {
            vector<string> D; split(*i, "&", D);
            int ma = 0;
            ITER(j, D) ma = max(ma, M[*j]);
            mi = min(mi, ma);
        }
        cout << mi << endl;
    }
}
