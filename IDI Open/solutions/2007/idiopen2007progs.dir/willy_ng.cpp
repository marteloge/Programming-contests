/* Sample solution for Free Willy
 * IDI Open 2007
 * Author: Nils Grimsmo
 *
 * Solution:
 * Do a breadth-first search from both the start and goal string, and see if 
 * these searches meet "in the middle" within the maximum number of steps.
 */

#include <algorithm>
#include <cstdio>
#include <cstdlib>
#include <cstring>

using namespace std;

const int MAX_N = 26;
const int MAX_P = 10;

// Used for sorting
int mystrcmp(const void* a, const void* b) {
    return strcmp(*(char * const*)a, *(char * const*)b);
}

// Check for overlap in sorted sets of strings
bool overlap(char** S0, char** S1, int n0, int n1) {
    for (int j0 = 0, j1 = 0; j0 < n0 && j1 < n1; ) {
        int c = strcmp(S0[j0], S1[j1]);
        if      (c < 0) j0++;
        else if (c > 0) j1++;
        else            return true;
    }
    return false;
}

// Apply permutation r to the string s
char* apply(char* s, int N, char* r) {
    char* t = new char[N+1]; 
    for (int k = 0; k < N; k++) t[k] = s[r[k]];
    t[N] = '\0';
    return t;
}

char** applyall(char** S0, int n0, int N, char R0[MAX_P][MAX_N+1], int P) {
    char** T = new char*[n0 * P];
    for (int j = 0, nj = 0; j < n0; j++) 
        for (int i = 0; i < P; i++) 
            T[nj++] = apply(S0[j], N, R0[i]);
    qsort(T, n0 * P, sizeof(char*), mystrcmp);
    for (int j = 0; j < n0; j++) delete[] S0[j];
    delete[] S0;
    return T;
}

int main() {
    int C; scanf("%d", &C);
    for (int c = 0; c < C; c++) {
        int N, P, L; scanf("%d %d %d", &N, &P, &L);
        char** S[2];
        S[0] = new char*[1]; S[0][0] = new char[N+1];
        S[1] = new char*[1]; S[1][0] = new char[N+1];
        scanf("%s %s", S[0][0], S[1][0]);
        int F[2] = {1, 1}; // The number of strings in each set in S
        char R[2][MAX_P][MAX_N+1];
        for (int i = 0; i < P; i++) {
            scanf("%s", R[0][i]);
            for (int j = 0; j < N; j++) {
                R[0][i][j] -= 'a';
                R[1][i][(int)R[0][i][j]]  = j; // Reverse permutation
            }
        }
        int m = 0; // The total number of steps used from both sides
        for ( ; m <= L; m++) {
            if (overlap(S[0], S[1], F[0], F[1])) break;
            S[m%2] = applyall(S[m%2], F[m%2], N, R[m%2], P);
            F[m%2] *= P;
        }
        if (m <= L) printf("%d\n", m);
        else        printf("whalemeat\n");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < F[i]; j++) delete[] S[i][j];
            delete[] S[i];
        }
    }
}
