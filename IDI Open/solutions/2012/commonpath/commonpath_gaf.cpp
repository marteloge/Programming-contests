#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;


struct Node{
	int i;
	int d;
	Node(){}
	Node(int a, int b){i=a;d=b;}
};

struct Edge{
	int to;
	int w;
	Edge(){}
	Edge(int a, int b){to=a;w=b;}
};

void dijkstra(const vector<Edge> edge[], int start, int dist[]);

int main()
{
	int T;
	cin >> T;
	while(T--)
	{
		int N, M;
		cin >> N >> M;
		int S, P, Q;
		cin >> S >> P >> Q;
		vector<Edge> edge[2000];
		for(int i = 0; i < M; ++i)
		{
			int fr, to, t;
			cin >> fr >> to >> t;
			edge[fr].push_back(Edge(to, t));
			edge[to].push_back(Edge(fr, t));
		}

		// All distances from school
		int distS[2000];
		dijkstra(edge, S, distS);

		// All distances from home1
		int distP[2000];
		dijkstra(edge, P, distP);

		// All distances from home2
		int distQ[2000];
		dijkstra(edge, Q, distQ);

		// Find out where they leave each other
		int maxTime = 0;
		int minD1 = distS[P];
		int minD2 = distS[Q];
		for(int i = 0; i < N; ++i)
		{
			if(((distS[i]+distP[i]) == distS[P]) && ((distS[i]+distQ[i]) == distS[Q]))
			{
				maxTime = max(maxTime, distS[i]);
			}
		}
		cout << maxTime << endl;
	}


}


class cmp{
public:
	bool operator()(const Node& a, const Node& b) const
	{
		return a.d > b.d;
	}
};

void dijkstra(const vector<Edge> edge[], int start, int dist[])
{
	vector<Node> heap;
	bool finished[2000];
	int superBig = (1<<29);
	for(int i = 0; i < 2000; ++i)
	{
		dist[i] = superBig;
		finished[i] = false;
	}
	dist[start] = 0;
	heap.push_back(Node(start,0));
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
					int d = curr.d+edge[curr.i][i].w;
					if(d < dist[edge[curr.i][i].to])
					{
						dist[edge[curr.i][i].to] = d;
						heap.push_back(Node(edge[curr.i][i].to, d));
						push_heap(heap.begin(), heap.end(), cmp());
					}
				}
			}
				
		}
	}
}

