/*
 * Author: Eirik Reksten
 * 
 * Solution for Rubiks Cube from IDI Open 2009
 * 
 * Iterating through the grid, initiating a flood fill of every flock I encounter.
 * 
 */

import java.io.*;
import java.util.*;

public class sheep_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			int h = INT(); INT();
			char[][] grid = new char[h][];
			for(int i = 0;i<h;i++)grid[i] = TOKEN().toCharArray();
			System.out.println(new sheep_er().count(grid));
		}
	}
	
	public int count(char[][] grid) {
		int cnt = 0;
		for(int i = 0;i<grid.length;i++)
			for(int j = 0;j<grid[i].length;j++)
				if(grid[i][j]=='#') {
					floodFill(grid,i,j);
					cnt++;
		}
		return cnt;
	}
	
	public void floodFill(char[][] grid, int x, int y) {
		
		LinkedList<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] {x,y});
		
		while(!queue.isEmpty()) {
			int[] next = queue.poll();
			int xx = next[0], yy = next[1];
			if(xx<0 || xx>=grid.length || yy<0 || yy>=grid[xx].length)continue;
			if(grid[xx][yy]!='#')continue;
			
			grid[xx][yy] = '.';
			
			queue.add(new int[] {xx-1,yy});
			queue.add(new int[] {xx+1,yy});
			queue.add(new int[] {xx,yy-1});
			queue.add(new int[] {xx,yy+1});
			
		}
		
	}

}
