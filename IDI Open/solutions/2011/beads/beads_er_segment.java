import java.io.*;
import java.text.*;
import java.util.*;


public class beads_er_segment {

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
		int games = INT();
		while(games-->0) {
			new beads_er_segment().go();
		}
	}
	
	public void go() throws Exception {
		int b = INT(), p = INT(), q = INT();
		int n = p+q;
		
		BeadBoxes tree = new BeadBoxes(b);
		
		while(n-->0) {
			StringBuilder sb = new StringBuilder();
			String action = TOKEN();
			if("P".equals(action)) {
				int box = INT()-1;
				int amount = INT();
				tree.insertBeads(box, amount);
			} else {
				int lowerBound = INT()-1;
				int upperBound = INT()-1;
				long cnt = tree.countBeads(upperBound) - tree.countBeads(lowerBound-1);
//				System.out.println(cnt);
				sb.append(cnt);
				sb.append("\n");
			}
			System.out.print(sb.toString());
		}
		
	}
	
	
	private static class BeadBoxes {
		
		long[][] cnt;
		int MAX;
			
		BeadBoxes(int boxes) {
			MAX = boxes;
			int rr = 1, rs = 1;
			while(rr<boxes) { rr *= 2; rs++; }
			cnt = new long[rs][];
			for(int i = 0;i<rs;i++, rr /= 2)cnt[i] = new long[rr];
		}

		public void insertBeads(int number, int amount) {
			for(int i = 0;i<cnt.length;i++, number /= 2)
				cnt[i][number] += amount;
		}
		
		public long countBeads(int toBox) {
			if (toBox<0)return 0;
			if(toBox>=MAX)return cnt[cnt.length-1][0];
			long sum = 0;
			for(int i = 0;i<cnt.length;i++, toBox /= 2) {
				if(toBox%2==0)sum += cnt[i][toBox];
				toBox--;
				if(toBox<0)break;
			}
			return sum;
		}
	}
	
}
