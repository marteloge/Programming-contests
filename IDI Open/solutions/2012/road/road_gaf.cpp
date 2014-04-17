#include <iostream>
#include <vector>
#include <cmath>
#include <list>
#include <algorithm>
#include <cassert>

using namespace std;

#define PI 3.141592653589793

struct Point;
struct Circle;
struct Line;
struct Node;
struct Edge;


std::vector<Line> circleCircleTangents(Circle c1, Circle c2, bool chngOrder);
void findTangents(Circle c, Point p, Point& pt1, Point& pt2);
void findCircleCircleIntersections(Circle c, Point p, double L, Point& pt1, Point& pt2);
double length(Line l);
bool isLegal(Line tmpL, Circle cs[], int N, int cSt, int cEn);

struct Point
{
	double x;
	double y;

	Point(){}
	Point(double a, double b){
		x = a;
		y = b;
	}
};

struct Circle
{
	Point p;
	double r;

	Circle(){}
	Circle(double a, double b, double c){
		p.x=a;
		p.y=b;
		r=c;
	}
};

struct Line
{
	Point pt0;
	Point pt1;
	Line(){}
	Line(Point a, Point b){
		pt0 = a;
		pt1 = b;
	}
};

struct Node{
	int i;
	double d;
};

class cmp{
	public:
	bool operator()(const Node& a, const Node& b) const
	{
		return a.d > b.d;
	}
};

struct Edge{
	int to;
	double w;
};


int main()
{
	int T;
	cin >> T;
	while(T--)
	{

		// Problem desc
		double W, L;
		int N;
		cin >> N >> W >> L;
		Circle cs[100];
		for(int i = 0; i < N; ++i)
		{
			cin >> cs[i].p.x >> cs[i].p.y >> cs[i].r;
		}
	
		// Start to generate graph
		vector<Edge> edge[2+4*100*101];

		// Add edge from start to end
		Edge startEnd;
		startEnd.to = 1;
		startEnd.w = L;
		Line veryTmp(Point(W/2,0),Point(W/2,L));
		if(isLegal(veryTmp, cs, N, -1, -1))
		{
			edge[0].push_back(startEnd);
		}

	
		// Add edges from/to each circle and from/to start+end
		double ang[4*101];
		Point start(W/2, 0);
		Point end(W/2, L);
		for(int cSt = 0; cSt < N; ++cSt)
		{
			// Lines from start
			Point pt1;
			Point pt2;
			findTangents(cs[cSt], start, pt1, pt2);
			Line tmpL = Line(start, pt1);
			ang[0] = atan2(pt1.y-cs[cSt].p.y,pt1.x-cs[cSt].p.x);
			if(isLegal(tmpL, cs, N, cSt, -1))
			{
				Edge tmpE;
				tmpE.to = 2 + 4*(N+1)*cSt;
				tmpE.w = length(tmpL);
				edge[0].push_back(tmpE);
			}
			tmpL = Line(start, pt2);
			ang[1] = atan2(pt2.y-cs[cSt].p.y,pt2.x-cs[cSt].p.x);
			if(isLegal(tmpL, cs, N, cSt, -1))
			{
				Edge tmpE;
				tmpE.to = 2 + 4*(N+1)*cSt+1;
				tmpE.w = length(tmpL);
				edge[0].push_back(tmpE);
			}

			// Check all lines to circles
			for(int cEn = 0; cEn < N; ++cEn)
			{
				if(cEn == cSt){
				       	for(int i = 0; i < 4; ++i)
				       	{
						ang[4+cEn*4+i] = 0;
					}
					continue;
				}
				vector<Line> test = circleCircleTangents(cs[cSt],cs[cEn], (cEn < cSt));
				for(int i = 0; i < 4; ++i)
				{
					ang[4+4*cEn+i] = atan2(test[i].pt0.y-cs[cSt].p.y,test[i].pt0.x-cs[cSt].p.x);
					if(isLegal(test[i], cs, N, cSt, cEn))
					{
						Edge tmpE;
						tmpE.to = 2+4*(N+1)*cEn+(4+4*cSt+i);
						tmpE.w = length(test[i]);
						edge[2+4*(N+1)*cSt+(4+4*cEn+i)].push_back(tmpE);
					}
				}
			}

			// Check lines to end point
			findTangents(cs[cSt], end, pt1, pt2);
			tmpL = Line(pt1, end);
			ang[2] = atan2(pt1.y-cs[cSt].p.y,pt1.x-cs[cSt].p.x);
			if(isLegal(tmpL, cs, N, cSt, -1))
			{
				Edge tmpE;
				tmpE.to = 1;
				tmpE.w = length(tmpL);
				edge[2+4*(N+1)*cSt+2].push_back(tmpE);
			}
			tmpL = Line(pt2, end);
			ang[3] = atan2(pt2.y-cs[cSt].p.y,pt2.x-cs[cSt].p.x);
			if(isLegal(tmpL, cs, N, cSt, -1))
			{
				Edge tmpE;
				tmpE.to = 1;
				tmpE.w = length(tmpL);
				edge[2+4*(N+1)*cSt+3].push_back(tmpE);
			}

			// Find distances between every pair of nodes on circle
			for(int i = 0; i < (4+4*N); ++i)
			{
				for(int j = 0; j < (4+4*N); ++j)
				{
					double d = min(abs(ang[i]-ang[j]), abs(2*PI-(ang[i]-ang[j])))*cs[cSt].r;
					Edge tmpE;
					tmpE.to = 2+4*(N+1)*cSt+i;
					tmpE.w = d;
					edge[2+4*(N+1)*cSt+j].push_back(tmpE);
				}
			}
		}

		// Do shortest path!
		vector<Node> heap;
		bool finished[2+4*100*101];
		double dist[2+4*100*101];
		int prev[2+4*100*101];
		double superBig = 1e160;
		for(int i = 0; i < (2+4*N*(N+1)); ++i){
			dist[i] = superBig;
			finished[i] = false;
			prev[i] = -1;
		}
		dist[0] = 0;
		prev[0] = 0;
		Node tmpNode;
		tmpNode.i = 0;
		tmpNode.d = 0.0;
		heap.push_back(tmpNode);
	
		while(!heap.empty())
		{
			Node curr = heap.front();
			pop_heap(heap.begin(),heap.end(), cmp());
			heap.pop_back();
			if(!finished[curr.i])
			{
				finished[curr.i] = true;
				for(int i = 0; i < edge[curr.i].size(); ++i)
				{
					if(!finished[edge[curr.i][i].to])
					{
						double d = curr.d+edge[curr.i][i].w;
						if(d < dist[edge[curr.i][i].to])
						{
							dist[edge[curr.i][i].to] = d;
							prev[edge[curr.i][i].to] = curr.i;
							tmpNode.i = edge[curr.i][i].to;
							tmpNode.d = d;
							heap.push_back(tmpNode);
							push_heap(heap.begin(), heap.end(), cmp());

						}
					}
				}
					
			}
		}

		cout.precision(16);
		cout.setf(ios::fixed | ios::showpoint);
		cout << dist[1] << endl;
	}

}

		
std::vector<Line> circleCircleTangents(Circle c1, Circle c2, bool chngOrder)
{
	bool changed = false;
	if(c1.r > c2.r || (c1.r == c2.r && chngOrder))
	{
		Circle tmp = c1;
		c1 = c2;
		c2 = tmp;
		changed = true;
	}
 

	// Find outer tangents
	double r2a = c2.r-c1.r;
	Point outer1;
	Point outer2;
	Circle tmpCir = c2;
	tmpCir.r = r2a;
	findTangents(tmpCir, c1.p, outer1, outer2);
	
	// Need to shift lines to get first tangent
	double v1x = -(outer1.y-c1.p.y);
	double v1y = outer1.x-c1.p.x;
	double v1Len = sqrt(pow(v1x,2)+pow(v1y,2));
	v1x *= c1.r/v1Len;
	v1y *= c1.r/v1Len;

	// Do offset
	Point outer1_1 = Point(c1.p.x+v1x, c1.p.y+v1y);
	Point outer1_2 = Point(outer1.x+v1x, outer1.y+v1y);

	// Need to shift line to get second tangent
	double v2x = outer2.y-c1.p.y;
	double v2y = -(outer2.x-c1.p.x);
	double v2Len = sqrt(pow(v2x,2)+pow(v2y,2));
	v2x *= c1.r/v2Len;
	v2y *= c1.r/v2Len;

	// Do offset
	Point outer2_1 = Point(c1.p.x+v2x, c1.p.y+v2y);
	Point outer2_2 = Point(outer2.x+v2x, outer2.y+v2y);

	// Find inner tangents
	double r1a = c1.r+c2.r;
	Point inner1;
	Point inner2;
	tmpCir = c1;
	tmpCir.r = r1a;
	findTangents(tmpCir, c2.p, inner1, inner2);

	v1x = inner1.y - c2.p.y;
        v1y = -(inner1.x - c2.p.x);
        v1Len = sqrt(pow(v1x,2)+pow(v1y,2));
	v1x *= c2.r/v1Len;
        v1y *= c2.r/v1Len;
	
	// Offset the tangent vector
        Point inner1_1 = Point(c2.p.x + v1x, c2.p.y + v1y);
        Point inner1_2 = Point(inner1.x + v1x, inner1.y + v1y);

	// Do second inner tangent
        v2x = -(inner2.y - c2.p.y);
        v2y = inner2.x - c2.p.x;
        v2Len = sqrt(pow(v2x,2)+pow(v2y,2));
        v2x *= c2.r/v2Len;
        v2y *= c2.r/v2Len;
	
	// Offset
        Point inner2_1 = Point(c2.p.x + v2x, c2.p.y + v2y);
        Point inner2_2 = Point(inner2.x + v2x, inner2.y + v2y);

        vector<Line> ret;
	if(!changed)
	{
		ret.push_back(Line(outer1_1, outer1_2));
		ret.push_back(Line(outer2_1, outer2_2));
		ret.push_back(Line(inner1_2, inner1_1));
		ret.push_back(Line(inner2_2, inner2_1));
	}
	else
	{
		ret.push_back(Line(outer1_2, outer1_1));
		ret.push_back(Line(outer2_2, outer2_1));
		ret.push_back(Line(inner1_1, inner1_2));
		ret.push_back(Line(inner2_1, inner2_2));
	}
	return ret;
}

void findTangents(Circle c, Point p, Point& pt1, Point& pt2)
{

	double dx = c.p.x-p.x;
	double dy = c.p.y-p.y;
	double d2 = dx*dx+dy*dy;
	
	double L = sqrt(abs(d2-c.r*c.r));

	findCircleCircleIntersections(c, p, L, pt1, pt2);
	return;
}

void findCircleCircleIntersections(Circle c, Point p, double L, Point& pt1, Point& pt2)
{
	double dx = c.p.x-p.x;
	double dy = c.p.y-p.y;
	double d = sqrt(pow(dx,2)+pow(dy,2));

	double a = (c.r*c.r-L*L+d*d)/(2.0*d);
	double h = sqrt(abs(c.r*c.r-a*a));
	
	double cx2 = c.p.x + a*(p.x-c.p.x)/d;
	double cy2 = c.p.y + a*(p.y-c.p.y)/d;

	pt1 = Point(cx2 + h*(p.y-c.p.y)/d, cy2 - h*(p.x-c.p.x)/d);
	pt2 = Point(cx2 - h*(p.y-c.p.y)/d, cy2 + h*(p.x-c.p.x)/d);

	// Check for only 1 solution?
	return;
}


double length(Line l)
{
	return sqrt(pow(l.pt0.x-l.pt1.x,2)+pow(l.pt0.y-l.pt1.y,2));
}

bool isLegal(Line tmpL, Circle cs[], int N, int cSt, int cEn)
{
	for(int i = 0; i < N; ++i)
	{
		if(i != cSt && i != cEn)
		{
			Point sv(tmpL.pt1.x-tmpL.pt0.x, tmpL.pt1.y-tmpL.pt0.y);
			Point pv(cs[i].p.x-tmpL.pt0.x, cs[i].p.y-tmpL.pt0.y);
			double len = sv.x*sv.x+sv.y*sv.y;
			assert( len >0 && "Terrible things have happened in line circle intersection checking");
			len = sqrt(len);
			Point svu(sv.x/len, sv.y/len);
			double proj = pv.x*svu.x+pv.y*svu.y;
			Point close;
			if(proj <= 0)
			{
				close = tmpL.pt0;
			}
			else if(proj >= len)
			{
				close = tmpL.pt1;
			}
			else
			{
				close = Point(svu.x*proj, svu.y*proj);
				close = Point(close.x+tmpL.pt0.x, close.y+tmpL.pt0.y);
			}
			Point d(cs[i].p.x-close.x, cs[i].p.y-close.y);
			double tmpLen = d.x*d.x+d.y*d.y;
			assert( tmpLen >= 0 && "Terrible things have happend");
			if( sqrt(tmpLen) < cs[i].r){
				return false;
			}
		}
	}
	return true;
}
