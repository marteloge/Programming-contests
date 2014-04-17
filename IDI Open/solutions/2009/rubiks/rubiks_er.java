/*
 * Author: Eirik Reksten
 * 
 * Solution for Rubiks Cube from IDI Open 2009
 * 
 * Precomputing all cubes at distance 7 or lower from the goal cube,
 * and running a BFS from each case until I hit one of these.
 * 
 */

import java.io.*;
import java.text.*;
import java.util.*;

public class rubiks_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String LINE() throws Exception { return stdin.readLine(); }
	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static long LONG() throws Exception {return Long.parseLong(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}

	static DecimalFormat DF = new DecimalFormat("0.000",new DecimalFormatSymbols(Locale.ENGLISH));
	
	public static void main(String[] args) throws Exception {
		
		// Precomputing a bunch of distances from the goal cube.
		distances = new HashMap<String, Integer>();
		LinkedList<Cube> queue = new LinkedList<Cube>();
		Cube goal = new Cube("RRRRYYOOGGBBYYOOGGBBWWWW");
		queue.add(goal);
		distances.put(goal.toString(), 0);
		while(!queue.isEmpty()) {
			Cube next = queue.poll();
			int dd = distances.get(next.toString());
			if(dd>6)break;
			for(Cube nn : next.doMoves()) {
				if(distances.containsKey(nn.toString()))continue;
				distances.put(nn.toString(),dd+1);
				queue.add(nn);
			}
		}
		
		int cases = INT();
		while(cases-->0) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0;i<12;i++)sb.append(TOKEN());
			new rubiks_er().go(new Cube(sb.toString()));
		}
		
	}
	
	public static HashMap<String, Integer> distances;
	
	public void go(Cube start) throws Exception {
		
		start.mapToStandard();
		
		LinkedList<Cube> queue = new LinkedList<Cube>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		queue.add(start);
		map.put(start.toString(), 0);
		while(!queue.isEmpty()) {
			Cube cc = queue.poll();
			if(distances.containsKey(cc.toString())) {
				System.out.println((distances.get(cc.toString())+map.get(cc.toString())));
				break;
			}
			int dd = map.get(cc.toString());
			for(Cube nn : cc.doMoves()) {
				if(map.containsKey(nn.toString()))continue;
				map.put(nn.toString(),dd+1);
				queue.add(nn);
			}
		}
	}
	
	private static class Cube {
		
		char[] colors;
		
		// Yes, I am aware that these two could possibly have been done a little smarter.
		// It didn't take as much time as you'd think, though :)
		private static final int[][] moves = new int[][] {
			new int[] {1,3,0,2,10,11,4,5,6,7,8,9,12,13,14,15,16,17,18,19,20,21,22,23}, // Top clockwise
			new int[] {2,0,3,1,6,7,8,9,10,11,4,5,12,13,14,15,16,17,18,19,20,21,22,23}, // Top counterclockwise
			new int[] {0,1,6,14,5,13,21,7,8,9,10,3,4,12,20,15,16,17,18,2,11,19,22,23}, // Front clockwise
			new int[] {0,1,19,11,12,4,2,7,8,9,10,20,13,5,3,15,16,17,18,21,14,6,22,23}, // Front counterclockwise
			new int[] {0,16,2,8,4,1,7,15,23,9,10,11,12,3,6,14,21,17,18,19,20,5,22,13}, // Side clockwise
			new int[] {0,5,2,13,4,21,14,6,3,9,10,11,12,23,15,7,1,17,18,19,20,16,22,8}  // Side counterclockwise
		};
		private static final int[][] same = new int[][] {
			new int[] {9,10}, new int[] {7,8}, new int[] {4,11}, new int[] {5,6},
			new int[] {2,11}, new int[] {3,6}, new int[] {3,5},	new int[] {1,8},
			new int[] {1,7}, new int[] {0,10}, new int[] {0,9},	new int[] {2,4},
			new int[] {19,20}, new int[] {14,21}, new int[] {13,21}, new int[] {16,23},
			new int[] {15,23}, new int[] {18,22}, new int[] {17,22}, new int[] {12,20},
			new int[] {12,19}, new int[] {13,14}, new int[] {17,18}, new int[] {15,16}
		};
		private static final String col = "RYOGBW"; 
		
		Cube(char[] colors) {
			this.colors = colors;
		}
		
		Cube(String rep) {
			this.colors = rep.toCharArray();
		}
		
		// Mapping to one of my precomputed cubes
		public void mapToStandard() {
			
			Set<Character>[] neighs = new TreeSet[6];
			for(int i = 0;i<6;i++)neighs[i] = new TreeSet<Character>();
			for(int i = 0;i<24;i++) {
				int ci = col.indexOf(this.colors[i]);
				neighs[ci].add(this.colors[i]);
				neighs[ci].add(this.colors[same[i][0]]);
				neighs[ci].add(this.colors[same[i][1]]);
			}
			char[] opposite = new char[6];
			for(int i = 0;i<6;i++)for(int j = 0;j<6;j++) {
				if(neighs[i].contains(col.charAt(j)))continue;
				opposite[i] = col.charAt(j);
			}
			
			HashMap<Character, Character> mm = new HashMap<Character, Character>();
			mm.put(this.colors[17],'G');
			mm.put(this.colors[22],'W');
			mm.put(this.colors[18],'B');
			mm.put(opposite[col.indexOf(this.colors[17])],'Y');
			mm.put(opposite[col.indexOf(this.colors[22])],'R');
			mm.put(opposite[col.indexOf(this.colors[18])],'O');

			for(int i = 0;i<24;i++)this.colors[i] = mm.get(this.colors[i]);
			
		}
		
		// Returning all cubes adjacent to this one
		public Cube[] doMoves() {
			Cube[] ncubes = new Cube[6];
			for(int i = 0;i<moves.length;i++) {
				char[] nc = new char[24];
				for(int j = 0;j<24;j++)nc[j] = this.colors[moves[i][j]];
				ncubes[i] = new Cube(nc);
			}
			return ncubes;
		}
		
		public String toString() {
			return String.valueOf(colors);
		}
		
	}

}
