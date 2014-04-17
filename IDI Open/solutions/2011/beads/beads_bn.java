// Author: B�rge Nordli
//
// Three implementations, only the binary tree implementation is fast enough.

import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.util.Scanner;
import java.util.StringTokenizer;

public class beads_bn {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(
			System.in));
	static StringTokenizer st;

	static String LINE() throws Exception {
		return stdin.readLine();
	}

	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())
			st = new StringTokenizer(LINE());
		return st.nextToken();
	}

	static int INT() throws Exception {
		return Integer.parseInt(TOKEN());
	}

	static long LONG() throws Exception {
		return Long.parseLong(TOKEN());
	}

	static double DOUBLE() throws Exception {
		return Double.parseDouble(TOKEN());
	}

	public static void main(String[] args) throws Exception {
		Collector c;
		for (int N = INT(); N-- > 0;) {
			int n = INT();
			c = new Node(1, n);
			StringBuilder sb = new StringBuilder();
			for (int i = INT() + INT(); i-- > 0;) {
				String op = TOKEN();
				if (op.equals("P")) {
					c.Add(INT(), INT());
				} else if (op.equals("Q")) {
					sb.append(c.Query(INT(), INT()));
					sb.append("\n");
//					System.out.println(c.Query(INT(), INT()));
				}
			}
			System.out.print(sb.toString());
		}
	}

	// static Scanner in = new Scanner(System.in);
}

interface Collector {
	public void Add(int pos, int val);

	public int Query(int start, int end);
}

///**
// * Trivial implementation data[i] is number of beads in box i adding beads is
// * O(1) counting beads is O(N)
// **/
//class Trivial implements Collector {
//	int[] data;
//
//	public Trivial(int size) {
//		data = new int[size + 1];
//	}
//
//	public void Add(int pos, int val) {
//		data[pos] += val;
//	}
//
//	public int Query(int start, int end) {
//		int ans = 0;
//		for (int i = start; i <= end; ++i) {
//			ans += data[i];
//		}
//		return ans;
//	}
//}
//
///**
// * Cumulative implementation data[i] is numbers of beads in box 1 through box i
// * adding beads is O(N) counting beads is O(1)
// **/
//class Cumulative implements Collector {
//	int[] data;
//
//	public Cumulative(int size) {
//		data = new int[size + 1];
//	}
//
//	public void Add(int pos, int val) {
//		for (int i = pos; i < data.length; ++i) {
//			data[i] += val;
//		}
//	}
//
//	public int Query(int start, int end) {
//		return data[end] - data[start - 1];
//	}
//}

/**
 * Binary tree implementation each Node is storing number of beads in box
 * 'start' to box 'end' and possibly has two sub-Nodes for finer counting.
 * adding beads is O(log(N)) counting beads is O(log(N))
 **/
class Node implements Collector {
	int start, end, value;
	Node left, right;

	public Node(int start, int end) {
		this.start = start;
		this.end = end;
		if (start != end) {
			int mid = (start + end) / 2;
			left = new Node(start, mid);
			right = new Node(mid + 1, end);
		}
	}

	public void Add(int pos, int val) {
		value += val;
		if (start != end) {
			if (pos <= (start + end) / 2)
				left.Add(pos, val);
			else
				right.Add(pos, val);
		}
	}

	public int Query(int start, int end) {
		if (start <= this.start && end >= this.end) {
			return value;
		} else if (end < this.start || start > this.end) {
			return 0;
		} else {
			return left.Query(start, end) + right.Query(start, end);
		}
	}
}
