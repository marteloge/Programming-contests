/* Sample solution for Save the Computer!
 * IDI Open 2007
 * Author: Nils Grimsmo
 *
 * Solution:
 *
 * O(c b) by dynamic programming and memoisation.
 *
 * For each budget from zero to the full budget
 *     For each component, find the best
 *         If you can by a component within the bugdet
 *             Take the optimal set of components for the budget which is the
 *             current minus the prize of the component
 *             Find the probability with one more of the component added
 *
 * Keep optimal combo of components for each budget in a matrix D.
 * Keep probability for the optimal combos in an array P.
 *
 * The total probability for a combination is the product of the cummulative
 * probabilities for each component. So we have a product of sums. But we do 
 * not maintain the factors or their foos. If we want to go from x*y*(a+b+c)*z
 * to x*y*(a+b+c+d)*z, we must divide by (a+b+c) and multiply by (a+b+c+d).
 *
 * Cummulative probabilities and factorials are memoized.
 */

#include <cstdio>
#include <cmath>

using namespace std;

const int MAX_c = 1000;
const int MAX_b = 1000;

int    c, b;
double L[MAX_c];            // Expected number of fails
int    R[MAX_c];            // Price
int    D[MAX_b+1][MAX_c];   // How many to buy
double P[MAX_b+1];          // Total probability
double fmem[MAX_b+1];
double pmem[MAX_c][MAX_b+1];

double fac(int k) {
    if (fmem[k] >= 0) return fmem[k];
    if (k < 2) return fmem[k] = 1;
    else       return fmem[k] = k * fac(k - 1);
}

double poisson(double la, int k) {
    return pow(M_E, -la) * pow(la, k) / fac(k);
}

double pcum(int i, int k) {
    if (pmem[i][k] >= 0) return pmem[i][k];
    return pmem[i][k] = poisson(L[i], k) + (k > 0 ? pcum(i, k - 1) : 0.0);
}

int main() {
    int n; scanf("%d", &n);
    for (int a = 0; a < n; a++) {
        scanf("%d %d", &c, &b);
        for (int j = 0; j <= b; j++) fmem[j] = -1.0;
        for (int i = 0; i < c; i++) for(int j = 0; j <= b; j++) pmem[i][j]=-1.0;
        for (int i = 0; i < c; i++) scanf("%lf", &(L[i]));
        for (int i = 0; i < c; i++) scanf("%d", &(R[i]));
        for (int i = 0; i < c; i++) D[0][i] = 0; 
        P[0] = 1.0; for (int i = 0; i < c; i++) P[0] *= pcum(i, 0); 
        for (int j = 1; j <= b; j++) {
            int buy = -1;
            double bestprob = P[j-1];
            for (int i = 0; i < c; i++) { 
                int h = j - R[i];
                if (h >= 0) {
                    double p = P[h] / pcum(i, D[h][i]) * pcum(i, D[h][i] + 1);
                    if (p > bestprob) {
                        bestprob = p;
                        buy = i;
                    }
                }
            }
            P[j] = bestprob;
            for (int i = 0; i < c; i++) {
                if (buy != -1) D[j][i] = D[j-R[buy]][i] + (i == buy ? 1 : 0);
                else           D[j][i] = D[j-1][i];
            }
        }
        printf("%.5f\n", P[b]);
    }
}
