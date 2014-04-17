import java.io.*;
import java.util.*;

public class conquest_tab{
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    while(t-->0){
      HashMap<String,Integer> m= new HashMap<String,Integer>();
      String[] inp = in.readLine().split(",");
      for(int i=0;i<inp.length;i++){
        String[] w = inp[i].split(":");
        m.put(w[0],Integer.parseInt(w[1]));
      }
      int best = Integer.MAX_VALUE;
      inp = in.readLine().split("\\|");
      for(int i=0;i<inp.length;i++){
        String[] w = inp[i].split("&");
        int s = 0;
        for(int j=0;j<w.length;j++) s=Math.max(m.get(w[j]),s);
        if(s<best) best=s;
      }
      System.out.println(best);
    }
  }
}
