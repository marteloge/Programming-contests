import java.io.*;
import java.util.*;


public class crosscountry_er {
	public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	public static StringTokenizer st = null;
	
	public static String LINE() throws IOException {
		return stdin.readLine();
	}
	
	public static String TOKEN() throws IOException {
		while(st==null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	
	public static int INT() throws IOException {
		return Integer.parseInt(TOKEN());
	}
	
	public static void main(String[] args) throws Exception {
		int cases = INT();
		while(cases-->0) {
			int racers = INT();
			int length = INT();
			int[] speeds = new int[racers];
			for(int i = 0;i<speeds.length;i++) {
				speeds[i] = INT();
			}
			System.out.println(solve(length, speeds));
			
		}
	}

	private static int solve(int length, int[] speeds) {
		int races = 0;
		boolean[] eliminated = new boolean[speeds.length];
		
		while(true) {
			
			int prevracer = 0;
			while(prevracer<speeds.length && eliminated[prevracer])
				prevracer++;
			
			if(prevracer == speeds.length)
				break;
			races++;
			
			eliminated[prevracer] = true;
			
			int interval = 1;
			for(int i = prevracer+1;i<speeds.length;i++) {
				if(eliminated[i])continue;
				
				// Does he reach the previous racer?
				int v1 = speeds[i], v2 = speeds[prevracer];
				if(interval*v1*v2<=length*(v1-v2)) {
					// YES!
					interval++;
				} else {
					// NO
					eliminated[i] = true;
					prevracer = i;
					interval = 1;
				}
				
			}
			
		}
		
		return races;
	}
}
