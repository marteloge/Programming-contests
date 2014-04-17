/*	solution by ruben spaans
		precalculate all the following distances using dijkstra's algorithm:
		- from school to all nodes
		- from per's home to all nodes
		- from paal's home to all nodes
		then for every node v, let it be the node where paths diverge.
		-	if shortest path to each home is still minimal when forced to go via v,
			update answer
		time complexity: running 3 dijkstra's: O(M+N log N).
*/
#include <stdio.h>
#include <string.h>

#define MAXH 20001

int heap[MAXH];   /*  heap value (on which it is sorted)  */
int heapid[MAXH]; /*  heap id */
int heapn;        /*  (number of elements in heap)+1 */

void heapinsert(int val,int id) {
  int pos=heapn++;
  while(pos>1 && val<heap[pos>>1]) {
    heap[pos]=heap[pos>>1];
    heapid[pos]=heapid[pos>>1];
    pos>>=1;
  }
  heap[pos]=val; heapid[pos]=id;
}

void heapremove(int *val,int *id) {
  int pos=1,next=1,newval,newid;
  *val=heap[1]; *id=heapid[1];
  newval=heap[--heapn];
  newid=heapid[heapn];
  while((pos<<1)<=heapn) {
    if((pos<<1)==heapn) next=pos<<1;
    else next=(pos<<1)+(heap[pos<<1]>heap[(pos<<1)+1]);
    if(heap[next]>=newval) break;
    heap[pos]=heap[next];
    heapid[pos]=heapid[next];
    pos=next;
  }
  heap[pos]=newval;
  heapid[pos]=newid;
}


#define MAXV 2000
#define MAXE 20000

int n,ne;               /*  number of nodes, number of edges */
int from[MAXE],to[MAXE];/*  edge i: from[i] to[i] */
int cost[MAXE];         /*  cost of edge from[i] -> to[i] */
int gs[MAXV+1];         /*  gs[i]: start of edges from i, gs[i+1]: end */

int distu[MAXV],dista[MAXV],distr[MAXV];

/*	dubious dijkstra: no decrease-key used, but heap can contain same node
		multiple times */
void dijkstra(int start,int *dist) {
  static char vis[MAXV];    /*  array indicating visited (processed) nodes */
  int val,w,cur,i,x2;
  memset(vis,0,n);
  memset(dist,126,n*sizeof(int));
  dist[start]=0;              /*  set distance to start to 0 */
  heapn=1;
  heapinsert(0,start);        /*  insert start node to heap */
  while(heapn>1) {
    heapremove(&val,&cur);
    if(vis[cur]) continue;    /*  if current node is already done: continue */
    vis[cur]=1;
    for(i=gs[cur];i<gs[cur+1];i++) {
      x2=to[i];
      if(vis[x2]) continue;
      w=val+cost[i];
      if(dist[x2]>w) {
        dist[x2]=w;
        heapinsert(w,x2);
      }
    }
  }
}

void countingsort() {
  static int newto[MAXE];
  static int newcost[MAXE];
  int i,j;
  for(i=0;i<=n;i++) gs[i]=0;
  for(i=0;i<ne;i++) gs[from[i]]++;
  for(i=1;i<n;i++) gs[i]+=gs[i-1];
  gs[n]=ne;
  for(i=0;i<ne;i++) {
    j=--gs[from[i]];
    newto[j]=to[i];
    newcost[j]=cost[i];
  }
  for(i=0;i<ne;i++) to[i]=newto[i],cost[i]=newcost[i];
  for(i=0;i<n;i++) for(j=gs[i];j<gs[i+1];j++) from[j]=i;
}

int main() {
	int T,m,i,a,b,c;
	int U,A,R;
	int best;
	scanf("%d",&T);
	while(T--) {
		scanf("%d %d",&n,&m);
		scanf("%d %d %d",&U,&A,&R);
		for(ne=i=0;i<m;i++) {
			scanf("%d %d %d",&a,&b,&c);
			/*	add both directions */
			from[ne]=a; to[ne]=b; cost[ne++]=c;
			from[ne]=b; to[ne]=a; cost[ne++]=c;
		}
		/*	sort edges */
		countingsort();
		/*	dijkstra from our points of interest */
		dijkstra(U,distu);
		dijkstra(A,dista);
		dijkstra(R,distr);
		/*	find answer */
		for(best=0,i=0;i<n;i++) {
			if(distu[i]+dista[i]==distu[A] && distu[i]+distr[i]==distu[R] && best<distu[i])
				best=distu[i];
		}
		printf("%d\n",best);
	}
	return 0;
}
