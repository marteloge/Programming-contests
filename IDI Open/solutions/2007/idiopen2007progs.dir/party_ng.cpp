/* Sample solution for Party
 * IDI Open 2007
 * Author: Nils Grimsmo
 *
 * Solution: 
 * Make a bipartite graph with boys on the left and girls on the right. Make an
 * edge with capacity 1 between boys and girls matchable. Let the capacity from
 * a source to a boy be one, and the capacity from a girl to the sink be equal
 * to the number of parties. Gradually increase the number of parties if 
 * necessary to be able to send a flow equal to the number of boys through the
 * network, if possible. Find open paths by a DFS search.
 *
 * O((m+f)^3)
 */

#include <cstdio>

const int MAX_V = 200;
int s, t, m, f, V; 
int C[MAX_V][MAX_V]; // Capacity
int F[MAX_V][MAX_V]; // Flow
bool S[MAX_V];       // Seen nodes in DFS search

bool findpath(int u, int w) {
    S[u] = true; // Mark node as seen
    if (u == w) return true; // This is the sink, we are done
    for (int v = 0; v < V; v++) {
        // Unseen node, available capacity, and rest of path found recursively
        if (!S[v] && F[u][v] < C[u][v] && findpath(v, w)) {
            F[u][v]++; F[v][u]--; return true; 
        } 
    }
    return false;
}

bool augment() {
    for (int k = 0; k < V; k++) S[k] = false; // No node seen yet
    return findpath(s, t);
}

int boy(int i)  { return 1 + i; }
int girl(int j) { return 1 + m + j; }

int main() {
    int n; scanf("%d", &n);
    for (int a = 0; a < n; a++) {
        scanf("%d %d", &m, &f);
        V = 1 + m + f + 1; 
        s = 0; t = V - 1;
        for (int i = 0; i < V; i++) for (int j = 0; j < V; j++) 
            C[i][j] = F[i][j] = 0;
        for (int i = 0; i < m; i++) 
            C[s][boy(i)] = 1;
        for (int j = 0; j < f; j++) {
            int p; scanf("%d", &p);
            for (int k = 0; k < p; k++) {
                int b; scanf("%d", &b);
                C[boy(b)][girl(j)] = 1; } }
        int matched = 0, parties = 0;
        while (parties <= m && matched < m) {
            if (augment()) {
                matched++;
            } else {
                parties++;
                for (int j = 0; j < f; j++) C[girl(j)][t] = parties;
            }
        }
        if (matched == m) printf("%d\n", parties);
        else printf("impossible\n");
    }
}
