/*
 * Solution for Problem F - Nurikabe
 * 
 * Running a BFS for each island, and finally for the water.
 * Then iterate through the map to check that you don't have
 * disconnected islands/water anywhere, and also look for 2x2 blocks.
 * 
 * O(N*M)
 *  
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;

public class nurikabe_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static String LINE() throws Exception { return stdin.readLine(); }
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static int[] dx = new int[] {-1,0,1,0}, dy = new int[] {0,-1,0,1};
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			int N = INT(), M = INT();
			char[][] board = new char[N][];
			for(int i = 0;i<N;i++)board[i] = TOKEN().toCharArray();
			if(new nurikabe_er().go(board))System.out.println("YES");
			else System.out.println("NO");
		}
	}
	
	public boolean go(char[][] board) {
		for(int i = 0;i<board.length-1;i++)for(int j = 0;j<board[i].length-1;j++)
			if(board[i][j]=='#' && board[i][j+1]=='#' && board[i+1][j]=='#' && board[i+1][j+1]=='#')return false;
		markWater(board);
		for(int i = 0;i<board.length;i++)for(int j = 0;j<board[i].length;j++)
			if(!checkIsland(board, i, j))return false;
		for(int i = 0;i<board.length;i++)for(int j = 0;j<board[i].length;j++)
			if(board[i][j]=='#' || board[i][j]=='.')return false;
		return true;
	}
	
	public boolean checkIsland(char[][] brd, int x, int y) {
		int cnt = brd[x][y]-'0';
		if(cnt<=0 || cnt>9)return true;
		LinkedList<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] {x,y});
		brd[x][y] = 'X';
		while(!queue.isEmpty()) {
			int[] nxt = queue.poll();
			cnt--;
			for(int i = 0;i<dx.length;i++) {
				int xx = nxt[0]+dx[i], yy = nxt[1]+dy[i];
				if(xx<0 || xx>=brd.length || yy<0 || yy>=brd[xx].length)continue;
				if(brd[xx][yy]=='.') {
					queue.add(new int[] {xx,yy});
					brd[xx][yy] = 'X';
				}
			}
		}
		return cnt==0;
	}
	
	public void markWater(char[][] brd) {
		for(int i = 0;i<brd.length;i++)for(int j = 0;j<brd[i].length;j++) {
			if(brd[i][j]=='#') {
				LinkedList<int[]> queue = new LinkedList<int[]>();
				queue.add(new int[] {i,j});
				brd[i][j] = '_';
				while(!queue.isEmpty()) {
					int[] nxt = queue.poll();
					for(int m = 0;m<dx.length;m++) {
						int xx = nxt[0]+dx[m], yy = nxt[1]+dy[m];
						if(xx<0 || xx>=brd.length || yy<0 || yy>=brd[xx].length)continue;
						if(brd[xx][yy]=='#') {
							queue.add(new int[] {xx,yy});
							brd[xx][yy] = '_';
						}
					}
				}
				return;
			}
		}
	}
}
