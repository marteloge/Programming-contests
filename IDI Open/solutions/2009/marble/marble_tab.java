// Solution for Marble Madness
// Author Truls Amundsen Bjørklund

import java.io.*;
import java.math.*;

public class marble_tab{
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    for(int n = Integer.parseInt(in.readLine());n>0;n--)
      System.out.println(new BigInteger(in.readLine().split(" ")[1]).testBit(0)?"0.00 1.00":"1.00 0.00");
  }
}
