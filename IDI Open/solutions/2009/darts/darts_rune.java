// Solution for Geometry Darts
// Author Rune Fevang

import java.io.*;
import java.util.*;
public class darts_rune{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(String s){ return Integer.parseInt(s);}
	static StringTokenizer st;
	static void ST() throws Exception { st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		new darts_rune().entry();
	}

	void entry() throws Exception{
		int s = INT(in.readLine());
		Shape[] shs = new Shape[s];
		for(int i = 0; i < s; i++){
			ST();
			char c = st.nextToken().charAt(0);
			if(c == 'C'){
				shs[i] = new Circle(DOUBLE(st.nextToken()), DOUBLE(st.nextToken()), DOUBLE(st.nextToken()));
			}else if(c == 'R'){
				shs[i] = new Rectangle(DOUBLE(st.nextToken()), DOUBLE(st.nextToken()), DOUBLE(st.nextToken()), DOUBLE(st.nextToken()));
			}else{
				shs[i] = new Triangle(DOUBLE(st.nextToken()), DOUBLE(st.nextToken()), DOUBLE(st.nextToken()), DOUBLE(st.nextToken()), DOUBLE(st.nextToken()), DOUBLE(st.nextToken()));
			}
		}

		int n = INT(in.readLine());
		for(int t = 0; t < n; t++){
			int p = 0;
			for(int i = 0; i < 6; i++){
				ST();
				double x = DOUBLE(st.nextToken());
				double y = DOUBLE(st.nextToken());
				for(int j = 0; j < s; j++){
	//				System.out.printf("(%.2f,%.2f) attempts to hit shape %d: %b%n",x,y,j,shs[j].hit(x,y));
					if(shs[j].hit(x,y)) p += i < 3 ? 1 : -1;
				}
			}
			if(p < 0){
				System.out.println("Hannah");
			}else if(p > 0){
				System.out.println("Bob");
			}else{
				System.out.println("Tied");
			}

		}
	}
	static double DOUBLE(String s){ return Double.parseDouble(s);}

	abstract class Shape{
		abstract boolean hit(double x, double y);
	}

	class Circle extends Shape{
		double x, y, r;
		Circle(double xx, double yy, double rr){
			x = xx; y = yy; r = rr;
		}

		boolean hit(double xx, double yy){
			return (xx-x)*(xx-x) + (yy-y)*(yy-y) <= r*r;
		}
	}

	class Rectangle extends Shape{
		double x1, y1, x2, y2;
		Rectangle(double xx1, double yy1, double xx2, double yy2){
			x1 = xx1; y1 = yy1; x2 = xx2; y2 = yy2;
		}
		boolean hit(double x, double y){
			return x >= x1 && x <= x2 && y >= y1 && y <= y2;
		}
	}

	class Triangle extends Shape{
		double[] a = new double[3];
		double[] b = new double[3];
		double[] c = new double[3];
		Triangle(double x1, double y1, double x2, double y2, double x3, double y3){
			set(0,x1,y1,x2,y2);
			set(1,x2,y2,x3,y3);
			set(2,x3,y3,x1,y1);
		}
		void set(int ind, double x1, double y1, double x2, double y2){
			a[ind] = y1-y2;
			b[ind] = x2-x1;
			c[ind] = -(a[ind]*x1 + b[ind]*y1);
		}
		boolean hit(double x, double y){
			int sign = 0;
			for(int i = 0; i < 3; i++){
				double s = a[i]*x + b[i]*y + c[i];
				int msign;
				if(s < 0) msign = -1;
				else if(s > 0) msign = 1;
				else msign = 0;
				if(sign == 0) sign = msign;
				if(msign != sign) return false;
			}
			return true;
		}
	}
}




