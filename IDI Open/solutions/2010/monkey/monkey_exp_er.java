/*
 * Solution for Problem H - Typing Monkey
 * 
 * Gaussian elimination doesn't work on this problem due to numerical
 * stability issues; double doesn't handle subtraction of very small
 * numbers very well.
 * You could overcome this problem by writing your own data type, but 
 * chances are that this will lead to a time limit exceeded.
 * 
 * - You will always be in one of (N+M+1) states, defined by the longest
 *   match with one of the strings.
 * - Transitions between these states are defined by the next letter typed
 * 
 * 1. Generate the graph explained above using recursion. Weights for these
 *    edges are the probability of walking along the edge. Also add an edge
 *    from each of the two terminal states (matching an entire word) to itself
 *    of probability 1.0.
 * 2. We are looking for the probability of being in the first terminal state
 *    after a huge amount of steps.
 * 3. Increase the probability matrix to a huge power, and read the resulting
 *    probability of going from state 0 to the desired terminal state. Using
 *    repeated squaring, high powers can be reached very quickly.
 * 4. Normalize the probabilities to avoid precision issues (though I don't do
 *    this here, the safest way is to normalize each row in the probability matrix
 *    after each multiplication.
 * 
 * O((N+M)^3)
 * 
 * To those trying the "compare the probabilities of getting the word on the
 * first try" approach:
 * If you have two letters, each with a probability of 0.5, and the two words
 * "abab" and "babb", the probability of getting the first one first is 9/14!
 * 
 * Eirik Reksten
 */

import java.io.*;
import java.util.*;

public class monkey_exp_er {

	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static String TOKEN() throws Exception {
		while (st == null || !st.hasMoreTokens())st = new StringTokenizer(stdin.readLine());
		return st.nextToken();
	}
	static int INT() throws Exception {return Integer.parseInt(TOKEN());}
	static double DOUBLE() throws Exception {return Double.parseDouble(TOKEN());}
	
	static final double EPSILON = 1e-20;
	
	public static void main(String[] args) throws Exception {
		
		int cases = INT();
		while(cases-->0) {
			
			double[] probs = new double[26];
			for(int i = 0;i<26;i++) {
				String pp = TOKEN();
				probs[i] = Double.parseDouble(pp);
			}
			
			State.clear();
			State.a = TOKEN();
			State.b = TOKEN();
			State.states = new TreeMap<String, State>();
			
			State.create(0,0);
			
			int nodes = State.stateCount;
			
			int start = 0, end = 0, end2 = 0;
			
			double[][] A = new double[nodes][];
			for(String s : State.states.keySet()) {
				State st = State.states.get(s);
				A[st.index] = st.getEquation(probs);
				if(st.alet==State.a.length()) {
					end = st.index;
				} else if(st.blet==State.b.length()) {
					end2 = st.index;
				}
			}
			
			if(end==0) {
				System.out.println("0.0");
			} else if(end2==0) {
				System.out.println("1.0");
			} else {
				A[end][end] = A[end2][end2] = 1.0;
				// A^(2^pow)
				int pow = 50;
				for(int i = 0;i<pow;i++)A = multMatrices(A, A);
				System.out.println((A[start][end]/(A[start][end2]+A[start][end])));
			}
			
		}
		
	}
	
	private static class State {
		
		static String a, b;
		static TreeMap<String,State> states;
		static int stateCount = 0;
		
		int index;
		int alet, blet;
		State[] neighbors;
		
		State(int alet, int blet) {
			this.alet = alet;
			this.blet = blet;
			this.index = stateCount++;
		}
		
		
		public double[] getEquation(double[] letterprobs) {
			double[] ret = new double[stateCount];
			if(this.neighbors==null)return ret;
			double left = 1.0;
			for(int i = 0;i<26;i++) {
				State next = neighbors[i];
				if(next.index>0) {
					ret[next.index] += letterprobs[i];
					left -= letterprobs[i];
				}
			}
			ret[0] += left;
			return ret;
		}
		
		public static State create(int alet, int blet) {

			String key = alet+"#"+blet;
			State here = new State(alet,blet);
			states.put(key,here);
			
			if(alet<a.length() && blet<b.length()) {
				here.neighbors = new State[26];
				char[] untilnow = new char[Math.max(alet,blet)+1];
				if(alet>blet)for(int i = 0;i<alet;i++)untilnow[i] = a.charAt(i);
				else for(int i = 0;i<blet;i++)untilnow[i] = b.charAt(i);
				
				// Calculate Neighbors!
				for(char next = 'a';next<='z';next++) {
					untilnow[untilnow.length-1] = next;
					int nalet = match(a,untilnow);
					int nblet = match(b,untilnow);
					if(nalet>nblet) {
						char[] nmatch = new char[nalet];
						System.arraycopy(untilnow,untilnow.length-nalet,nmatch,0,nalet);
						nblet = match(b,nmatch);
					} else {
						char[] nmatch = new char[nblet];
						System.arraycopy(untilnow,untilnow.length-nblet,nmatch,0,nblet);
						nalet = match(a,nmatch);
					}
					String nextkey = nalet+"#"+nblet;
					
					if(states.containsKey(nextkey))here.neighbors[next-'a'] = states.get(nextkey);
					else  {
						here.neighbors[next-'a'] = create(nalet,nblet);
					}
				}
			}
			
			return here;
			
		}
		
		public static void clear() {
			State.a = null;
			State.b = null;
			State.states = null;
			State.stateCount = 0;
		}
		
	}

	public static int match(String ref, char[] lastletters) {
		for(int start = 0;start<lastletters.length;start++) {
			boolean ok = true;
			for(int i = 0;start+i<lastletters.length;i++) {
				if(ref.charAt(i)!=lastletters[start+i]) {
					ok = false;
					break;
				}
			}
			if(ok) return lastletters.length-start;
		}
		return 0;
	}
	
	public static double[][] multMatrices(double[][] A, double[][] B) {
		double[][] ans = new double[A.length][B[0].length];
		for(int i = 0;i<A.length;i++)
			for(int j = 0;j<B[0].length;j++)
				for(int k = 0;k<B.length;k++)
					ans[i][j] += A[i][k]*B[k][j];
		return ans;
	}
	
}
