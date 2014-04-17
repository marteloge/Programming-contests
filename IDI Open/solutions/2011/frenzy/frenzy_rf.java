import java.util.*;
import java.io.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
@SuppressWarnings("unchecked")
class frenzy_rf{

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(Object o){return Integer.parseInt(o.toString());}
	static StringTokenizer st;
	static void ST() throws Exception{st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int T = INT(in.readLine());
		for(int i = 0; i < T; i++){
			new frenzy_rf().entry();
		}
	}

	int mx, my;
	char[][] map;
	int[][] id;
	int sheep;
	int[][] ds;
	int inf = Integer.MAX_VALUE/2;
	int[] fetch(int x, int y){
		int[] ret = new int[sheep];
		Arrays.fill(ret, inf);
		LinkedList<int[]> que = new LinkedList();
		LinkedList<int[]> next = new LinkedList();
		que.add(new int[] {x,y});
		boolean[][] been = new boolean[mx][my];
		int cost = 0;
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		while(true){
			if(que.size() == 0){
				if(next.size() == 0) break;
				cost++;
				que = next;
				next = new LinkedList();
			}
			int[] at = que.removeFirst();
			if(been[at[0]][at[1]]) continue;
			been[at[0]][at[1]] = true;
			if(map[at[0]][at[1]] == '#') ret[id[at[0]][at[1]]] = cost;
			for(int i = 0; i < 4; i++){
				int nx = at[0]+dx[i];
				int ny = at[1]+dy[i];
				if(nx < 0 || ny < 0 || nx >= mx || ny >= my || map[nx][ny] == 'X') continue;
				next.add(new int[] {nx,ny});
			}
		}
		return ret;
	}

	void entry() throws Exception{
		ST();
		mx = INT(st.nextToken());
		my = INT(st.nextToken());
		map = new char[mx][my];
		id = new int[mx][my];
		for(int i = 0; i < mx; i++){
			Arrays.fill(id[i], -1);
			map[i] = in.readLine().toCharArray();
			for(int j = 0; j < my; j++){
				if(map[i][j] == '#'){
					id[i][j] = sheep++;
				}
			}
		}
		int[] sd = null;
		ds = new int[sheep][];
		int at = 0;
		for(int i = 0; i < mx; i++){
			for(int j = 0; j < my; j++){
				if(map[i][j] == 'U') sd = fetch(i,j);
				if(map[i][j] == '#'){
					ds[at++] = fetch(i,j);
				}
			}
		}
		memo = new int[1<<sheep][sheep];
		int best = inf;
		for(int i = 0; i < ds.length; i++){
			best = Math.min(best, sd[i]+go(1<<i, i));
		}
		if(best >= inf) System.out.println("impossible");
		else System.out.println(best+sheep);
	}

	int[][] memo;
	int go(int msk, int at){
		if(memo[msk][at] != 0) return memo[msk][at]-1;
		if(msk == (1<<sheep)-1) return 0;
		int ret = inf;
		for(int i = 0; i < sheep; i++){
			if(((1<<i)&msk)!=0) continue;
			ret = Math.min(ret, ds[at][i] + go(msk | (1<<i), i));
		}
		memo[msk][at] = ret+1;
		return ret;
	}

	class State implements Comparable<State>{
		int msk, at, c;
		State(int m, int a, int cc){msk = m; at = a; c = cc;}
		public int compareTo(State s){
			return c - s.c;
		}
	}
}
