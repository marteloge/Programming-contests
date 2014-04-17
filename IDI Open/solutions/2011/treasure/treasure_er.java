import java.io.*;
import java.util.*;


public class treasure_er {

	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer st = null;
	
	private static String LINE() throws IOException {
		return stdin.readLine();
	}
	
	private static String TOKEN() throws IOException {
		while(st==null || !st.hasMoreTokens())st = new StringTokenizer(LINE());
		return st.nextToken();
	}
	
	private static int INT() throws IOException {
		return Integer.parseInt(TOKEN());
	}
	
	public static void main(String[] args) throws Exception {
		
		int cases = INT();
		while(cases-->0) {
			int N = INT(), M = INT();
			long[][] treasures = new long[N][2];
			long[][] mines = new long[M][2];
			for(int i = 0;i<N;i++)treasures[i][0] = INT();
			for(int i = 0;i<N;i++)treasures[i][1] = INT();
			for(int i = 0;i<M;i++)mines[i][0] = INT();
			for(int i = 0;i<M;i++)mines[i][1] = INT();
			System.out.println(solve(treasures, mines));
		}
	}
	
	private static int solve(final long[][] treasures, long[][] tmpmines) {
		
		final long[][] mines = reverse(monotoneChain(tmpmines));
		
		int M = mines.length, N = treasures.length;
		
		LinkedList<Integer> inside = new LinkedList<Integer>();
		LinkedList<Integer> outside = new LinkedList<Integer>();
		
		for(int i = 0;i<N;i++) {
			// ALL points on the (infinite) line to the inside
			if(ccw(mines[M-1],mines[0],treasures[i])<0)outside.add(i);
			else inside.add(i);
		}
		int best = outside.size();
		
		for(int pivot = 0;pivot<M;pivot++) {
			// Put next mine in inside list.
			inside.add(-((pivot+1)%M)-1);
			
			// Sort according to angle around pivot
			final int mypivot = pivot;
			Collections.sort(inside, new Comparator<Integer>() {
				@Override
				public int compare(Integer i, Integer j) {
					if(i<0)return sortCCW(mines[mypivot],treasures[j],mines[-i-1]);
					if(j<0)return sortCCW(mines[mypivot],mines[-j-1],treasures[i]);
					return sortCCW(mines[mypivot],treasures[j],treasures[i]);
				}
			});
			
			Collections.sort(outside, new Comparator<Integer>() {
				@Override
				public int compare(Integer i, Integer j) {
					return sortCCW(mines[mypivot],treasures[j],treasures[i]);
				}
			});
			
			// Roll line around to next pivot
			while(true) {
				
				int nextinside = inside.peek();
				long[] ip = nextinside>=0?treasures[nextinside]:mines[-nextinside-1];
				
				if(outside.size()==0 || sortCCW(ip,mines[pivot],treasures[outside.peek()]) > 0 ) {
					// Inside point comes moves out
					nextinside = inside.poll();
					if(nextinside<0)break;
					outside.add(nextinside);
					
				} else {
					// Outside point moves in
					int nextoutside = outside.poll();
					inside.add(nextoutside);
				}
				
				best = Math.max(best, outside.size());
				
			}
			
			
		}
		
		
		return best;
	}

	private static long[][] reverse(long[][] arr) {
		long[][] rev = new long[arr.length][];
		for(int i = 0; i<arr.length;i++) {
			rev[i] = arr[arr.length-1-i];
		}
		return rev;
	}

	private static long ccw(long[] A, long[] B, long[] C) {
		long x1 = B[0]-A[0], y1 = B[1]-A[1];
		long x2 = C[0]-A[0], y2 = C[1]-A[1];
		return x1*y2-x2*y1;
	}
	
	private static int sortCCW(long[] A, long[] B, long[] C) {
		long turn = ccw(A,B,C);
		if(turn == 0) {
			// The points are colinear!
			long ab = sqdist(A,B);
			long ac = sqdist(A,C);
			long bc = sqdist(B,C);
			// if a-b turns around to c, treat as left turn (1)
			if(ac<bc || ac<ab)return 1;
			// if a-b continues to c, treat as right turn (-1)
			return -1;
		}
		return turn<0?-1:1;
	}
	
	private static long sqdist(long[] A, long[] B) {
		long dx = A[0]-B[0], dy = A[1]-B[1];
		return dx*dx+dy*dy;
	}
	
	private static long[][] monotoneChain(long[][] polygon) {
		int N = polygon.length;
		if (N == 1)return new long[][] { { polygon[0][0], polygon[0][1] } };
		long[][] points = new long[N][];
		for(int i = 0;i<N;i++)points[i] = new long[] {polygon[i][0], polygon[i][1]};
		
		Arrays.sort(points, 0, N, new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				if(a[0]<b[0])return -1;	if(a[0]>b[0])return 1;
				if(a[1]<b[1])return -1;	if(a[1]>b[1])return 1;
				return 0;
			}
		});
		long[][] U = new long[N+2][]; int ulen = 0;
		for(int i = 0;i<N;i++) {
			U[ulen++] = points[i];
			while(ulen>2 && ccw(U[ulen-3],U[ulen-2],U[ulen-1])>=0)U[ulen-2] = U[--ulen];
		}
		int length = ulen--;
		for(int i = N-1;i>=0;i--) {
			U[length++] = points[i];
			while(length-ulen>2 && ccw(U[length-3],U[length-2],U[length-1])>=0)	U[length-2] = U[--length];
		}
		if(U[length-1]==U[0])length--;
		long[][] hull = new long[length][];
		System.arraycopy(U,0,hull,0,length);
		return hull;
	}
	
}
