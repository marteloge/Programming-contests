/* Sample solution for Help Chelsea!
 * IDI Open 2007
 * Author: Nils Grimsmo
 */

#include <cstdio>

using namespace std;

const int MAX_p = 1000;
const int MAX_name = 20;

int main() {
    int n; scanf("%d", &n);
    for (int c = 0; c < n; c++) {
        int p; scanf("%d", &p);
        char N[MAX_p][MAX_p+1];
        int max_v = -1;
        int i_max = -1;
        for (int i = 0; i < p; i++) {
            int c; scanf("%d", &c);
            scanf("%s", N[i]);
            if (c > max_v) {
                max_v = c;
                i_max = i;
            }
        }
        printf("%s\n", N[i_max]);
    }
}
