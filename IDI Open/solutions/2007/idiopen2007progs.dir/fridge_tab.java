import java.io.*;

public class fridge_tab{
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    while(t-->0) System.out.println(Integer.parseInt(in.readLine(),2));
  }
}
