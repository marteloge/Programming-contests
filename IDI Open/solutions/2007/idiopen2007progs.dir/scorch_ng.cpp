/* Sample solution for Scorched earth
 * IDI Open 2007
 * Author: Nils Grimsmo
 *
 * Note: This is a solution for the simplified version of the problem, with the
 * easier limitations on d, as specified in the new PDF. See uglyscorch_ng.cpp.
 *
 * Solution:
 *
 * Separate motion into x and y component, and set up motion equations:
 *
 *     I     (xo-xu) = v_x t + 1/2 w t^2
 *     II    (yo-yu) = v_y t - 1/2 g t^2
 *
 *
 *     I      1/2 w t^2 + v cos(d) + (xu-xo) = 0
 *     II    -1/2 g t^2 + v sin(d) + (yu-yo) = 0 | *= cos(d)/sin(d)
 *
 *     I      1/2 w t^2          + v cos(d) t + (xu-xo)          = 0
 *     II    -1/2 g t^2 / tan(d) + v cos(d) t + (yu-yo) / tan(d) = 0 
 *
 *     I-II  1/2 (w+g/tan(d)) t^2 + (xu-xo) - (yu-yo) / tan(d) = 0
 *           t = sqrt( ((xu-xo) - (yu-yo) / tan(d)) / (1/2 (w+g/tan(d))) )
 *
 *     I     v = (1/2 w t^2 + (xu-xo)) / (cos(d) t)
 */

#include <cstdio>
#include <cstdlib>
#include <cmath>

using namespace std;

const double EPS = 1E-9;
const double g = 9.8;

bool eq(double a, double b) { return abs(a-b) < EPS; }

int main() {
    int n; scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        double xu, yu, xo, yo, w, d;
        scanf("%lf %lf %lf %lf %lf %lf", &xu, &yu, &xo, &yo, &w, &d);
        d = d * M_PI / 180.0;
        double t = sqrt(2.0 * ((xo-xu)-(yo-yu)/tan(d)) / (w+g/tan(d)));
        double v = (-.5*w*t*t + xo-xu) / (cos(d)*t);
        if (0 <= v && v <= 300) {
            printf("%.5lf\n", v + 1E-9);
        } else {
            printf("impossible\n");
        }
    }
}
