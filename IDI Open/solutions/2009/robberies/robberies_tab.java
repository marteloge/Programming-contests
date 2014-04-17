// Solution for Robberies
// Author Truls Amundsen Bjørklund

import java.io.*;

public class robberies_tab{
  public static final int MAX_MILLS=10000;
  
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    while(t-->0){
      String[] inp = in.readLine().split(" ");
      double p = 1.0-Double.parseDouble(inp[0]);
      int n = Integer.parseInt(inp[1]);
      int[] m = new int[n];
      double[] ps = new double[n];
      for(int i=0;i<n;i++){
        inp = in.readLine().split(" ");
        m[i] = Integer.parseInt(inp[0]);
        ps[i] = 1.0-Double.parseDouble(inp[1]);
      }
      double[] probs = new double[MAX_MILLS+1];
      probs[0]=1.0;
      for(int i=0;i<n;i++) for(int j=MAX_MILLS-m[i];j>=0;j--){
        double prob = probs[j]*ps[i];
        if(prob>p && prob>probs[j+m[i]]) probs[j+m[i]]=prob;
      }
      int i=0;
      for(i=MAX_MILLS;probs[i]<p;i--);
      System.out.println(i);
    }
  }
}
