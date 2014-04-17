// Author: Børge Nordli
//
// Straightforward object-oriented implementation.

import java.util.ArrayList;
import java.util.Scanner;

public class darts_bn {
  static ArrayList<Shape> shapes = new ArrayList<Shape>();
  static int Score(double x, double y) {
    int score = 0;
    for (Shape s : shapes) {
      if (s.Contains(x, y)) ++score;
    }
    return score;
  }

  public static void main(String[] args) {
    for (int i = in.nextInt(); i --> 0;) {
      String t = in.next();
      if (t.equals("C")) {
        shapes.add(new Circle(in.nextDouble(), in.nextDouble(), in.nextDouble()));
      } else if (t.equals("R")) {
        shapes.add(new Rectangle(in.nextDouble(), in.nextDouble(),
                                 in.nextDouble(), in.nextDouble()));
      } else if (t.equals("T")) {
        shapes.add(new Triangle(in.nextDouble(), in.nextDouble(),
                                in.nextDouble(), in.nextDouble(),
                                in.nextDouble(), in.nextDouble()));
      }
    }
    int g = in.nextInt();
    for (int i = 0; i < g; ++i) {
      int b = 0, h = 0;
      b += Score(in.nextDouble(), in.nextDouble());
      b += Score(in.nextDouble(), in.nextDouble());
      b += Score(in.nextDouble(), in.nextDouble());
      h += Score(in.nextDouble(), in.nextDouble());
      h += Score(in.nextDouble(), in.nextDouble());
      h += Score(in.nextDouble(), in.nextDouble());
      System.out.println(b > h ? "Bob" : b == h ? "Tied" : "Hannah");
    }
  }

  static Scanner in = new Scanner(System.in);
}

interface Shape {
  public boolean Contains(double x, double y);
}

class Circle implements Shape {
  double x, y, rr;
  public Circle(double x, double y, double r) {
    this.x = x; this.y = y; this.rr = r*r;
  }
  public boolean Contains(double x, double y) {
    return ((x - this.x)*(x - this.x) +
            (y - this.y)*(y - this.y)) < rr;
  }
}

class Rectangle implements Shape {
  double x1, y1, x2, y2;
  public Rectangle(double x1, double y1,
                   double x2, double y2) {
    this.x1 = x1; this.y1 = y1;
    this.x2 = x2; this.y2 = y2;
  }
  public boolean Contains(double x, double y) {
    return (x1 < x && x < x2) &&
           (y1 < y && y < y2);
  }
}

class Triangle implements Shape {
  double x1, y1, x2, y2, x3, y3, area;
  public Triangle(double x1, double y1,
                  double x2, double y2,
                  double x3, double y3) {
    this.x1 = x1; this.y1 = y1;
    this.x2 = x2; this.y2 = y2;
    this.x3 = x3; this.y3 = y3;
    this.area = Area2(x1, y1, x2, y2, x3, y3);
  }
  public boolean Contains(double x, double y) {
    return Math.abs(Area2(x1, y1, x2, y2, x, y) +
                    Area2(x1, y1, x3, y3, x, y) +
                    Area2(x2, y2, x3, y3, x, y) -
                    area) < 1e-7;
  }
  // http://mathworld.wolfram.com/TriangleArea.html.
  static double Area2(double x1, double y1,
                      double x2, double y2,
                      double x3, double y3) {
    return Math.abs(x1*(y2 - y3) + x2*(y3 - y1) + x3*(y1 - y2));
  }
}
