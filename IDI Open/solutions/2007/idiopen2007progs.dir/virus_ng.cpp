/* Sample solution for Virus
 * IDI Open 2007
 * Author: Nils Grimsmo
 *
 * Solution:
 *
 * O(N^2 (log L)^2) Binary search over time to blow-up, and O(log L) matrix
 * power.
 *
 * Let A be a quadratic matrix such that the column vectors are the transitions
 * for each form, and with a last column of zeros and one in the last row,
 * which represent the "transitions" for the atoms:
 *
 *     /                   \
 *     | t_1,1 ... t_N,1 0 |
 *     |   .  .      .   . |
 * A = |   .    .    .   . |
 *     |   .      .  .   . |
 *     | t_1,N ... t_N,N 0 |
 *     | t_1,a ... t_N,a 1 |
 *     \                   /
 *
 * Then, if x_t is a column vector with the number of forms and atoms at time t
 *
 *     x_{t+1} = A x_t
 *
 * But 
 *
 *     x_t = A x_{t-1} = A (A x_{t-2}) = ... = A^t x_0
 *
 * Where x_0 = [1 0 ... 0]^T, representing the single form of type 1 at the
 * beginning.
 *
 * The power A^t can be found with O(log t) matrix multiplications. Take for
 * example 
 *
 *     A^13 = A^8 A^4 A = ((A^2)^2)^2 (A^2)^2 A = (((A^2) A)^2)^2 A
 *
 * You can find the answer from the bits in the exponent:
 *
 *     Start with Ai = I, the identity matrix
 *     For bit b in the exponent from most to least significant
 *         Ai := Ai Ai 
 *         if b = 1
 *             Ai := Ai A
 *
 * The identity matrix is
 *
 *     /           \
 *     | 1 0 ... 0 |
 *     | 0 1 ... 0 |
 * I = |  .  .   . |
 *     |  .   .  . |
 *     |  .    . . |
 *     | 0 0 ... 1 |
 *     \           /
 *
 * A trick must be done to avoid overflow in the values. Use longs, and let 
 * x = min(L, x) in the matrix element operations. Using doubles to handle the
 * overflow through INF does not work, as 0*INF=NAN.
 */

#include <algorithm>
#include <cmath>
#include <cstdio>
#include <stdint.h>

using namespace std;

const int MAX_N = 20;

int64_t** neu(int n) {
    int64_t** A = new int64_t*[n];
    int64_t* B = new int64_t[n*n];
    for (int i = 0; i < n; i++) A[i] = B + i * n;
    return A;
}

void del(int n, int64_t** A) {
    delete[] A[0];
    delete[] A;
}

void ident(int n, int64_t** A) {
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            A[i][j] = (j == i) ? 1 : 0;
}

void mul(int n, int64_t** A, int64_t** B, int64_t** C, int64_t maks) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            C[i][j] = 0;
            for (int k = 0; k < n; k++)
                C[i][j] = min(C[i][j] + A[i][k] * B[k][j], maks);
        }
    }
}

void puw(int n, int64_t** A, int64_t r, int64_t** Ai, int64_t maks) {
    int64_t** tAi = neu(MAX_N+1);
    int s = 0;
    int h = (int)ceil(log(r)/log(2));
    for (int b = h; b >= 0; b--) if (r & (1LL << b)) s++;
    if ((h + s) % 2 == 0) swap(Ai, tAi);
    ident(n, Ai);
    for (int b = h; b >= 0; b--) {
        mul(n, Ai, Ai, tAi, maks);
        swap(Ai, tAi);
        if (r & (1LL << b)) {
            mul(n, Ai, A, tAi, maks);
            swap(Ai, tAi);
        }
    }
}

int main() {
    int T; scanf("%d", &T);
    for (int t = 0; t < T; t++) {
        int N; int64_t L; scanf("%d %lld", &N, &L);
        int64_t** A = neu(N+1);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N+1; j++)
                scanf("%lld", &(A[i][j]));
        for (int j = 0; j < N; j++) A[N][j] = 0;
        A[N][N] = 1;
        int64_t** Ai = neu(N+1);
        int64_t le = 1;
        int64_t ri = N*L+1;
        while (le <= ri) {
            int64_t mi = (le + ri) / 2;
            puw(N+1, A, mi, Ai, L);
            if (le == ri) break;
            if (Ai[0][N] < L) le = mi + 1;
            else              ri = mi;
        }
        if (le > N*L) printf("lucky\n");
        else          printf("%lld\n", le); 
        del(N+1, A); del(N+1, Ai);
    }
}
