/* Sample solution for Frigde of Your Dreams
 * IDI Open 2007
 * Author: Nils Grimsmo
 */

#include <cstdio>

const int BITS = 24;

int main() {
    int n; scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        char B[BITS+1]; scanf("%s", B);
        int v = 0;
        for (int j = 0; j < BITS; j++) {
            v = v * 2 + B[j] - '0';
        }
        printf("%d\n", v);
    }
}
