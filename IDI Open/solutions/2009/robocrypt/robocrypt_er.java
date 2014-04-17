/*
 * Author: Eirik Reksten
 * 
 * Solution for Robotic Encryption from IDI Open 2009
 * 
 * Computing a W*H*4 matrix for each set of instructions. This allows us to not having to compute
 * the outcome of the same set of instructions more than once. This reduces the amount of computation
 * due to loops drastically.
 * 
 */

import java.io.*;
import java.util.*;

public class robocrypt_er {

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
			INT();
			int H = INT();
			char[][] grid = new char[H][];
			for(int i = 0;i<H;i++)grid[i] = stdin.readLine().toCharArray();
			int N = INT();
			String[] commandlines = new String[N];
			for(int i = 0;i<N;i++)commandlines[i] = TOKEN();
			new robocrypt_er().go(grid, commandlines);
		}
	}
	
	public void go(char[][] grid, String[] commandlines) throws Exception {
		
		W = grid.length; H = grid[0].length;
		StringBuilder decrypt = new StringBuilder();
		int x = 0, y = 0, dir = 0;
		for(String cmd : commandlines) {
			CommandSet cc = new CommandSet(cmd);
			int[] moving = cc.adjacencyMatrix();
			int to = moving[toIndex(x, y, dir)];
			int[] nst = fromIndex(to);
			x = nst[0];
			y = nst[1];
			dir = nst[2];
			decrypt.append(grid[x][y]);
		}
		System.out.println(decrypt.toString());
		
	}
	
	private int W, H;
	private int[] identity;
	private int[][] commandbuff = new int[3][];
	
	private int toIndex(int x, int y, int dir) {
		return 4*H*x+4*y+dir;
	}
	
	private int[] fromIndex(int index) {
		int[] ret = new int[3];
		ret[2] = index%4;
		index /= 4;
		ret[1] = index%H;
		ret[0] = index/H;
		return ret;
	}
	
	// The "stand still" matrix
	public int[] identitymatrix() {
		if(identity==null) {
			int length = 4*W*H;
			int[] rr = new int[length];
			for(int i = 0;i<length;i++)rr[i] = i;
			return rr;
		}
		return identity;
	}
	
	
	// The matrices for the most basic commands
	public int[] commandMatrix(char command) {
		int val = command=='F'?0:(command=='R'?1:2);
		if(commandbuff[val]==null) {
			int[] ret = new int[4*W*H];
			for(int x = 0;x<W;x++) {
				for(int y = 0;y<H;y++) {
					for(int i = 0;i<4;i++) {
						int nx = x, ny = y, ni = i;
						if(command=='F') {
							if(i==0) { nx++; if(nx==W) {nx--; ni = 2;} }
							else if(i==1) { ny--; if(ny==-1) { ny = 0; ni = 3; } }
							else if(i==2) { nx--; if(nx==-1) {nx = 0; ni = 0;} }
							else if(i==3) { ny++; if(ny==H) {ny--; ni = 1;} }
						}
						else if(command=='R')ni = (ni+1)%4;
						else ni = (ni+3)%4;
						ret[toIndex(x, y, i)]= toIndex(nx, ny, ni);
					}
				}
			}
			commandbuff[val] = ret;
		}
		return commandbuff[val];
	}
	
	// Combining two matrixes to form the one for a more advanced set of instructions.
	public int[] combine(int[] A, int[] B) {
		int[] rr = new int[A.length];
		for(int i = 0;i<rr.length;i++)rr[i] = B[A[i]];
		return rr;
	}
	
	// ******** Classes for parsing and executing instructions **********
	
	private static abstract class Instruction {
		public abstract int[] adjacencyMatrix();
	}
	 
	private class CommandSet extends Instruction {
		ArrayList<Instruction> command;
		
		CommandSet(String line) {
			command = new ArrayList<Instruction>();
			for(int i = 0;i<line.length();i++) {
				char nxt = line.charAt(i);
				if(nxt=='(') {
					int cnt = 1, at = i+1;
					while(cnt>0) {
						if(line.charAt(at)==')')cnt--;
						else if(line.charAt(at)=='(')cnt++;
						at++;
					}
					command.add(new Loop(line.substring(i,at+1)));
					i = at;
				} else command.add(new Command(nxt));
			}
		}

		public int[] adjacencyMatrix() {
			int[] at = identitymatrix();
			for(Instruction s : command)at = combine(at, s.adjacencyMatrix());
			return at;
		}
		
	}
	
	private class Loop extends Instruction {
		CommandSet command;
		int times;
		
		Loop(String line) {
			times = line.charAt(line.length()-1)-'0';
			command = new CommandSet(line.substring(1,line.length()-2));
		}

		public int[] adjacencyMatrix() {
			int[] at = identitymatrix();
			int[] mover = command.adjacencyMatrix();
			for(int i = 0;i<times;i++)at = combine(at, mover);
			return at;
		}
		
	}
	
	private class Command extends Instruction {
		
		char command;
		
		Command(char cmd) {
			this.command = cmd;
		}
		
		public int[] adjacencyMatrix() {
			return commandMatrix(this.command);		}
		
	}
	

}
