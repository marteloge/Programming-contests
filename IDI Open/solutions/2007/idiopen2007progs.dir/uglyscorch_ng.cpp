/* Sample solution for Scorched earth
 * IDI Open 2007
 * Author: Nils Grimsmo
 *
 * Solution:
 *
 * Separate motion into x and y component, and set up motion equations:
 *
 * I     (xo-xu) = v_x t + 1/2 w t^2
 * II    (yo-yu) = v_y t - 1/2 g t^2
 *
 * If cos(d) = 0:
 *
 *     I      1/2 w t^2              + (xu-xo) = 0
 *     II    -1/2 g t^2 + v sin(d) t + (yu-yo) = 0
 *
 *     I     t = sqrt( (xo-xu) / (1/2 w) )
 *
 *     II    v = (1/2 g t^2 + yo-yu) / (sin(d) t)
 *
 * Or if sin(d) = 0:
 *
 *     I      1/2 w t^2 + v cos(d) t + (xu-xo) = 0
 *     II    -1/2 g t^2              + (yu-yo) = 0
 *
 *     I     t = sqrt( (yo-yu) / (-1/2 g) )
 *       
 *     II    v = (-1/2 w t^2 + xo-xu) / (cos(d) t)
 *
 * Or in general:
 *
 *     I      1/2 w t^2 + v cos(d) t + (xu-xo) = 0
 *     II    -1/2 g t^2 + v sin(d) t + (yu-yo) = 0 | *= cos(d)/sin(d)
 *
 *     I      1/2 w t^2          + v cos(d) t + (xu-xo)          = 0
 *     II    -1/2 g t^2 / tan(d) + v cos(d) t + (yu-yo) / tan(d) = 0 
 *
 *     I-II  1/2 (w+g/tan(d)) t^2 + (xu-xo) - (yu-yo) / tan(d) = 0
 *           t = sqrt( ((xu-xo) - (yu-yo) / tan(d)) / (1/2 (w+g/tan(d))) )
 *
 *     I     v = (1/2 w t^2 + (xu-xo)) / (cos(d) t)
 *
 * If (w+g/tan(d)) = 0, then -g/w = tan(d)), and there is no single answer, as
 * the accelleration on the missile has the same direction as the initial
 * speed. This is unless the target is at the top of the curve for the maximum
 * velocity.
 *
 *     From classical mechanics:
 *         v_{final}^2 = v_{start}^2 + 2 a s 
 *
 *     In y direction, find speed we must start with to end up with zero at the
 *     top position.
 *
 *         0^2 = (v*sin(d))^2 - 2 g (yo - yu)
 *           v = sqrt( 2 g (yo - yu) / sin(d)^2 )
 */

#include <cstdio>
#include <cstdlib>
#include <cmath>

using namespace std;

const double EPS = 1E-9;
const double g = 9.8;
const double MAX_v = 300.0;

bool eq(double a, double b) { return abs(a-b) < EPS; }

int main() {
    int n; scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        double xu, yu, xo, yo, w, d;
        scanf("%lf %lf %lf %lf %lf %lf", &xu, &yu, &xo, &yo, &w, &d);
        d = d * M_PI / 180.0;
        if (eq(w+g/tan(d), 0)) {
            if (eq(w/-g, (xo-xu)/(yo-yu))) {
                double v = sqrt( 2*g*(yo-yu) / (sin(d)*sin(d)) );
                if (eq(v, MAX_v)) {
                    printf("%.5lf\n", MAX_v);
                } else if (v < 0 || MAX_v < v) {
                    printf("impossible\n");
                } else if (v < MAX_v) {
                    printf("%.5f:%.5f\n", v, MAX_v);
                } else {
                    printf("%.5f:%.5f\n", 0.0, MAX_v);
                }
            } else {
                printf("impossible\n");
            }
        } else {
            double t, v;
            if (eq(cos(d), 0)) {
                t = sqrt((xo-xu) / ( .5*w));
                v = ( .5*g*t*t + yo-yu) / (sin(d)*t);
            } else if (eq(sin(d), 0)) {
                t = sqrt((yo-yu) / (-.5*g));
                v = (-.5*w*t*t + xo-xu) / (cos(d)*t);
            } else {
                t = sqrt(2.0 * ((xo-xu)-(yo-yu)/tan(d)) / (w+g/tan(d)));
                v = (-.5*w*t*t + xo-xu) / (cos(d)*t);
            } 
            if (0 <= v && v <= 300) {
                printf("%.5lf\n", v + 1E-9);
            } else {
                printf("impossible\n");
            }
        }
    }
}
