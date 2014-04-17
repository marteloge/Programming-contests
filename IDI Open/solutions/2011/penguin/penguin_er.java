import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class penguin_er {

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
			int N = INT(), W = INT();
			int[] profile = new int[N+1];
			for(int i = 0;i<profile.length;i++) {
				profile[i] = INT();
			}
			
			System.out.println(solve(profile, W));
		}

	}
	
	public static double solve(int[] profile, int water) {
		double max = 100.0;
		double min = 0.0;
		int iterations = 50;
		while(iterations-->0) {
			double mid = min + (max-min)/2.0;
			double needed = waterNeeded(profile, mid);
			if(needed>water)min = mid;
			else max = mid;
			
		}
		return max;
	}
	
	public static double waterNeeded(final int[] profile, final double maxHeight) {
		double[] waterLevel = new double[profile.length];
		
		// Filling the track!
		double lastwl = 0.0;
		// Left-to-right
		for(int i = 0;i<profile.length;i++) {
			if(lastwl>profile[i])waterLevel[i] = lastwl;
			else if(i==0 || profile[i]>=profile[i-1])lastwl = profile[i] - maxHeight;
		}
		lastwl = 0.0;
		// Right-to-left
		for(int i = profile.length-1;i>=0;i--) {
			if(lastwl>profile[i] && lastwl>waterLevel[i])waterLevel[i] = lastwl;
			else if(i==profile.length-1 || profile[i]>=profile[i+1])lastwl = profile[i]-maxHeight;
		}
		
		// Calculating amount of water:
		double water = 0.0;
		for(int i = 0;i<profile.length-1;i++) {
			water += usedWater(profile[i], profile[i+1],Math.max(waterLevel[i], waterLevel[i+1]));
		}
		return water;
		
	}
	
	public static double usedWater(final int ha, final int hb, final double waterLevel) {
		double max = ha>hb?ha:hb, min = ha<hb?ha:hb;
		if(waterLevel<=min)	return 0.0;
		if(waterLevel>max) 	return waterLevel - max + (max-min)/2;
		return (waterLevel-min)*(waterLevel-min)/(2*(max-min));
	}

}
