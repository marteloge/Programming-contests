// Solution for Robotic Encryption
// Author Rune Fevang

import java.io.*;
import java.util.*;
public class robocrypt_rune{
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static int INT(String s){ return Integer.parseInt(s);}
	static StringTokenizer st;
	static void ST() throws Exception { st = new StringTokenizer(in.readLine());}
	public static void main(String[] args) throws Exception{
		int n = INT(in.readLine());
		for(int t = 0; t < n; t++){
			new robocrypt_rune().entry();
		}
	}

	void entry() throws Exception{
		ST();
		my = INT(st.nextToken());
		mx = INT(st.nextToken());
		setup();
		grid = new char[mx][my];
		for(int i = 0; i < mx; i++){
			grid[i] = in.readLine().toCharArray();
		}
		int n = INT(in.readLine());

		String ret = "";
		int[] sofar = IDENT;
		for(int t = 0; t < n; t++){
			String s = in.readLine();
			sofar = combine(sofar, go(s));
			ret += decode(sofar[0]);
		}
		System.out.println(ret);
	}

	int mx, my, n;
	char[][] grid;

	int encode(int x, int y, int dir){
		return 4*mx*y + 4*x + dir;
	}

	char decode(int n){
		int dir = n%4;
		n /= 4;
		int y = n/mx;
		int x = n%mx;
		return grid[x][y];
	}

	int[] go(String s){
		if(s.equals("")) return IDENT;
		int[] part;
		if(s.charAt(0) == 'F'){
			part = FORWARD;
		}else if(s.charAt(0) == 'L'){
			part = LEFT;
		}else if(s.charAt(0) == 'R'){
			part = RIGHT;
		}else{
			int left = 0;
			for(int i = 0;;i++){
				if(s.charAt(i) == '(') left++;
				else if(s.charAt(i) == ')') left--;
				if(left == 0){
					int count = s.charAt(i+1)-'0';
					part = repeat(go(s.substring(1,i)), count);
					s = s.substring(i+1);
					break;
				}
			}
		}
		return combine(part, go(s.substring(1)));
	}

	int[] IDENT;
	int[] FORWARD;
	int[] LEFT;
	int[] RIGHT;

	int[] dx = {1,0,-1,0};
	int[] dy = {0,1,0,-1};

	void setup(){
		n = 4*mx*my;
		IDENT = new int[n];
		FORWARD = new int[n];
		LEFT = new int[n];

		for(int x = 0; x < mx; x++){
			for(int y = 0; y < my; y++){
				for(int dir = 0; dir < 4; dir++){
					LEFT[encode(x,y,dir)] = encode(x,y,(dir+1)%4);
					IDENT[encode(x,y,dir)] = encode(x,y,dir);
					int nx = x+dx[dir];
					int ny = y+dy[dir];
					int ndir = dir;
					if(nx < 0 || ny < 0 || nx >= mx || ny >= my){
						nx = x;
						ny = y;
						ndir = (dir+2)%4;
					}
					FORWARD[encode(x,y,dir)] = encode(nx, ny, ndir);
				}
			}
		}
		RIGHT = repeat(LEFT, 3);
	}

	int[] combine(int[] a, int[] b){
		int[] ret = new int[n];
		for(int i = 0; i < a.length; i++){
			ret[i] = b[a[i]];
		}
		return ret;
	}
	int[] repeat(int[] a, int p){
		int[] ret = IDENT;
		for(int i = 0; i < p; i++) ret = combine(ret, a);
		return ret;
	}
}


