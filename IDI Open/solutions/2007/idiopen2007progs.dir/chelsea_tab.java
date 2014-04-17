import java.io.*;

public class chelsea_tab{
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(in.readLine());
    while(n-->0){
      int max = -1;
      String best = null;
      int p = Integer.parseInt(in.readLine());
      for(int i=0;i<p;i++){
        String[] inp = in.readLine().split(" ");
        int c = Integer.parseInt(inp[0]);
        if(c>max){max=c; best=inp[1];}
      }
      System.out.println(best);
    }
  }
}
