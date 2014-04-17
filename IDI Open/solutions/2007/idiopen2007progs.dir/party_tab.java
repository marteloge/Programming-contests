import java.io.*;
import java.util.*;

public class party_tab{
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    outer:while(t-->0){
      String[] inp = in.readLine().split(" ");
      int m = Integer.parseInt(inp[0]);
      int f = Integer.parseInt(inp[1]);
      int[][] cap = new int[f+m+2][f+m+2];
      for(int i=0;i<m;i++) cap[i][f+m]=1;
      for(int i=0;i<f;i++){
        inp = in.readLine().split(" ");
        for(int j=1;j<inp.length;j++) cap[m+i][Integer.parseInt(inp[j])]=1;
      }
      int last =0;
      int[][] fl = new int[cap.length][cap[0].length];
      for(int j=1;j<=m;j++){
        for(int i=m;i<cap.length-2;i++) cap[cap.length-1][i]=j;
        int te = maxflow(cap,fl,cap.length-1,cap.length-2);
        if(te==0) break;
        last+=te;
        if(last==m){System.out.println(j); continue outer;}
      }
      System.out.println("impossible");
    }
  }

  public static int maxflow(int[][] cap, int[][] flow, int from, int to){
    int nr = 0;
    int n = cap.length;
    int[] augpath = finnAugmentingPath(from, to, flow, cap);
    while(augpath!=null){
      int last =to;
      while(last!=from){
        flow[last][augpath[last]] -= 1;
        flow[augpath[last]][last] += 1;
        last=augpath[last];
      }
      augpath = finnAugmentingPath(from, to, flow, cap);
      nr++;
    }
    return nr;
  }
  
  public static int[] finnAugmentingPath(int kilde, int sluk, int[][] flyt, int[][] kapasitet) {
    boolean[]erOppdaga = new boolean[flyt.length];
    ArrayList<Integer> BFko = new ArrayList<Integer>();
    BFko.add(new Integer(kilde));
    erOppdaga[kilde]=true;
    int denne;
    int[] parent = new int[flyt.length];
    int x=0;
    while (x<BFko.size()) {
      denne = BFko.get(x++).intValue();
      for (int i = 0; i < flyt.length; i++) {
        if ((!erOppdaga[i]) && (flyt[denne][i] < kapasitet[denne][i])) {
          BFko.add(new Integer(i));
          erOppdaga[i] = true;
          parent[i] = denne;
          if (i == sluk) return parent;
        }
      }
    }
    return null;
  }
}
