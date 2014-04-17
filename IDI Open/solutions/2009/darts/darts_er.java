/*
 * Author: Eirik Reksten
 * 
 * Solution for Geometry Darts from IDI Open 2009
 * 
 * This is solved by brute force. For each dart, iterate through all the shapes
 * and check whether it hits or not.
 * 
 */

import java.io.*;
import java.util.*;

public class darts_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}

	
	public static void main(String[] args) throws Exception {
		int s = INT();
		Shape[] shapes = new Shape[s];
		for(int i = 0;i<s;i++) {
			String type = TOKEN();
			if("C".equals(type))shapes[i] = new Circle(DOUBLE(),DOUBLE(),DOUBLE());
			else if("R".equals(type))shapes[i] = new Rectangle(DOUBLE(),DOUBLE(),DOUBLE(),DOUBLE());
			else shapes[i] = new Triangle(DOUBLE(),DOUBLE(),DOUBLE(),DOUBLE(),DOUBLE(),DOUBLE());
		}
		int n = INT();
		while(n-->0)new darts_er().go(shapes);
	}
	
	public void go(Shape[] shapes) throws Exception {
		int bob = 0, hannah = 0;
		
		for(int i = 0;i<3;i++)bob += count(shapes, new double[] {DOUBLE(),DOUBLE()});
		for(int i = 0;i<3;i++)hannah += count(shapes, new double[] {DOUBLE(),DOUBLE()});
		
		if(bob>hannah)System.out.println("Bob");
		else if(bob<hannah)System.out.println("Hannah");
		else System.out.println("Tied");
		
	}
	
	public int count(Shape[] shapes, double[] dart) {
		int cnt = 0;
		for(Shape s : shapes)if(s.dartHits(dart))cnt++;
		return cnt;
	}
	
//	************ Classes for the different shapes **************
	
	private static interface Shape {
		public boolean dartHits(double[] dart);
	}
	
	private static class Circle implements Shape {
		double[] center;
		double radius;
		
		Circle(double x, double y, double r) {
			center = new double[] {x,y};
			radius = r;
		}
		
		public boolean dartHits(double[] dart) {
			double dx = dart[0]-center[0];
			double dy = dart[1]-center[1];
			double sqDist = dx*dx+dy*dy;
			double sqRad = radius*radius;
			return sqDist<sqRad;
		}
		
	}
	
	private static class Rectangle implements Shape {
		double lx, ux, ly, uy;
		
		Rectangle(double x1, double y1, double x2, double y2) {
			lx = x1; ux = x2; ly = y1; uy = y2;
		}
		
		public boolean dartHits(double[] dart) {
			if(dart[0]>ux)return false;
			if(dart[0]<lx)return false;
			if(dart[1]>uy)return false;
			if(dart[1]<ly)return false;
			return true;
		}
	}
	
	private static class Triangle implements Shape {
		
		double[] A, B, C;
		
		Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
			A = new double[] {x1,y1};
			B = new double[] {x2,y2};
			C = new double[] {x3,y3};
		}
		
		public boolean dartHits(double[] dart) {
			int side1 = java.awt.geom.Line2D.relativeCCW(A[0], A[1], B[0], B[1], dart[0], dart[1]);
			int side2 = java.awt.geom.Line2D.relativeCCW(B[0], B[1], C[0], C[1], dart[0], dart[1]);
			int side3 = java.awt.geom.Line2D.relativeCCW(C[0], C[1], A[0], A[1], dart[0], dart[1]);
			return side1==side2 && side1==side3;
		}
	}

}
