import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
@SuppressWarnings("unchecked")
class mobile_rf{

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(Object o){return Integer.parseInt(o.toString());}
	static StringTokenizer st;
	static void ST() throws Exception{st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int T = INT(in.readLine());
		for(int i = 0; i < T; i++){
			System.out.println(new mobile_rf().entry());
		}
	}

	String entry() throws Exception{
		ST();
		int w1 = INT(st.nextToken());
		int h1 = INT(st.nextToken());
		int xs1 = INT(st.nextToken());
		int ys1 = INT(st.nextToken());
		int xe1 = INT(st.nextToken());
		int ye1 = INT(st.nextToken());
		ST();
		int w2 = INT(st.nextToken());
		int h2 = INT(st.nextToken());
		int xs2 = INT(st.nextToken());
		int ys2 = INT(st.nextToken());
		int xe2 = INT(st.nextToken());
		int ye2 = INT(st.nextToken());

		// Fetch intersection intervals for both dimensions independantly
		double[] ival1 = isect(w1,xs1,xe1,w2,xs2,xe2);
		double[] ival2 = isect(h1,ys1,ye1,h2,ys2,ye2);

		// Verify intervals are valid, and overlapping
		if(ival1 == null || ival2 == null || ival1[0] > ival2[1] || ival2[0] > ival1[1]) return "No Collision";

		// Return earliest intersection point
		return Double.toString(max(ival1[0], ival2[0])).replaceAll(",",".");
	}

	double[] isect(int w1, int xs1, int xe1, int w2, int xs2, int xe2){

		// Figure out relative speed
		int rel = (xe2-xs2)-(xe1-xs1);

		if(rel == 0){
			// No relative movement, either they're always overlapping, or they never are
			if(xs1 > xs2+w2 || xs2 > xs1+w1) return null;
			return new double[] {0,1};
		}else if(rel > 0){
			// For simplicity, the rest of the function will assume rect2 is moving to the left relative to rect1.
			// Since that's not the case currently, we switch rectangles
			return isect(w2,xs2,xe2,w1,xs1,xe1);
		}

		// Instead of having two moving line segments, we'll reduce rect2 to a point (x3), moving
		// at s speed towards a non-moving line segment (x1-x2).
		int x1 = xs1;
		int x2 = x1 + w1 + w2;
		int x3 = xs2 + w2;
		double s = -rel;

		// Calculate intersection interval
		double[] ret = new double[] {(x3-x2)/s, (x3-x1)/s};

		// Intersect the interval with [0,1], since that's all we're interested in
		ret[0] = max(ret[0], 0);
		ret[1] = min(ret[1], 1);

		// Finally, check if the interval is still valid
		if(ret[0] > ret[1]) return null;

		return ret;
	}
}